package com.example.juegogolpeatopo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class CambioDePassword extends AppCompatActivity {
    private EditText actualPassword, nuevoPassword;
    private Button cambiarPassword;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_de_password);
        //Enlazamos las variables
        actualPassword = findViewById(R.id.actualPassword);
        nuevoPassword = findViewById(R.id.nuevoPassword);
        cambiarPassword = findViewById(R.id.cambiarPassword);

        //Conectamos con la base de datos
        reference = FirebaseDatabase.getInstance().getReference("JUGADORES");
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actual = actualPassword.getText().toString().trim();
                String nueva = nuevoPassword.getText().toString().trim();

                if (TextUtils.isEmpty(actual)) {
                    Toast.makeText(CambioDePassword.this, "Debes introducir la contraseña actual", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(nueva)) {
                    Toast.makeText(CambioDePassword.this, "Debes introducir la nueva contraseña", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(actual) && !TextUtils.isEmpty(nueva) && actual.length() >= 6 && nueva.length() >= 6) {
                    cambioPassword(actual, nueva);
                }
            }
        });
    }

    private void cambioPassword(String actual, String nueva) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), actual);

        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(nueva)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        String value = nuevoPassword.getText().toString().trim();
                                        HashMap<String, Object> result = new HashMap<>();
                                        result.put("Password", value);
                                        reference.child(user.getUid()).updateChildren(result)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(CambioDePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        firebaseAuth.signOut();
                                        startActivity(new Intent(CambioDePassword.this, Login.class));
                                        Toast.makeText(CambioDePassword.this, "Se cerró sesión", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CambioDePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CambioDePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}