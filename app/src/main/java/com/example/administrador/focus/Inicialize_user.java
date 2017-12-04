package com.example.administrador.focus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Inicialize_user extends AppCompatActivity implements View.OnClickListener {

    private EditText UserWeight, UserHeight,UserBMI;
    private String somatotype="";
    private ImageButton buttonEctomorfo,buttonMesomorfo,buttonEndomorfo;
    private CheckBox checkEctomorfo,checkMesomorfo,checkEndomorfo;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicialize_user);

        UserWeight = (EditText)findViewById(R.id.user_width);
        UserHeight = (EditText)findViewById(R.id.user_height);
        UserBMI = (EditText) findViewById(R.id.user_bmi);

        buttonEctomorfo = (ImageButton)findViewById(R.id.button_ectomorfo);
        buttonMesomorfo = (ImageButton)findViewById(R.id.button_mesomorfo);
        buttonEndomorfo = (ImageButton)findViewById(R.id.button_endomorfo);

        buttonEctomorfo.setOnClickListener(this);
        buttonMesomorfo.setOnClickListener(this);
        buttonEndomorfo.setOnClickListener(this);

        checkEctomorfo = (CheckBox)findViewById(R.id.check_ectomorfo);
        checkMesomorfo = (CheckBox)findViewById(R.id.check_mesomorfo);
        checkEndomorfo = (CheckBox)findViewById(R.id.check_endomorfo);

        checkEctomorfo.setOnClickListener(this);
        checkMesomorfo.setOnClickListener(this);
        checkEndomorfo.setOnClickListener(this);

        next  =(Button) findViewById(R.id.netx);
        next.setOnClickListener(this);

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
    }

    public  void onClick(View view){
        switch (view.getId()){
            case R.id.button_ectomorfo:
                checkEctomorfo.setChecked(true);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(false);
                somatotype="1";
                break;

            case R.id.button_endomorfo:
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(true);
                checkMesomorfo.setChecked(false);
                somatotype="3";
                break;

            case  R.id.button_mesomorfo:
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(true);
                somatotype="2";
                break;

            case R.id.check_ectomorfo:
                checkEctomorfo.setChecked(true);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(false);
                somatotype="1";
                break;

            case R.id.check_endomorfo:
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(true);
                checkMesomorfo.setChecked(false);
                somatotype="3";
                break;

            case  R.id.check_mesomorfo:
                checkEctomorfo.setChecked(false);
                checkEndomorfo.setChecked(false);
                checkMesomorfo.setChecked(true);
                somatotype="2";
                break;

            case R.id.netx:
                if(initialize_user()){
                    Intent i=new Intent(this, UserMenu.class);
                    startActivity(i);

                }
        }
    }

    private Boolean initialize_user(){
        SQLHelper db=new SQLHelper(this);
        db.opendb();
        try {
            Cursor cursor = db.checkOpenSession();
            String user;
            if(cursor.moveToFirst()){
                if(successData()){
                    user = cursor.getString(cursor.getColumnIndex("nombre_usu"));
                     db.initialize_user(user,UserWeight.getText().toString(),UserHeight.getText().toString(),
                            UserBMI.getText().toString(),somatotype);
                     return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean successData(){
        UserWeight.setError(null);
        UserHeight.setError(null);
        Boolean success=true;
        if(UserWeight.getText().toString().equals("")){
            success=false;
            UserWeight.setError(getResources().getString(R.string.peso_requerido));
        }
        if(UserHeight.getText().toString().equals("")){
            success=false;
            UserHeight.setError(getResources().getString(R.string.estatura_requerida));
        }
        if(!checkEndomorfo.isChecked() && !checkEctomorfo.isChecked() && !checkMesomorfo.isChecked()){
            success=false;
            Toast t=Toast.makeText(this, getResources().getString(R.string.somatotipo_requerido), Toast.LENGTH_LONG);
            t.show();
        }
        return success;
    }
}
