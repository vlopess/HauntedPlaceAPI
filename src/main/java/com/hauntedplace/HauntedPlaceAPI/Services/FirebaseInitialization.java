package com.hauntedplace.HauntedPlaceAPI.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization() throws IOException {
        FileInputStream serviceAccount =
            new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("haunted-place-681c1.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);

    }
}
