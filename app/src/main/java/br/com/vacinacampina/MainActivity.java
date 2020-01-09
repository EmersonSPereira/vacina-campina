package br.com.vacinacampina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import br.com.vacinacampina.activity.CadastroActivity;
import br.com.vacinacampina.activity.LoginActivity;

public class MainActivity extends IntroActivity {

    public static final String INTRO = "INTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!myPreferences.getBoolean(INTRO, false)) {
            exibeIntro(myPreferences);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    private void exibeIntro(SharedPreferences myPreferences) {
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_blue_dark)
                .fragment(R.layout.slider_1)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_blue_dark)
                .fragment(R.layout.slider_2)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_blue_dark)
                .fragment(R.layout.slider_3)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_blue_dark)
                .canGoForward(false)
                .fragment(R.layout.slider_cadastro)
                .canGoForward(false)
                .build());
        SharedPreferences.Editor editorPreferencias = myPreferences.edit();
        editorPreferencias.putBoolean(INTRO, true);
        editorPreferencias.commit();

    }

    public void btnEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
