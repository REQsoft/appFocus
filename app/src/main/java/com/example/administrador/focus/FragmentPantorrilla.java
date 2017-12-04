package com.example.administrador.focus;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPantorrilla extends Fragment {


    RecyclerView recyclerView;
    TextView parte_cuerpo;
    View rootView;

    public FragmentPantorrilla() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ejercicios, container, false);
        recyclerView=rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        parte_cuerpo=rootView.findViewById(R.id.parte_cuerpo);
        parte_cuerpo.setText(getResources().getString(R.string.pantorrilla));

        SQLHelper db=new SQLHelper(getContext());
        db.opendb();
        Cursor cursor = db.consultExercise("PANTORRILLA");
        Adapter adapter=new Adapter(cursor);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
