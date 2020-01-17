package br.com.vacinacampina.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.adapter.AdapterVacina;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Vacina;

public class VacinaService {

    public static final String VACINA = "Vacianas";
    public static final String NOME = "nome";
    private DatabaseReference vacinaReference;


    public static DatabaseReference getDatabaseReference() {

        return FirebaseConfig.getFirebaseDatabase().getReference(VACINA);
    }

    public static void consultarVacinaPorNome(String textoDigitado,final List<Vacina> vacinas, final AdapterVacina adapterVacina) {
       vacinas.clear();
        if (!textoDigitado.isEmpty()) {
            Query query = VacinaService.getDatabaseReference().orderByChild(NOME)
                    .startAt(textoDigitado.toUpperCase()).endAt(textoDigitado.toLowerCase() + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    vacinas.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        vacinas.add(snapshot.getValue(Vacina.class));
                    }


                    adapterVacina.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            listarVacinas(vacinas,adapterVacina);
        }

    }

    public static void listarVacinas(final List<Vacina> vacinas, final AdapterVacina adapterVacina) {

        Query query = VacinaService.getDatabaseReference().orderByChild(NOME);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    vacinas.add(snapshot.getValue(Vacina.class));
                }

                adapterVacina.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
