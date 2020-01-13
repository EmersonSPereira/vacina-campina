package br.com.vacinacampina.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.vacinacampina.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VacinaFragment extends Fragment {


    public VacinaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vacina, container, false);
    }

}
