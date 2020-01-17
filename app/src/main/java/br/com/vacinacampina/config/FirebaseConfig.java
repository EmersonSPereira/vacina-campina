package br.com.vacinacampina.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfig {

    private static FirebaseAuth auth;
    private static StorageReference storageReference;
    private static FirebaseDatabase firebaseDatabase;


    public static FirebaseAuth getAuth() {

        if(auth == null ) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;

    }

    public static FirebaseDatabase getFirebaseDatabase(){
        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return firebaseDatabase;
    }

    public static StorageReference getStorageReference(){
            if(storageReference == null){
                storageReference = FirebaseStorage.getInstance().getReference();
            }
            return storageReference;
    }


}
