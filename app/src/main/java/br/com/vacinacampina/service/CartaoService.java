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

import br.com.vacinacampina.adapter.AdapterCartao;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Cartao;

import static br.com.vacinacampina.service.VacinaService.NOME;

public class CartaoService {


    public static final String CARTAO = "cartao";

    /**
     * Salva o cartão com a lista de vacinas padrão disponilibilizada pelo SUS.
     * @param idProprietarioCartao
     */
    public static void salvarCartao(String idProprietarioCartao){

        for (Cartao cartao : Cartao.getVacinasSimples()) {
            cartao.setId(getDatabaseReference().child(idProprietarioCartao).push().getKey());
            getDatabaseReference().child(idProprietarioCartao).child(cartao.getId()).setValue(cartao);
        }
    }

    public static void listarcartoes(final List<Cartao> cartoes,
                                      final AdapterCartao adapterCartao,
                                      final ProgressBar progressBarcartoes,
                                      final String idProprietarioCartao) {

        cartoes.clear();
        Query query = getDatabaseReference().child(idProprietarioCartao).orderByChild(NOME);
        progressBarcartoes.setVisibility(View.VISIBLE);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cartoes.add(snapshot.getValue(Cartao.class));
                }

                adapterCartao.notifyDataSetChanged();
                progressBarcartoes.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBarcartoes.setVisibility(View.GONE);

            }
        });

    }

    public static DatabaseReference getDatabaseReference() {

        return FirebaseConfig.getFirebaseDatabase().getReference(CARTAO);
    }
}
