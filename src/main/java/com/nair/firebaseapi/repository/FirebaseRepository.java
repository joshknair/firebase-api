package com.nair.firebaseapi.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import com.nair.firebaseapi.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FirebaseRepository {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    DatabaseReference database;
    Firestore db;

    public FirebaseRepository() {
        try {
            log.info("Connecting to firebase");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("https://fir-api-b3aa4.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

            database = FirebaseDatabase.getInstance().getReference();
            log.info("Connected to firebase, database::{}", database);
        } catch (Exception e) {
            log.error("Unable to initialize firebase.  Error::{}", e);
        }
    }

    public Customer findCustomerById(String uid) {
        Customer customer = null;
        try {
            ApiFuture<DocumentSnapshot> customerFuture = db.collection("customers").document(uid).get();

            DocumentSnapshot document = customerFuture.get();

            if (document.exists()) {
                log.info("Document exists. {}", document.toObject(Customer.class).toString());
                customer = document.toObject(Customer.class);
            } else {
                log.error("Document with id::{} does not exist", uid);
            }
        } catch (Exception e) {
            log.error("Unknown exception occured", e);
        }
        return customer;
    }
}


