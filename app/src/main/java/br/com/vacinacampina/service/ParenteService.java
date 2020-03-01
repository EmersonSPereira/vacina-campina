package br.com.vacinacampina.service;

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

    public static void listarParentes(final List<Parente> Parentes, final AdapterParentes adapterParente) {

        Query query = ParenteService.getDatabaseReference().orderByChild(NOME);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Parentes.add(snapshot.getValue(Parente.class));
                }

                adapterParente.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static  void salvarAtualizarParente(Parente parente){

        if(parente.getId() != null){

        }
        parente.setId(getDatabaseReference().push().getKey());
        getDatabaseReference().child(parente.getId()).setValue(parente);
    }

    public static DatabaseReference getDatabaseReference() {

        return FirebaseConfig.getFirebaseDatabase().getReference(PARENTE).child(UsuarioService.getUsuarioLogado().getUid());
    }
    }
