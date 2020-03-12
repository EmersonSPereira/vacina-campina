package br.com.vacinacampina.service;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import br.com.vacinacampina.adapter.AdapterParentes;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Parente;

import static br.com.vacinacampina.service.VacinaService.NOME;


public class ParenteService {

    public static final String PARENTE = "parente";

    public static void listarParentes(final List<Parente> parentes, final AdapterParentes adapterParente, final ProgressBar progressBarParentes) {

        parentes.clear();
        Query query = ParenteService.getDatabaseReference().orderByChild(NOME);
        progressBarParentes.setVisibility(View.VISIBLE);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    parentes.add(snapshot.getValue(Parente.class));
                }

                adapterParente.notifyDataSetChanged();
                progressBarParentes.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBarParentes.setVisibility(View.GONE);

            }
        });

    }

    public static  void salvarAtualizarParente(Parente parente){

        if(parente.getId() != null){
            getDatabaseReference().child(parente.getId()).setValue(parente);
        }else{
        parente.setId(getDatabaseReference().push().getKey());
        getDatabaseReference().child(parente.getId()).setValue(parente);}
    }

    public static void excluirParente(String id){
        getDatabaseReference().child(id).removeValue();
    }

    public static DatabaseReference getDatabaseReference() {

        return FirebaseConfig.getFirebaseDatabase().getReference(PARENTE).child(UsuarioService.getUsuarioLogado().getUid());
    }
    }
