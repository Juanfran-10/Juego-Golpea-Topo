package com.example.juegogolpeatopo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleJugador extends AppCompatActivity {

    private ImageView imagenDetalle;
    private TextView titulo, nombreDetalle, correoDetalle, edadDetalle, paisDetalle, puntosDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_jugador);

        //Inicializamos la variables
        imagenDetalle = findViewById(R.id.imagenDetalle);
        titulo = findViewById(R.id.titulo);
        nombreDetalle = findViewById(R.id.nombreDetalle);
        correoDetalle = findViewById(R.id.correoDetalle);
        edadDetalle = findViewById(R.id.edadDetalle);
        paisDetalle = findViewById(R.id.paisDetalle);
        puntosDetalle = findViewById(R.id.puntosDetalles);

        String imagen = getIntent().getStringExtra("Imagen");
        String nombre = getIntent().getStringExtra("Nombre");
        String correo = getIntent().getStringExtra("Correo");
        String edad = getIntent().getStringExtra("Edad");
        String pais = getIntent().getStringExtra("Pais");
        String puntos = getIntent().getStringExtra("Puntos");

        nombreDetalle.setText("Nombre -> " + nombre);
        correoDetalle.setText("Correo -> " + correo);
        edadDetalle.setText("Edad -> " + edad);
        paisDetalle.setText("Pais -> " + pais);
        puntosDetalle.setText("Puntuacion -> " + puntos);

        //Gestionamos la imagen
        try {
            Picasso.get().load(imagen).into(imagenDetalle);
        } catch (Exception e) {
            //Picasso.get().load(R.drawable.mario).into(imagenDetalle);
        }

        //Cambio de letra
        cambioDeFuente();

    }

    private void cambioDeFuente() {
        //Cambiamos la letra
        String ubicacion = "fuentes/topo.TTF";
        Typeface tf = Typeface.createFromAsset(DetalleJugador.this.getAssets(), ubicacion);

        titulo.setTypeface(tf);
        nombreDetalle.setTypeface(tf);
        correoDetalle.setTypeface(tf);
        edadDetalle.setTypeface(tf);
        paisDetalle.setTypeface(tf);
        puntosDetalle.setTypeface(tf);
    }
}