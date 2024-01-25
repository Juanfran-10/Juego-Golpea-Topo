package com.example.juegogolpeatopo;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class EscenarioJuego extends AppCompatActivity {
    private TextView txtContador;
    private TextView txtNombreEJ;
    private TextView txtTiempo;
    private TextView txtAncho;
    private TextView txtAlto;
    private ImageView imgTopo;
    private String uids;
    private String nombres;
    private String topos;
    private int contador;
    private int puntos;
    private int anchoPantalla;
    private int altoPantalla;
    private Random aleatorio;
    private boolean gameOver = false;
    private Dialog miDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        txtContador = (TextView) findViewById(R.id.txtContador);
        txtNombreEJ = (TextView) findViewById(R.id.txtNombreEJ);
        txtTiempo = (TextView) findViewById(R.id.txtTiempo);
        txtAncho = (TextView) findViewById(R.id.txtAncho);
        txtAlto = (TextView) findViewById(R.id.txtAlto);
        imgTopo = (ImageView) findViewById(R.id.imgTopo);
        miDialog = new Dialog(EscenarioJuego.this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        jugadores = firebaseDatabase.getReference("JUGADORES");

        Bundle intent = getIntent().getExtras();
        uids = intent.getString("UID");
        nombres = intent.getString("NOMBRE");
        topos = intent.getString("TOPO");

        txtContador.setText(topos);
        txtNombreEJ.setText(nombres);

        Pantalla();
        CuentaAtras();

        //Al hacer click en la imagen
        imgTopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOver) {
                    contador++; //Aumenta en 1 cada vez que haga click en la imagen
                    txtContador.setText(String.valueOf(contador));//Cambiamos el valor del contador en el JTextField
                    imgTopo.setImageResource(R.drawable.topo_aplastado);

                    //Nos permite que la imagen vuelva a su estado original despues de un determinado tiempo
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgTopo.setImageResource(R.drawable.topo);
                            Movimiento();
                        }
                    }, 500);
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };

        // Agregar el callback al dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    //Método para obtener el tamaño de la pantalla
    private void Pantalla() {
        //Obtenemos el tamaño de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        anchoPantalla = point.x;
        altoPantalla = point.y;

        String ancho = String.valueOf(anchoPantalla);
        String alto = String.valueOf(altoPantalla);

        txtAncho.setText(ancho);
        txtAlto.setText(alto);

        aleatorio = new Random();
    }

    private void Movimiento() {
        int min = 0;
        //Cogemos las variables X Y y le restamos el alto y ancho para que no se salga de la pantalla
        int maximoX = anchoPantalla - imgTopo.getWidth();
        int maximoY = altoPantalla - imgTopo.getHeight();

        //Generamos numeros random para que la imagen se ubique aleatoriamente en la pantalla
        int randomX = aleatorio.nextInt(((maximoX - min) + 1) + min);
        int ramdomY = aleatorio.nextInt(((maximoY - min) + 1) + min);

        //Cambiamos las cordenadas X Y de la imagen con las posiciones aleatorias
        imgTopo.setX(randomX);
        imgTopo.setY(ramdomY);
    }

    //Método para hacer retroceder el tiempo
    private void CuentaAtras() {
        //Codigo cogido de la pagina de android
        new CountDownTimer(15000, 1000) {
            //Se ejecuta cada segundo
            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished / 1000;
                txtTiempo.setText(segundosRestantes + "S");
            }

            //Se ejecuta cuando acaba el tiempo
            public void onFinish() {
                txtTiempo.setText("0S");
                gameOver = true;
                MensajeGameOver();
                puntos += contador;
                GuardarResultados("Topos", puntos);
            }
        }.start();
    }

    private void MensajeGameOver(){
        String ubicacion = "fuentes/topo.TTF";
        Typeface typeface = Typeface.createFromAsset(EscenarioJuego.this.getAssets(), ubicacion);

        TextView txtSeAcaboTiempo, txtNumGolpeos, txtNum;
        Button btJugarDeNuevo, btIrMenu, btVerPuntos;

        miDialog.setContentView(R.layout.game_over);

        txtSeAcaboTiempo = miDialog.findViewById(R.id.txtSeAcaboTiempo);
        txtNumGolpeos = miDialog.findViewById(R.id.txtNumGolpeos);
        txtNum = miDialog.findViewById(R.id.txtNum);
        btJugarDeNuevo = miDialog.findViewById(R.id.btJugarDeNuevo);
        btIrMenu = miDialog.findViewById(R.id.btIrMenu);
        btVerPuntos = miDialog.findViewById(R.id.btVerPuntos);

        String topos = String.valueOf(contador);
        txtNum.setText(topos);

        //Cambiamos el tipo de letra
        txtSeAcaboTiempo.setTypeface(typeface);
        txtNumGolpeos.setTypeface(typeface);
        txtNum.setTypeface(typeface);
        btJugarDeNuevo.setTypeface(typeface);
        btIrMenu.setTypeface(typeface);
        btVerPuntos.setTypeface(typeface);

        btJugarDeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = 0;
                miDialog.dismiss();
                txtContador.setText("0");
                gameOver = false;
                CuentaAtras();
                Movimiento();
            }
        });

        btIrMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscenarioJuego.this, Menu.class));
            }
        });

        btVerPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscenarioJuego.this, Puntos.class));
            }
        });

        miDialog.show();
        miDialog.setCancelable(false);
    }

    private void GuardarResultados(String key, int topos){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, topos);
        jugadores.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EscenarioJuego.this, "El puntaje ha sido actualizado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}