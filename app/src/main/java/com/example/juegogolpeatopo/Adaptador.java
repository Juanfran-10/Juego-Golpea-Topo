package com.example.juegogolpeatopo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder> implements Filterable {
    private Context context;
    private List<Usuario> usuarioList;
    private List<Usuario> usuarioListFull; // Lista completa sin filtrar
    private Typeface tf;

    //Constructor
    public Adaptador(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
        this.usuarioListFull = new ArrayList<>(usuarioList); // Copia de la lista completa
        this.tf = Typeface.createFromAsset(context.getAssets(), "fuentes/topo.TTF");
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView imagenJugador;
        TextView nombreJugador, correoJugador, puntosJugador, edadJugador, paisJugador;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //Inicializamos
            imagenJugador = itemView.findViewById(R.id.imagenJugador);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            correoJugador = itemView.findViewById(R.id.correoJugador);
            puntosJugador = itemView.findViewById(R.id.puntosJugador);
            edadJugador = itemView.findViewById(R.id.edadJugador);
            paisJugador = itemView.findViewById(R.id.paisJugador);
        }
    }

    @NonNull
    @Override
    //Inflamos el diseño
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        //Obtenemos los datos del modelo
        final String imagen = usuarioList.get(i).getImagen();
        final String nombre = usuarioList.get(i).getNombre();
        final String correo = usuarioList.get(i).getCorreo();
        final String edad = usuarioList.get(i).getEdad();
        final String pais = usuarioList.get(i).getPais();
        int topos = usuarioList.get(i).getTopos();
        //Convertimos a String los topos
        final String textoTopos = String.valueOf(topos);

        //Datos del jugador
        holder.nombreJugador.setText(nombre);
        holder.correoJugador.setText(correo);
        holder.puntosJugador.setText(textoTopos);
        holder.edadJugador.setText(edad);
        holder.paisJugador.setText(pais);

        //Cambiamos el tipo de letra
        holder.nombreJugador.setTypeface(tf);
        holder.correoJugador.setTypeface(tf);
        holder.puntosJugador.setTypeface(tf);
        holder.edadJugador.setTypeface(tf);
        holder.paisJugador.setTypeface(tf);

        //Imagen del jugador
        try {
            //Si el usuario tiene foto de perfil
            Picasso.get().load(imagen).into(holder.imagenJugador);
        } catch (Exception e) {
        }

        //Al hacer click en un jugador
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleJugador.class);

                intent.putExtra("Imagen", imagen);
                intent.putExtra("Nombre", nombre);
                intent.putExtra("Correo", correo);
                intent.putExtra("Puntos", textoTopos);
                intent.putExtra("Pais", pais);
                intent.putExtra("Edad", edad);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    // Implementa los métodos de Filterable

    @Override
    public Filter getFilter() {
        return usuarioFilter;
    }

    private Filter usuarioFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Usuario> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Si no hay texto de búsqueda, muestra la lista completa
                filteredList.addAll(usuarioListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                // Filtra según el nombre u otros criterios que desees
                for (Usuario usuario : usuarioListFull) {
                    if (usuario.getNombre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(usuario);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usuarioList.clear();
            usuarioList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
