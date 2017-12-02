package com.example.administrador.focus;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConfiguracion extends Fragment {

    private EditText UserWeight, UserHeight;
    private TextView UserBMI;
    private String somatotype="";
    private ImageButton buttonEctomorfo,buttonMesomorfo,buttonEndomorfo;
    private CheckBox checkEctomorfo,checkMesomorfo,checkEndomorfo;
    private Button next;
    private  View rootView;

    public FragmentConfiguracion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_configuracion, container, false);

        UserWeight = rootView.findViewById(R.id.user_width);
        UserHeight = rootView.findViewById(R.id.user_height);
        UserBMI = rootView.findViewById(R.id.user_bmi);

        buttonEctomorfo = rootView.findViewById(R.id.button_ectomorfo);
        buttonMesomorfo = rootView.findViewById(R.id.button_mesomorfo);
        buttonEndomorfo = rootView.findViewById(R.id.button_endomorfo);

        buttonEctomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(true);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(false);
                somatotype="1";
            }
        });
        buttonMesomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(true);
                somatotype="2";
            }
        });
        buttonEndomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(true);
                checkMesomorfo.setChecked(false);
                somatotype="3";
            }
        });

        checkEctomorfo = rootView.findViewById(R.id.check_ectomorfo);
        checkMesomorfo = rootView.findViewById(R.id.check_mesomorfo);
        checkEndomorfo = rootView.findViewById(R.id.check_endomorfo);

        checkEctomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(true);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(false);
                somatotype="1";
            }
        });
        checkMesomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(true);
                somatotype="2";
            }
        });
        checkEndomorfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(true);
                checkMesomorfo.setChecked(false);
                somatotype="3";
            }
        });

        restoreData();

        next  = rootView.findViewById(R.id.netx);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(update_user()){
                    Intent i=new Intent(getContext(), UserMenu.class);
                    startActivity(i);

                }
            }
        });

        UserHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!UserWeight.getText().toString().equals("") && !UserHeight.getText().toString().equals("")){
                    float weidth = Float.parseFloat(UserWeight.getText().toString());
                    float heiht = Float.parseFloat(UserHeight.getText().toString());

                    float BMI = (float) (weidth/Math.pow(heiht/100,2));
                    UserBMI.setText(Float.toString(BMI));
                }
                else{
                    UserBMI.setText("");
                }
            }
        });

        UserWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!UserWeight.getText().toString().equals("") && !UserHeight.getText().toString().equals("")){
                    float weidth = Float.parseFloat(UserWeight.getText().toString());
                    float heiht = Float.parseFloat(UserHeight.getText().toString());

                    float BMI = (float) (weidth/Math.pow(heiht/100,2));
                    UserBMI.setText(Float.toString(BMI));
                }else{
                    UserBMI.setText("");
                }
            }
        });
        return rootView;
    }

    private Boolean update_user(){
        SQLHelper db=new SQLHelper(getContext());
        db.opendb();
        try {
            Cursor cursor = db.checkOpenSession();
            String user;
            if(cursor.moveToFirst()){
                if(successData()){
                    user = cursor.getString(cursor.getColumnIndex("nombre_usu"));
                    db.update_user(user,UserWeight.getText().toString(),UserHeight.getText().toString(),
                            UserBMI.getText().toString(),somatotype);
                    Toast t=Toast.makeText(getContext(), "Usuario actualizado", Toast.LENGTH_LONG);
                    t.show();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast t=Toast.makeText(getContext(), "Usuario NO actualizado", Toast.LENGTH_LONG);
        t.show();
        return false;
    }

    private Boolean successData(){
        UserWeight.setError(null);
        UserHeight.setError(null);
        Boolean success=true;
        if(UserWeight.getText().toString().equals("")){
            success=false;
            UserWeight.setError("Es necesario conocer tu peso");
        }
        if(UserHeight.getText().toString().equals("")){
            success=false;
            UserHeight.setError("Es necesario conocer tu altura");
        }
        if(!checkEndomorfo.isChecked() && !checkEctomorfo.isChecked() && !checkMesomorfo.isChecked()){
            success=false;
            Toast t=Toast.makeText(getContext(), "Debes seleccionar tu tipo de cuerpo", Toast.LENGTH_LONG);
            t.show();
        }
        return success;
    }

    private void restoreData(){
        SQLHelper db=new SQLHelper(getContext());
        db.opendb();
        try {
            Cursor cursor = db.checkOpenSession();
            String user;
            if(cursor.moveToFirst()){
                user = cursor.getString(cursor.getColumnIndex("nombre_usu"));
                cursor = db.consult_user(user);
                if(cursor.moveToFirst()){
                    UserWeight.setText(cursor.getString(cursor.getColumnIndex("peso")));
                    UserHeight.setText(cursor.getString(cursor.getColumnIndex("estatura")));
                    switch (cursor.getString(cursor.getColumnIndex("id_somatotipo_somatotipos"))){
                        case "1": checkEctomorfo.setChecked(true); somatotype="1"; break;
                        case "2": checkMesomorfo.setChecked(true); somatotype="2"; break;
                        case "3": checkEndomorfo.setChecked(true); somatotype="3"; break;
                    }
                    UserBMI.setText(cursor.getString(cursor.getColumnIndex("imc")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
