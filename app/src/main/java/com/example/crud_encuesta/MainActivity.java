package com.example.crud_encuesta;


import android.content.Intent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ListView;

import com.example.crud_encuesta.Componentes_R.CarreraActivity;
import com.example.crud_encuesta.Componentes_AP.Activities.LoginActivity;
import com.example.crud_encuesta.Componentes_R.EscuelaActivity;


public class MainActivity extends AppCompatActivity {
    private ListView listView;

    private Toolbar myTopToolBar;
    private ImageView loggin;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        myTopToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myTopToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loggin = (ImageView) findViewById(R.id.log);
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    /*
     * Para acceder a la base de datos
     *
     * DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
     * databaseAccess.open();
     * .
     * .
     * .
     * your code
     * .
     * .
     * .
     * databaseAccess.close();
     */


    public void pressed() {
        Toast.makeText(MainActivity.this, "Has presionado el boton login", Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(getApplicationContext(),GpoEmpActivity.class);
        //startActivity(i);

        Intent i = new Intent(this, AreaActivity.class);
        startActivity(i);
    }

    public void prueba_escuela(View view) {
        Intent i = new Intent(this, EscuelaActivity.class);
        startActivity(i);
    }


    public void prueba_carrera(View view) {
        Intent i = new Intent(this, CarreraActivity.class);
        startActivity(i);
    }

    public void Login(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
