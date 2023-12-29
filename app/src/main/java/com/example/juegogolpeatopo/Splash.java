package com.example.juegogolpeatopo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int DURACION_SPLASH = 3000;
        //Ejecuta la pantalla de carga según el valor que tengamos definido en la variable DURACION_SPLASH
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Abre la pantalla de carga(Splash)
                Intent intent = new Intent(Splash.this, Menu.class);
                startActivity(intent);
            }
        }, DURACION_SPLASH);
    }
}