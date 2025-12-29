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

import java.util.Optional;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Inject
    @RestClient
    GoogleAuthClient googleAuthClient;

    @Inject
    public AuthService(UserRepository userRepository, JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new WebApplicationException("Email already exists", Response.Status.CONFLICT);
        }

        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        User user = User.create(request.userType(), request.name(), request.email(), hashedPassword,
                request.phoneNumber());
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
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

    private TokenResponse generateTokenResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken, 3600L, userMapper.toDTO(user));
    }
}
