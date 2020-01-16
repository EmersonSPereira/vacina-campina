package br.com.vacinacampina.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.com.vacinacampina.R;
import br.com.vacinacampina.fragment.CartaoFragment;
import br.com.vacinacampina.fragment.ContaFragment;
import br.com.vacinacampina.fragment.PostosFragment;
import br.com.vacinacampina.fragment.VacinaFragment;

public class TelaPrincipalActivity extends AppCompatActivity {
    public static final int PRIMEIRO_ITEM_MENU = 0;
    BottomNavigationViewEx bottomNavigationViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        //Habilitando a navegação do menu
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        habilitarNavegacao(bottomNavigationViewEx);
        setFragmentInicial();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void habilitarNavegacao(BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){

                    case R.id.navigation_cartao:
                        fragmentTransaction.replace(R.id.frameLayout_principal, new CartaoFragment()).commit();
                        return true;
                        case R.id.navigation_vacinas:
                        fragmentTransaction.replace(R.id.frameLayout_principal, new VacinaFragment()).commit();
                        return true;
                        case R.id.navigation_mapa_postos:
                        fragmentTransaction.replace(R.id.frameLayout_principal, new PostosFragment()).commit();
                        return true;
                        case R.id.navigation_usuarios:
                        fragmentTransaction.replace(R.id.frameLayout_principal, new ContaFragment()).commit();
                        return true;

                        default: return false;

                }
            }
        });
    }

    private void setFragmentInicial(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_principal, new CartaoFragment()).commit();
        bottomNavigationViewEx.getMenu().getItem(PRIMEIRO_ITEM_MENU).setChecked(true);
    }



}
