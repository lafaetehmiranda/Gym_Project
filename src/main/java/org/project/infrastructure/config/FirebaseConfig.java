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

/**
 * Configuration class for Firebase integration.
 */
@ApplicationScoped
public class FirebaseConfig {

    private static final Logger LOG = Logger.getLogger(FirebaseConfig.class);

    @ConfigProperty(name = "firebase.config.path")
    String firebaseConfigPath;

    @ConfigProperty(name = "firebase.storage.bucket")
    String storageBucket;

    @ConfigProperty(name = "firebase.config.base64", defaultValue = "")
    String firebaseConfigBase64;

    void onStart(@Observes StartupEvent ev) {
        initializeFirebase();
    }

    private synchronized void initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                LOG.info("Attempting to initialize Firebase Admin SDK...");

                InputStream serviceAccount;

                if (firebaseConfigBase64 != null && !firebaseConfigBase64.isBlank()) {
                    LOG.info("Using Firebase config from environment variable.");
                    byte[] decoded = Base64.getDecoder().decode(firebaseConfigBase64.trim());
                    serviceAccount = new ByteArrayInputStream(decoded);
                } else {
                    LOG.info("Loading Firebase config from classpath: " + firebaseConfigPath);
                    serviceAccount = Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream(firebaseConfigPath);
                }

                if (serviceAccount == null) {
                    LOG.error("Firebase config not found (no Base64 env var and file missing in classpath: "
                            + firebaseConfigPath + ")");
                    return;
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setStorageBucket(storageBucket)
                        .build();

                FirebaseApp.initializeApp(options);
                LOG.info("Firebase Admin SDK initialized successfully with bucket: " + storageBucket);
            }
        } catch (Exception e) {
            LOG.error("Error initializing Firebase Admin SDK", e);
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
}
