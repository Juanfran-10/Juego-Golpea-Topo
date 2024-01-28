package com.example.juegogolpeatopo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu extends AppCompatActivity {
    //Creación de variables
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference jugadores;
    private TextView txtMiPerfil;
    private TextView txtMiPuntuacion;
    private TextView txtTopos;
    private TextView txtUid;
    private TextView txtCorreo;
    private TextView txtNombre;
    private TextView txtEdad;
    private TextView txtPais;
    private TextView txtFechaM;
    private TextView txtMenu;
    private Button btJugar;
    private Button btEditarDatos;
    private Button btCambiarPassword;
    private Button btPuntuaciones;
    private Button btAcercaDe;
    private Button btCerrarSesion;
    private Button btBorrarJugador;
    private CircleImageView imagenPerfil;
    private StorageReference referenciaAlmacenamiento;
    private String rutaAlmacenamiento = "FotosDePerfil/*";
    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO = 200;
    private static final int CODIGO_PARA_LA_SELECCION_DE_LA_IMAGEN = 300;
    private String[] permisosAlmacenamiento;
    private Uri imagenUri;
    private String perfil;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Enlazamos la variables
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        jugadores = firebaseDatabase.getReference("JUGADORES");

        //Ubicación
        String ubicacion = "fuentes/topo.TTF";
        //Cambiar el tipo de letra
        Typeface tf = Typeface.createFromAsset(Menu.this.getAssets(), ubicacion);

        txtMiPerfil = (TextView) findViewById(R.id.txtMiPerfil);
        txtMiPuntuacion = (TextView) findViewById(R.id.txtMiPuntuacion);
        txtTopos = (TextView) findViewById(R.id.txtTopos);
        txtUid = (TextView) findViewById(R.id.txtUid);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtEdad = (TextView) findViewById(R.id.txtEdad);
        txtPais = (TextView) findViewById(R.id.txtPais);
        txtFechaM = (TextView) findViewById(R.id.txtFechaM);
        txtMenu = (TextView) findViewById(R.id.txtMenu);

        btJugar = (Button) findViewById(R.id.btJugar);
        btEditarDatos = (Button) findViewById(R.id.btEditarDatos);
        btCambiarPassword = (Button) findViewById(R.id.btCambiarPassword);
        btPuntuaciones = (Button) findViewById(R.id.btPuntuaciones);
        btAcercaDe = (Button) findViewById(R.id.btAcercaDe);
        btBorrarJugador = (Button) findViewById(R.id.btBorrarJugador);
        btCerrarSesion = (Button) findViewById(R.id.btCerrarSesion);
        imagenPerfil = (CircleImageView) findViewById(R.id.imagenPerfil);

        referenciaAlmacenamiento = FirebaseStorage.getInstance().getReference();

        dialog = new Dialog(Menu.this);

        //Cambiamos los tipos de letra
        txtMiPerfil.setTypeface(tf);
        txtMiPuntuacion.setTypeface(tf);
        txtTopos.setTypeface(tf);
        txtUid.setTypeface(tf);
        txtCorreo.setTypeface(tf);
        txtNombre.setTypeface(tf);
        txtEdad.setTypeface(tf);
        txtPais.setTypeface(tf);
        txtFechaM.setTypeface(tf);
        txtMenu.setTypeface(tf);

        btJugar.setTypeface(tf);
        btEditarDatos.setTypeface(tf);
        btCambiarPassword.setTypeface(tf);
        btPuntuaciones.setTypeface(tf);
        btAcercaDe.setTypeface(tf);
        btBorrarJugador.setTypeface(tf);
        btCerrarSesion.setTypeface(tf);

        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, EscenarioJuego.class);

                String textoUid = txtUid.getText().toString();
                String textoNombre = txtNombre.getText().toString();
                String textoTopo = txtTopos.getText().toString();

                intent.putExtra("UID", textoUid);
                intent.putExtra("NOMBRE", textoNombre);
                intent.putExtra("TOPO", textoTopo);

                startActivity(intent);
                Toast.makeText(Menu.this, "Enviando parámetros", Toast.LENGTH_SHORT).show();
            }
        });

        btEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarDatos();
            }
        });

        btCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, CambioDePassword.class));
            }
        });

        btPuntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Puntos.class);
                startActivity(intent);
            }
        });

        btAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { acercaDe(tf); }
        });

        btBorrarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        btCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion();
            }
        });
    }

    //Se ejecuta cuando se abre la aplicación
    @Override
    protected void onStart() {
        UsuarioLogeado();
        super.onStart();
    }

    //Método para comprobar si el usuario ha iniciado sesión
    private void UsuarioLogeado() {
        if (user != null) {
            Consulta();
            Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(Menu.this, MainActivity.class));
            finish();
        }
    }

    //Método para cerrar sesión
    private void CerrarSesion() {
        auth.signOut();
        startActivity(new Intent(Menu.this, MainActivity.class));
        Toast.makeText(this, "Se cerró sesión correctamente", Toast.LENGTH_SHORT).show();
    }

    //Método para realizar la consulta
    private void Consulta() {
        //Consulta
        Query query = jugadores.orderByChild("Correo").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Obtención de los datos
                    String textoTopos = "" + ds.child("Topos").getValue();
                    String textoUid = "" + ds.child("Uid").getValue();
                    String textoCorreo = "" + ds.child("Correo").getValue();
                    String textoNombre = "" + ds.child("Nombre").getValue();
                    String textoEdad = "" + ds.child("Edad").getValue();
                    String textoPais = "" + ds.child("Pais").getValue();
                    String textoFecha = "" + ds.child("Fecha").getValue();
                    String textoImagen = "" + ds.child("Imagen").getValue();

                    //Seteo datos de los TextView
                    txtTopos.setText(textoTopos);
                    txtUid.setText(textoUid);
                    txtCorreo.setText("Correo -> " + textoCorreo);
                    txtNombre.setText("Nombre -> " + textoNombre);
                    txtEdad.setText("Edad -> " + textoEdad);
                    txtPais.setText("Pais -> " + textoPais);
                    txtFechaM.setText("Se unio -> " + textoFecha);

                    try {
                        Picasso.get().load(textoImagen).into(imagenPerfil);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.mario).into(imagenPerfil);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Método para cambiar los datos
    private void EditarDatos() {
        //Definimos un array con las opciones a elegir
        String[] opciones = {"Foto de perfil", "Cambiar edad", "Cambiar pais"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0:
                        perfil = "Imagen";
                        ActualizarFotoPerfil();
                        break;
                    case 1:
                        ActualizarValor("Edad");
                        break;
                    case 2:
                        ActualizarValor("Pais");
                        break;
                }
            }
        });
        builder.create().show();
    }

    //En el siguiente
    private void ActualizarFotoPerfil() {
        String[] opciones = {"Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen de: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    ElegirImagenDeGaleria();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO:
                if (grantResults.length > 0) {
                    boolean escrituraDeAlmacenamientoAceptado = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (escrituraDeAlmacenamientoAceptado) {
                        //Permiso habilitado
                        ElegirImagenDeGaleria();
                    } else {
                        Toast.makeText(this, "Habilite el permiso a la galería", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Se llama cuando el jugador ya ha elegido la imagen de la galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            //Obtenemos la uri de la imagen
            imagenUri = data.getData();
            SubirFoto(imagenUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Cambia la foto de perfil del jugador y actualiza la informacion en la base de datos
    private void SubirFoto(Uri imgUri) {
        String rutaArchivo = rutaAlmacenamiento + "" + perfil + "" + user.getUid();
        StorageReference storageReference = referenciaAlmacenamiento.child(rutaArchivo);
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri uri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            HashMap<String, Object> resultado = new HashMap<>();
                            resultado.put(perfil, uri.toString());
                            jugadores.child(user.getUid()).updateChildren(resultado)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Menu.this, "La imagen ha sido cambiada correctamente", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Menu.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(Menu.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Menu.this, "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ElegirImagenDeGaleria() {
        Intent intentGaleria = new Intent(Intent.ACTION_PICK);
        intentGaleria.setType("image/*");
        startActivityForResult(intentGaleria, CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);
    }

    private void ActualizarValor(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar: " + key);
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10, 10, 10, 10);
        EditText editText = new EditText(this);
        editText.setHint("Introduzca " + key);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        if(key.equalsIgnoreCase("Edad")){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        //Si hacemos click en actualizar
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> resultado = new HashMap<>();
                resultado.put(key, valor);
                jugadores.child(user.getUid()).updateChildren(resultado)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Menu.this, "Dato Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Menu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Menu.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void acercaDe(Typeface tf){
        TextView txtDesarrolladoPor, txtDevNombre;
        Button ok;

        dialog.setContentView(R.layout.acerca_de);

        txtDesarrolladoPor = dialog.findViewById(R.id.txtDesarrolladoPor);
        txtDevNombre = dialog.findViewById(R.id.txtDevNombre);
        ok = dialog.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtDesarrolladoPor.setTypeface(tf);
        txtDevNombre.setTypeface(tf);
        ok.setTypeface(tf);

        dialog.show();
    }

    private void mostrarDialogoConfirmacion() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de que desea borrar su perfil?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                borrarUsuario();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // No hacer nada, simplemente cerrar el diálogo
            }
        });

        builder.show();
    }

    private void borrarUsuario() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            // Obtener el UID del usuario
            String uid = user.getUid();

            // Eliminar usuario de Firebase Authentication
            user.delete().addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("JUGADORES").child(uid);
                    userRef.removeValue().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();

                            // Cerrar sesión después de borrar el usuario
                            auth.signOut();
                            startActivity(new Intent(Menu.this, MainActivity.class));
                        } else {
                            Toast.makeText(this, "Error al borrar los datos del usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Error al borrar el usuario", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}