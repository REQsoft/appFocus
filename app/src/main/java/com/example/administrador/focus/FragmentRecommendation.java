package com.example.administrador.focus;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecommendation extends Fragment {

    TextView carbohidratos,proteinas,grasas,peso,numeroSeries,repeticiones,descansoSeries,horasSemana,diasSemena,divisionSesiones;
    View rootView;

    public FragmentRecommendation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recommendation, container, false);

        carbohidratos = rootView.findViewById(R.id.carbohidratos);
        proteinas = rootView.findViewById(R.id.proteinas);
        grasas = rootView.findViewById(R.id.grasas);
        peso = rootView.findViewById(R.id.peso);
        numeroSeries = rootView.findViewById(R.id.series);
        repeticiones = rootView.findViewById(R.id.repeticiones);
        descansoSeries = rootView.findViewById(R.id.descanso_series);
        horasSemana = rootView.findViewById(R.id.horas_semana);
        diasSemena = rootView.findViewById(R.id.dias_semana);
        divisionSesiones = rootView.findViewById(R.id.division_sesiones);

        mostrar_recomendaciones();

        return rootView;
    }

    private void mostrar_recomendaciones(){
        SQLHelper db=new SQLHelper(getContext());
        db.opendb();
        try {
            Cursor cursor = db.checkOpenSession();
            if(cursor.moveToFirst()){
                String usuario_activo = cursor.getString(cursor.getColumnIndex("nombre_usu"));
                //Dieta

                cursor = db.consultRecommendationDiet(usuario_activo);
                if(cursor.moveToFirst()){
                    carbohidratos.setText(cursor.getString(cursor.getColumnIndex("carbohidratos")));
                    proteinas.setText(cursor.getString(cursor.getColumnIndex("proteinas")));
                    grasas.setText(cursor.getString(cursor.getColumnIndex("grasas")));
                }

                //Intensidad
                cursor = db.consultRecommendationIntensity(usuario_activo);
                if(cursor.moveToFirst()){
                    String intensidad = cursor.getString(cursor.getColumnIndex("intensidad"));
                    Log.i("sql", String.valueOf(cursor.getCount()));
                    peso.setText(intensidad.split(":")[0]);
                    numeroSeries.setText(intensidad.split(":")[1]);
                    repeticiones.setText(intensidad.split(":")[2]);
                }

                //Tiempo

                cursor = db.consultRecommendationTime(usuario_activo);
                if(cursor.moveToFirst()){
                    horasSemana.setText(cursor.getString(cursor.getColumnIndex("horas_semana")));
                    diasSemena.setText(cursor.getString(cursor.getColumnIndex("dias_semana")));
                    divisionSesiones.setText(cursor.getString(cursor.getColumnIndex("division_tiempo")));
                }

                cursor = db.consultRecommendationRest(usuario_activo);
                if(cursor.moveToFirst()){
                    descansoSeries.setText(cursor.getString(cursor.getColumnIndex("tiempo")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
