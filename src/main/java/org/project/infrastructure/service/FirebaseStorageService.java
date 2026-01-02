package org.project.infrastructure.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class FirebaseStorageService {

    private static final Logger LOG = Logger.getLogger(FirebaseStorageService.class);

    @Inject
    Bucket bucket;

    public String uploadFile(InputStream fileStream, String fileName, String contentType) {
        try {
            String blobName = UUID.randomUUID().toString() + "-" + fileName;
            Blob blob = bucket.create(blobName, fileStream, contentType);

            LOG.info("File uploaded successfully: " + blobName);

            // Generate a signed URL valid for 7 days
            return blob.signUrl(7, TimeUnit.DAYS).toString();
        } catch (Exception e) {
            LOG.error("Error uploading file to Firebase Storage", e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public boolean deleteFile(String blobName) {
        try {
            Blob blob = bucket.get(blobName);
            if (blob != null) {
                return blob.delete();
            }
            return false;
        } catch (Exception e) {
            LOG.error("Error deleting file from Firebase Storage: " + blobName, e);
            return false;
        }
    }
}
