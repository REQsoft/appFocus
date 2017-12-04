package com.example.administrador.focus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private EditText userName;
    private EditText password;
    private Button buttonLogin;
    private  Button buttonRegister;
    String usuario_activo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
       if(checkSession()){
            if(isInitialize()){
                Intent i=new Intent(this, UserMenu.class);
                startActivity(i);
            }else{
                Intent i=new Intent(this, Inicialize_user.class);
                startActivity(i);
            }
        }

        userName = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_login){
            SQLHelper db=new SQLHelper(this);
            db.opendb();
            try {
                if(db.validate_user(userName.getText().toString(), password.getText().toString())){
                    if(db.user_initialize(userName.getText().toString())){
                        db.setSession(true,userName.getText().toString());
                        Intent i=new Intent(this, UserMenu.class);
                        startActivity(i);
                    }else{
                        db.setSession(true,userName.getText().toString());
                        Intent i=new Intent(this, Inicialize_user.class);
                        startActivity(i);
                    }
                }
                else{
                    Toast t=Toast.makeText(this, getResources().getString(R.string.error_login), Toast.LENGTH_LONG);
                    t.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if(v.getId()==R.id.button_register){
            Intent i=new Intent(this, UserRegist.class);
            startActivity(i);
        }

    }

    private Boolean checkSession(){
        SQLHelper db=new SQLHelper(this);
        db.opendb();
        db.launchData();
        Cursor c = db.checkOpenSession();
        if(c.moveToFirst()){
            usuario_activo = c.getString(c.getColumnIndex("nombre_usu"));
            return true;
        }
        return false;
    }

    private Boolean isInitialize(){
        SQLHelper db=new SQLHelper(this);
        db.opendb();
        return db.user_initialize(usuario_activo);
    }

}



