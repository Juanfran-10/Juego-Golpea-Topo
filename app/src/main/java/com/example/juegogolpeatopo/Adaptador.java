package com.example.juegogolpeatopo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder>{
    private Context context;
    private List<Usuario> usuarioList;

    //Constructor
    public Adaptador(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        CircleImageView imagenJugador;
        TextView nombreJugador, correoJugador, puntosJugador;

        public MyHolder(@NonNull View itemView){
            super(itemView);
            //Inicializamos
            imagenJugador = itemView.findViewById(R.id.imagenJugador);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            correoJugador = itemView.findViewById(R.id.correoJugador);
            puntosJugador = itemView.findViewById(R.id.puntosJugador);
        }
    }

    @androidx.annotation.NonNull
    @Override
    //Inflamos el diseño
    public MyHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyHolder holder, int i) {
        //Obtenemos los datos del modelo
        String imagen = usuarioList.get(i).getImagen();
        String nombre = usuarioList.get(i).getNombre();
        String correo = usuarioList.get(i).getCorreo();
        int topos = usuarioList.get(i).getTopos();
        //Convertimos a String los topos
        String textoTopos = String.valueOf(topos);

        //Datos del jugador
        holder.nombreJugador.setText(nombre);
        holder.correoJugador.setText(correo);
        holder.puntosJugador.setText(textoTopos);

        //Imagen del jugador
        try{
            //Si el usuario tiene foto de perfil
            Picasso.get().load(imagen).into(holder.imagenJugador);
        }catch (Exception e){
            //Si no tiene foto de perfil
        }
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }
}
