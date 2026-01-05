package org.project.infrastructure.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import java.util.Optional;

/**
 * Configuration class for Firebase integration.
 */
@ApplicationScoped
public class FirebaseConfig {

    private static final Logger LOG = Logger.getLogger(FirebaseConfig.class);

    @ConfigProperty(name = "firebase.config.path")
    Optional<String> firebaseConfigPath;

    @ConfigProperty(name = "firebase.storage.bucket")
    String storageBucket;

    @ConfigProperty(name = "firebase.config.base64")
    Optional<String> firebaseConfigBase64;

    void onStart(@Observes StartupEvent ev) {
        initializeFirebase();
    }

    private synchronized void initializeFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
            LOG.info("Attempting to initialize Firebase Admin SDK...");

            InputStream serviceAccount = null;

            if (firebaseConfigBase64.isPresent() && !firebaseConfigBase64.get().isBlank()) {
                String b64 = firebaseConfigBase64.get().trim();
                if (b64.startsWith("{")) {
                    LOG.info("Firebase config in env appears to be raw JSON.");
                    serviceAccount = new ByteArrayInputStream(b64.getBytes());
                } else {
                    LOG.info("Decoding Firebase config from Base64.");
                    try {
                        byte[] decoded = Base64.getMimeDecoder().decode(b64);
                        serviceAccount = new ByteArrayInputStream(decoded);
                    } catch (Exception e) {
                        LOG.error("Failed to decode Base64 config.", e);
                    }
                }
            }

            if (serviceAccount == null && firebaseConfigPath.isPresent()) {
                String path = firebaseConfigPath.get();
                LOG.info("Attempting to load Firebase config from: " + path);

                // 1. Try context classloader
                serviceAccount = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                if (serviceAccount != null) {
                    LOG.info("Found config in context classloader.");
                }

                // 2. Try class resource (absolute)
                if (serviceAccount == null) {
                    serviceAccount = FirebaseConfig.class.getResourceAsStream("/" + path);
                    if (serviceAccount != null)
                        LOG.info("Found config in class resource (absolute).");
                }

                // 3. Try class resource (relative - unlikely but safe to try)
                if (serviceAccount == null) {
                    serviceAccount = FirebaseConfig.class.getResourceAsStream(path);
                    if (serviceAccount != null)
                        LOG.info("Found config in class resource (relative).");
                }

                // 4. Try filesystem (src/main/resources) - Good for local dev if classpath is
                // weird
                if (serviceAccount == null) {
                    try {
                        java.io.File file = new java.io.File("src/main/resources/" + path);
                        if (file.exists()) {
                            serviceAccount = new java.io.FileInputStream(file);
                            LOG.info("Found config in src/main/resources file system.");
                        }
                    } catch (Exception ignored) {
                    }
                }

                // 5. Try filesystem (root)
                if (serviceAccount == null) {
                    try {
                        java.io.File file = new java.io.File(path);
                        if (file.exists()) {
                            serviceAccount = new java.io.FileInputStream(file);
                            LOG.info("Found config in root file system.");
                        }
                    } catch (Exception ignored) {
                    }
                }
            }

            if (serviceAccount == null) {
                LOG.error("Failed to locate Firebase config file: " + firebaseConfigPath.orElse("null")
                        + ". Checked classpath and filesystem.");
                throw new RuntimeException("Firebase configuration file not found. Please ensure "
                        + firebaseConfigPath.orElse("null") + " is in the classpath or root directory.");
            }

            try {
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .setStorageBucket(storageBucket)
                        .build();

                FirebaseApp.initializeApp(options);
                LOG.info("Firebase Admin SDK initialized successfully.");
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to initialize Firebase: I/O Error reading credentials.", e);
            }
        }
    }

    @Produces
    @Singleton
    public Bucket produceBucket() {
        if (FirebaseApp.getApps().isEmpty()) {
            initializeFirebase();
        }

        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException(
                    "FirebaseApp has not been initialized. Check logs for errors during initialization.");
        }

        return StorageClient.getInstance().bucket();
    }

    @Produces
    @Singleton
    public com.google.firebase.auth.FirebaseAuth produceAuth() {
        if (FirebaseApp.getApps().isEmpty()) {
            initializeFirebase();
        }

        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException(
                    "FirebaseApp has not been initialized. Check logs for errors during initialization.");
        }

        return com.google.firebase.auth.FirebaseAuth.getInstance();
    }
}
