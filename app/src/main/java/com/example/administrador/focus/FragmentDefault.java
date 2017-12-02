package com.example.administrador.focus;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDefault extends Fragment {

    private View rootView;
    private TextView peso,altura,imc;
    private ImageView somatotipo;

    public FragmentDefault() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_default, container, false);

        peso = rootView.findViewById(R.id.peso);
        altura = rootView.findViewById(R.id.estatura);
        imc = rootView.findViewById(R.id.imc);
        somatotipo = rootView.findViewById(R.id.somatotipo);
        mostrar_caracteristicas();
        return rootView;
    }

    private void mostrar_caracteristicas(){
        try {
            SQLHelper db=new SQLHelper(this.getContext());
            db.opendb();
            Cursor datos = db.checkOpenSession();
            if(datos.moveToFirst()){
                String usuario_activo = datos.getString(datos.getColumnIndex("nombre_usu"));
                datos = db.consult_user(usuario_activo);
                if(datos.moveToFirst()){
                    peso.setText(datos.getString(datos.getColumnIndex("peso")));
                    altura.setText(datos.getString(datos.getColumnIndex("estatura")));
                    imc.setText(datos.getString(datos.getColumnIndex("imc")));
                    switch (datos.getString(datos.getColumnIndex("id_somatotipo_somatotipos"))){
                        case "1":
                            somatotipo.setImageResource(R.drawable.ectomorfo);
                            break;
                        case "2":
                            somatotipo.setImageResource(R.drawable.mesomorfo);
                            break;
                        case "3":
                            somatotipo.setImageResource(R.drawable.endomorfo);
                            break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
