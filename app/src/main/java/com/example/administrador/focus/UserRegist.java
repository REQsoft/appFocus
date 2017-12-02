package com.example.administrador.focus;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegist extends AppCompatActivity implements View.OnClickListener {

    private EditText user_name,password,password2;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist);

        user_name = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);

        buttonRegister = (Button)findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        user_name.setError(null);
        password.setError(null);
        password2.setError(null);
        if(view.getId()==R.id.button_register){
             if(user_name.getText().toString().equals("")){
                 user_name.setError("Debes tener un nombre de usuario");
                 return;
             }
             if(password.getText().toString().equals("")){
                 password.setError("Debes tener una contraseña");
                 return;
             }
             if(password2.getText().toString().equals("")){
                 password2.setError("Debes rectificar tu contraseña");
                 return;
             }

            if(!password2.getText().toString().equals(password.getText().toString())){
                password2.setError("Las contraseñas no coinciden");
                password.setError("Las contraseñas no coinciden");
                password.setText("");
                password2.setText("");
                return;
            }

            try {
                SQLHelper db=new SQLHelper(this);
                db.opendb();
                if(!db.exist_user(user_name.getText().toString())){
                    if(password.getText().toString().equals(password2.getText().toString())){
                        ContentValues values = new ContentValues();
                        values.put("nombre_usu",user_name.getText().toString());
                        values.put("clave",password.getText().toString());
                        db.insert_user(values);
                        Toast t=Toast.makeText(this, "El usuario se a registrado con exito", Toast.LENGTH_LONG);
                        t.show();
                        Intent i=new Intent(this, LoginActivity.class);
                        startActivity(i);
                    }
                }else{
                    user_name.setError("Este nombre de usuario ya esta en uso");
                }
            }catch (Exception e){
                Toast t=Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG);
                t.show();
                e.printStackTrace();
            }

        }
    }
}
