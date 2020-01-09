package br.com.vacinacampina.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import br.com.vacinacampina.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }
    public void btnEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
