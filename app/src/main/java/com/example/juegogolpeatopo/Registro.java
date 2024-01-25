package com.example.juegogolpeatopo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Registro extends AppCompatActivity {
    //DECLARACION DE VARIABLES
    private EditText edtCorreo;
    private EditText edtPassword;
    private EditText edtNombre;
    private EditText edtEdad;
    private EditText edtPais;
    private TextView txtFecha;
    private Button btRegistroR;
    private FirebaseAuth auth; //FIREBASE AUTENTICACION
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //ENLAZAMOS LA VARIABLES
        edtCorreo = (EditText) findViewById(R.id.edtCorreo);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtEdad = (EditText) findViewById(R.id.edtEdad);
        edtPais = (EditText) findViewById(R.id.edtPais);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        btRegistroR = (Button) findViewById(R.id.btRegistroR);

        auth = FirebaseAuth.getInstance();

        //Ubicación
        String ubicacion = "fuentes/topo.TTF";
        //Cambiar el tipo de letra
        Typeface tf = Typeface.createFromAsset(Registro.this.getAssets(), ubicacion);

        btRegistroR.setTypeface(tf);
        txtFecha.setTypeface(tf);

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");//FORMATO PARA LA FECHA
        String textoFecha = fecha.format(date);
        txtFecha.setText(textoFecha);

        btRegistroR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = edtCorreo.getText().toString();
                String password = edtPassword.getText().toString();

                //VALIDACION PARA EL CORREO
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    edtCorreo.setError("Correo no válido");
                    edtCorreo.setFocusable(true);
                    //VALIDACION PARA LA CONTRASEÑA
                } else if (password.length() < 6) {
                    edtPassword.setError("La contraseña debe ser mayor a 6");
                    edtPassword.setFocusable(true);
                } else {
                    RegistrarJugador(correo, password);
                }
            }
        });

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setMessage("Registrando, espere por favor");
        progressDialog.setCancelable(false);
    }

    private void RegistrarJugador(String correo, String password) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //SI EL JUGADOR FUE REGISTRADO
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            int contador = 0;

                            assert user != null;//Que el usuario no es nulo
                            String textoUid = user.getUid();
                            String textoCorreo = edtCorreo.getText().toString();
                            String password = edtPassword.getText().toString();
                            String nombre = edtNombre.getText().toString();
                            String edad = edtEdad.getText().toString();
                            String pais = edtPais.getText().toString();
                            String fecha = txtFecha.getText().toString();

                            HashMap<Object, Object> datosJugador = new HashMap<>();

                            datosJugador.put("Uid", textoUid);
                            datosJugador.put("Correo", textoCorreo);
                            datosJugador.put("Password", password);
                            datosJugador.put("Nombre", nombre);
                            datosJugador.put("Edad", edad);
                            datosJugador.put("Pais", pais);
                            datosJugador.put("Imagen", "");
                            datosJugador.put("Fecha", fecha);
                            datosJugador.put("Topos", contador);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("JUGADORES");//Nombre de la base de datos
                            reference.child(textoUid).setValue(datosJugador);
                            startActivity(new Intent(Registro.this, Menu.class));
                            Toast.makeText(Registro.this, "Usuario Registro Exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Registro.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                //SI FALLA EL REGISTRO
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}