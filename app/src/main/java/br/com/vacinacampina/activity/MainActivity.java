package br.com.vacinacampina.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.login.LoginActivity;

public class MainActivity extends IntroActivity {

    public static final String INTRO = "INTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!myPreferences.getBoolean(INTRO, false)) {
            exibeIntro(myPreferences);
        } else {
          //finaliza o slid e chama a activity de Login
          finish();
          startActivity(new Intent(this, LoginActivity.class));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                // Finished the intro
                // Cancelled the intro. You can then e.g. finish this activity too.
                finish();
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
