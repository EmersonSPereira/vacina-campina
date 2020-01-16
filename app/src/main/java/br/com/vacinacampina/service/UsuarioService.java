package br.com.vacinacampina.service;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.URI;

import br.com.vacinacampina.R;
import br.com.vacinacampina.config.FirebaseConfig;

public class UsuarioService {

    private static final String TAG_ATUALIZAR_USUARIO = "Atualizar Usuário";

    public static FirebaseUser getUsuarioLogado(){
        return FirebaseConfig.getAuth().getCurrentUser();
    }

    public static void atualizarUsuario(String nome, final Context context){


        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();
        getUsuarioLogado().updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,
                            context.getResources().getString(R.string.sucesso_atualizar_dados),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void atualizarUsuario(String nome, Uri uri,final Context context){

        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .setPhotoUri(uri).build();
       getUsuarioLogado().updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e(TAG_ATUALIZAR_USUARIO,  context.getResources().getString(R.string.enviado_email_verificacao), task.getException());

                }
            }
        });
    }

    public static void deslogarUsuario(){

        FirebaseConfig.getAuth().signOut();
    }
}
