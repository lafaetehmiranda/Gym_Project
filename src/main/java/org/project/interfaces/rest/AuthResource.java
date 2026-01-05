package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.project.application.dto.*;
import org.project.application.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

/**
 * REST resource for Authentication and Authorization.
 */
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication and Authorization endpoints")
public class AuthResource {

    private final AuthService authService;
    private final JsonWebToken jwt;

    @Inject
    public AuthResource(AuthService authService, JsonWebToken jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @POST
    @Path("/register")
    @PermitAll
    @Operation(summary = "Register a new user")
    public Response register(RegisterRequest request) {
        UserDTO user = authService.register(request);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Login with email and password")
    public Response login(LoginRequest request) {
        TokenResponse response = authService.login(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/refresh")
    @PermitAll
    @Operation(summary = "Refresh access token")
    public Response refresh(RefreshTokenRequest request) {
        TokenResponse response = authService.refresh(request.refreshToken());
        return Response.ok(response).build();
    }

    @POST
    @Path("/logout")
    @RolesAllowed({ "STUDENT", "TRAINER", "ADMIN" })
    @Operation(summary = "Logout user")
    public Response logout(@HeaderParam("Authorization") String token) {
        authService.logout(token);
        return Response.noContent().build();
    }

    @GET
    @Path("/profile")
    @RolesAllowed({ "STUDENT", "TRAINER", "ADMIN" })
    @Operation(summary = "Get current user profile")
    public Response profile() {
        String email = jwt.getName();
        UserDTO user = authService.getProfile(email);
        return Response.ok(user).build();
    }

    @POST
    @Path("/google/login")
    @PermitAll
    @Operation(summary = "Login with Google ID Token")
    public Response googleLogin(GoogleLoginRequest request) {
        TokenResponse response = authService.googleLogin(request.idToken());
        return Response.ok(response).build();
    }

    @POST
    @Path("/google/verify-token")
    @PermitAll
    @Operation(summary = "Verify Google ID Token")
    public Response verifyGoogleToken(GoogleTokenVerifyRequest request) {
        TokenResponse response = authService.googleLogin(request.idToken());
        return Response.ok(response).build();
    }

    @POST
    @Path("/firebase/login")
    @PermitAll
    @Operation(summary = "Login with Firebase ID Token")
    public Response firebaseLogin(FirebaseLoginRequest request) {
        TokenResponse response = authService.firebaseLogin(request.idToken());
        return Response.ok(response).build();
    }
}
