package com.example.administrador.focus;

import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrador on 29/11/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolderDatos> {

    Cursor datos;

    public Adapter(Cursor datos) {
        this.datos = datos;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        datos.moveToPosition(position);
        holder.asignarDatos(datos);
    }

    @Override
    public int getItemCount() {
        return datos.getCount();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        View v;
        TextView nombre;
        ImageView image;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            v = itemView;
            nombre=itemView.findViewById(R.id.nombre_ejercicio);
            image=itemView.findViewById(R.id.imagen_gif);
        }

        public void asignarDatos(Cursor datos) {
            try {
                AnimationDrawable animation;
                nombre.setText(datos.getString(datos.getColumnIndex("nombre")));
                String animacion = datos.getString(datos.getColumnIndex("animacion"));
                image.setBackgroundResource(v.getResources().getIdentifier(animacion,"drawable",v.getContext().getPackageName()));
                animation = (AnimationDrawable) image.getBackground();
                animation.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
