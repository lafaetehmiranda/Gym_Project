package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.project.infrastructure.service.FirebaseStorageService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * REST resource for file storage operations.
 */
@Path("/storage")
@Tag(name = "Storage")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StorageResource {

    @Inject
    FirebaseStorageService storageService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@RestForm("file") FileUpload file) {
        try {
            if (file == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("File is missing").build();
            }

            InputStream inputStream = new FileInputStream(file.filePath().toFile());
            String fileName = file.fileName();
            String contentType = file.contentType();

            String fileUrl = storageService.uploadFile(inputStream, fileName, contentType);

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("fileName", fileName);

            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error uploading file: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{fileName}")
    public Response deleteFile(@PathParam("fileName") String fileName) {
        boolean deleted = storageService.deleteFile(fileName);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("File not found or could not be deleted").build();
        }
    }
}
