package br.com.vacinacampina.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfig {

    private static FirebaseAuth auth;
    private static StorageReference storageReference;

    public static FirebaseAuth getAuth() {

        if(auth == null ) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;

    }

    public static StorageReference getStorageReference(){
            if(storageReference == null){
                storageReference = FirebaseStorage.getInstance().getReference();
            }
            return storageReference;
    }


}
