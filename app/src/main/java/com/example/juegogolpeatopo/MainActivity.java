package com.example.juegogolpeatopo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegistro = (Button) findViewById(R.id.btRegistro);

        //Ubicación
        String ubicacion = "fuentes/topo.TTF";
        //Cambiar el tipo de letra
        Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(), ubicacion);

        //Cambiamos los tipos de letra
        btLogin.setTypeface(tf);
        btRegistro.setTypeface(tf);

        //Nos lleva a la pantalla del login
        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        //Nos lleva a la pantalla del registro
        this.btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }
}