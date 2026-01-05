package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.project.application.dto.*;
import org.project.application.mapper.UserMapper;
import org.project.domain.model.User;
import org.project.domain.repository.UserRepository;
import org.project.infrastructure.security.JwtService;
import org.project.infrastructure.rest.GoogleAuthClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mindrot.jbcrypt.BCrypt;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import jakarta.enterprise.inject.Instance;
import java.util.Optional;

/**
 * Service for handling authentication business logic.
 */
@ApplicationScoped
public class AuthService {

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final Instance<FirebaseAuth> firebaseAuth;

    @Inject
    @RestClient
    GoogleAuthClient googleAuthClient;

    @Inject
    public AuthService(UserRepository userRepository, JwtService jwtService, UserMapper userMapper,
            Instance<FirebaseAuth> firebaseAuth) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.firebaseAuth = firebaseAuth;
    }

    @Transactional
    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new WebApplicationException("Email already exists", Response.Status.CONFLICT);
        }

        String formattedPhone = formatToE164(request.phoneNumber());

        // Create user in Firebase
        try {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(request.email())
                    .setPassword(request.password())
                    .setDisplayName(request.name());

            if (formattedPhone != null) {
                createRequest.setPhoneNumber(formattedPhone);
            }

            firebaseAuth.get().createUser(createRequest);
        } catch (FirebaseAuthException e) {
            if ("email-already-exists".equals(e.getErrorCode())) {
                // User already in Firebase, proceed with local creation
            } else if ("invalid-phone-number".equals(e.getErrorCode())) {
                throw new WebApplicationException(
                        "Invalid phone number format. Please use a format like +5511999999999",
                        Response.Status.BAD_REQUEST);
            } else {
                LOG.error("Error creating Firebase user: " + e.getMessage() + " Code: " + e.getErrorCode(), e);
                throw new WebApplicationException("Error creating Firebase user: " + e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }

        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        User user = User.create(request.userType(), request.name(), request.email(), hashedPassword,
                formattedPhone);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    private String formatToE164(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return null;
        }

        // Remove any non-digit characters except '+'
        String cleaned = phoneNumber.replaceAll("[^\\d+]", "");

        if (cleaned.startsWith("+")) {
            return cleaned;
        }

        // If it doesn't start with '+', we assume it's a number that needs it.
        // For now, let's just prepend '+' if it's all digits.
        if (cleaned.matches("\\d+")) {
            return "+" + cleaned;
        }

        return cleaned; // Let Firebase validate if it's still weird
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED));

        if (!BCrypt.checkpw(request.password(), user.getPassword())) {
            throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);
        }

        return generateTokenResponse(user);
    }

    public TokenResponse refresh(String refreshToken) {
        throw new WebApplicationException(
                "Refresh token validation requires actual token parsing.",
                Response.Status.NOT_IMPLEMENTED);
    }

    public void logout(String token) {
    }

    public UserDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException("User not found", Response.Status.NOT_FOUND));
        return userMapper.toDTO(user);
    }

    @Transactional
    public TokenResponse googleLogin(String idToken) {
        GoogleAuthClient.GoogleTokenInfo info = googleAuthClient.verifyToken(idToken);

        if (info == null || info.email() == null) {
            throw new WebApplicationException("Invalid Google token", Response.Status.UNAUTHORIZED);
        }

        Optional<User> existingUser = userRepository.findByEmail(info.email());
        User user;
        if (existingUser.isEmpty()) {
            user = User.create(org.project.domain.enums.UserType.STUDENT, info.name(), info.email(), "", "");
            user = userRepository.save(user);
        } else {
            user = existingUser.get();
        }
        return generateTokenResponse(user);
    }

    @Transactional
    public TokenResponse firebaseLogin(String idToken, org.project.domain.enums.UserType userType) {
        try {
            FirebaseToken decodedToken = firebaseAuth.get().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String name = (String) decodedToken.getClaims().get("name");

            Optional<User> existingUser = userRepository.findByEmail(email);
            User user;
            if (existingUser.isEmpty()) {
                String firebaseName = (String) decodedToken.getClaims().get("name");
                if (firebaseName == null)
                    firebaseName = decodedToken.getName();

                user = User.create(userType != null ? userType : org.project.domain.enums.UserType.STUDENT,
                        firebaseName != null ? firebaseName : "Firebase User",
                        email, "", "");
                user = userRepository.save(user);
            } else {
                user = existingUser.get();
            }

            return generateTokenResponse(user);
        } catch (FirebaseAuthException e) {
            throw new WebApplicationException("Invalid Firebase token", Response.Status.UNAUTHORIZED);
        }
    }

    private TokenResponse generateTokenResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken, 3600L, userMapper.toDTO(user));
    }
}
