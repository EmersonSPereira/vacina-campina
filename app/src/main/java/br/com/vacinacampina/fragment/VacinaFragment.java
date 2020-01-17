package br.com.vacinacampina.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.adapter.AdapterVacina;
import br.com.vacinacampina.model.Vacina;
import br.com.vacinacampina.service.VacinaService;

/**
 * A simple {@link Fragment} subclass.
 */
public class VacinaFragment extends Fragment {

    public static final String BUSCAR_VACINA = "Buscar Vacina";
    private SearchView searchViewPesquisa;
    private RecyclerView recyclerViewVacina;

    private AdapterVacina adapterVacina;

    List<Vacina> vacinas = new ArrayList<>();

    public VacinaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacina, container, false);

        searchViewPesquisa = view.findViewById(R.id.searchView_vacina);
        recyclerViewVacina = view.findViewById(R.id.recycleView_vacina);
        configurarRecycleView(view);
        configurarSearchView();
        VacinaService.listarVacinas(vacinas,adapterVacina);


        return view;
    }

    private void configurarSearchView() {
        //Configurando Search View
        searchViewPesquisa.setQueryHint(BUSCAR_VACINA);
        searchViewPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String textoDigitado) {

                VacinaService.consultarVacinaPorNome(textoDigitado,vacinas,adapterVacina);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String textoDigitado) {

                VacinaService.consultarVacinaPorNome(textoDigitado,vacinas,adapterVacina);

                return true;
            }
        });
    }

    private void configurarRecycleView(View view) {
        //Configurando Recycleview

        recyclerViewVacina.setHasFixedSize(true);
        recyclerViewVacina.setLayoutManager(new LinearLayoutManager(getContext()));


        adapterVacina = new AdapterVacina(vacinas, getActivity());
        recyclerViewVacina.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL));
        recyclerViewVacina.setAdapter(adapterVacina);
    }

    private List<Vacina> consultarVacinaPorNome(String textoDigitado) {
        if (!textoDigitado.isEmpty()) {

            Query query = VacinaService.getDatabaseReference().orderByChild("nome")
                    .startAt(textoDigitado.toUpperCase()).endAt(textoDigitado.toLowerCase() + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        vacinas.add(snapshot.getValue(Vacina.class));
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return vacinas;
    }



}
