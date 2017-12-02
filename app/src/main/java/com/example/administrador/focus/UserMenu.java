package com.example.administrador.focus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class UserMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String usuario_activo;
    TextView usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //setUserHeader();
        //======================================================================
        Fragment fragment = null;
        Class fragmentClass =FragmentDefault.class;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.principal,fragment).commit();
        //=======================================================================

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        setUserHeader(header);

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = null;
            Class fragmentClass =FragmentAcercade.class;
            try {
                fragment = (Fragment)fragmentClass.newInstance();
            }catch (Exception e){
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.principal,fragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass =FragmentDefault.class;

        if (id == R.id.recomendaciones) {
            fragmentClass =FragmentRecommendation.class;
        } else if (id == R.id.configuracion) {
            fragmentClass =FragmentConfiguracion.class;
        } else if (id == R.id.abdomen) {
            fragmentClass =FragmentAbdomen.class;
        } else if (id == R.id.antebrazo) {
            fragmentClass =FragmentAntebrazo.class;
        } else if (id == R.id.biceps) {
            fragmentClass =FragmentBicep.class;
        } else if (id == R.id.espalda) {
            fragmentClass =FragmentEspalda.class;
        } else if (id == R.id.hombros) {
            fragmentClass =FragmentHombros.class;
        } else if (id == R.id.pantorrilla) {
            fragmentClass =FragmentPantorrilla.class;
        } else if (id == R.id.pecho) {
            fragmentClass =FragmentPecho.class;
        } else if (id == R.id.pierna) {
            fragmentClass =FragmentPierna.class;
        } else if (id == R.id.triceps) {
            fragmentClass =FragmentBicep.class;
        } else if (id == R.id.acerca_de) {
            fragmentClass =FragmentAcercade.class;
        } else if (id == R.id.cerrar_sesion) {
            SQLHelper db=new SQLHelper(this);
            String user;
            db.opendb();
            Cursor c = db.checkOpenSession();
            c.moveToFirst();
            user = c.getString(c.getColumnIndex("nombre_usu"));
            db.setSession(false,user);
            Toast t=Toast.makeText(this,"Sesion cerrada", Toast.LENGTH_LONG);
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        try {
            fragment = (Fragment)fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.principal,fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getUsuario_activo(){
        SQLHelper db=new SQLHelper(this);
        String user;
        db.opendb();
        Cursor c = db.checkOpenSession();
        c.moveToFirst();
        return c.getString(c.getColumnIndex("nombre_usu"));
    }

    private void setUserHeader(View v){
        usuario_activo = getUsuario_activo();
        usuario = (TextView)v.findViewById(R.id.header_user);
        usuario.setText(usuario_activo);
    }
}
