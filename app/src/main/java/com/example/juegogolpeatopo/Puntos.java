package com.example.juegogolpeatopo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Puntos extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewUsuarios;
    private Adaptador adaptador;
    private List<Usuario> usuarioList;
    private FirebaseAuth firebaseAuth;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Puntuaciones");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);

        layoutManager.setReverseLayout(true); // Ordena de orden inverso
        layoutManager.setStackFromEnd(true);

        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(layoutManager);
        usuarioList = new ArrayList<>();

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Puedes realizar alguna acción cuando se envía la búsqueda (opcional)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra tu lista de usuarios según el texto de búsqueda
                adaptador.getFilter().filter(newText);
                return false;
            }
        });

        ObtenerListaUsuarios();
    }

    private void ObtenerListaUsuarios() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("JUGADORES");
        reference.orderByChild("Topos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarioList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);

                    /*if(!usuario.getUid().equals(user.getUid())){
                        usuarioList.add(usuario);
                    }*/

                    usuarioList.add(usuario);
                }

                // Verificar si hay datos antes de configurar el adaptador
                if (!usuarioList.isEmpty()) {
                    adaptador = new Adaptador(Puntos.this, usuarioList);
                    recyclerViewUsuarios.setAdapter(adaptador);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}