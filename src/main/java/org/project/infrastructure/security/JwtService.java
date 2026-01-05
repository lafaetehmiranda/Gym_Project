package org.project.infrastructure.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.project.domain.model.User;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * Service for generating and managing JWT tokens.
 */
@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateAccessToken(User user) {
        Set<String> roles = new HashSet<>();
        roles.add(user.getUserType().name());

        return Jwt.issuer(issuer)
                .upn(user.getEmail())
                .groups(roles)
                .claim("userId", user.getId().toString())
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    public String generateRefreshToken(User user) {
        return Jwt.issuer(issuer)
                .upn(user.getEmail())
                .claim("userId", user.getId().toString())
                .claim("type", "refresh")
                .expiresIn(Duration.ofDays(7))
                .sign();
    }
}
