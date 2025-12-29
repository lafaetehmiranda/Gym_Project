package org.project.infrastructure.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "google-auth-api")
public interface GoogleAuthClient {

    @GET
    @Path("/tokeninfo")
    GoogleTokenInfo verifyToken(@QueryParam("id_token") String idToken);

    record GoogleTokenInfo(
            String iss,
            String azp,
            String aud,
            String sub,
            String email,
            String email_verified,
            String name,
            String picture,
            String given_name,
            String family_name,
            String locale,
            String iat,
            String exp) {
    }
}
