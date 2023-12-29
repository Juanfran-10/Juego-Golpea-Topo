package com.example.juegogolpeatopo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    //Declaración de variables
    private EditText edtCorreoLogin;
    private EditText edtPasswordLogin;
    private Button btLoginL;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Enlazamos las variables
        edtCorreoLogin = (EditText) findViewById(R.id.edtCorreoLogin);
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        btLoginL = (Button) findViewById(R.id.btLoginL);
        auth = FirebaseAuth.getInstance();

        //Ubicación
        String ubicacion = "fuentes/topo.TTF";
        //Cambiar el tipo de letra
        Typeface tf = Typeface.createFromAsset(Login.this.getAssets(), ubicacion);

        btLoginL.setTypeface(tf);

        //Click en el boton de Login
        btLoginL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = edtCorreoLogin.getText().toString();
                String password = edtPasswordLogin.getText().toString();

                //VALIDACION PARA EL CORREO
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    edtCorreoLogin.setError("Correo no válido");
                    edtCorreoLogin.setFocusable(true);
                    //VALIDACION PARA LA CONTRASEÑA
                } else if (password.length() < 6) {
                    edtPasswordLogin.setError("La contraseña debe ser mayor a 6");
                    edtPasswordLogin.setFocusable(true);
                } else {
                    LoginJugador(correo, password);
                }
            }
        });
    }

    //Método para iniciar sesión al jugador
    private void LoginJugador(String correo, String password) {
        auth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(new Intent(Login.this, Menu.class));
                            assert user != null;//Confirmamos que el usuario no es nulo
                            Toast.makeText(Login.this, "Bienvenido/a " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    //Si falla el logeo nos muestra el mensaje
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}