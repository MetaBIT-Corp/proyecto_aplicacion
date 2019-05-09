package com.example.crud_encuesta;

import android.content.Intent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.crud_encuesta.Componentes_AP.Activities.EvaluacionActivity;
import com.example.crud_encuesta.Componentes_AP.Activities.TurnoActivity;
import com.example.crud_encuesta.Componentes_Docente.ActivityDocente;
import com.example.crud_encuesta.Componentes_Estudiante.ActivityEstudiante;
import com.example.crud_encuesta.Componentes_MT.Area.AreaActivity;
import com.example.crud_encuesta.Componentes_MT.Clave.ClaveActivity;
import com.example.crud_encuesta.Componentes_MT.Intento.IntentoActivity;
import com.example.crud_encuesta.Componentes_EL.CarreraActivity;
import com.example.crud_encuesta.Componentes_AP.Activities.LoginActivity;
import com.example.crud_encuesta.Componentes_EL.EncuestaActivity;
import com.example.crud_encuesta.Componentes_EL.EscuelaActivity;
import com.example.crud_encuesta.Componentes_EL.MateriaActivity;

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
                //Login();
                intento();
                //evaluacion();
                //pressed();

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
        Intent i = new Intent(getApplicationContext(), AreaActivity.class);
        startActivity(i);
    }

    public void area(){
        Intent i = new Intent(this, AreaActivity.class);
        startActivity(i);
    }

    public void intento(){
        Intent i = new Intent(this, IntentoActivity.class);
        startActivity(i);
    }

    public void clave(){
        Intent i = new Intent(this, ClaveActivity.class);
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

    public void prueba_materia(View view) {
        Intent i = new Intent(this, MateriaActivity.class);
        startActivity(i);
    }

    public void prueba_encuesta(View view) {
        Intent i = new Intent(this, EncuestaActivity.class);
        startActivity(i);
    }

    public void Login(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void evaluacion(){
        Intent i = new Intent(this, EvaluacionActivity.class);
        startActivity(i);
    }
    public void turno(){
        Intent i = new Intent(this, TurnoActivity.class);
        startActivity(i);
    }

    public void activity_docente(View view){
        Intent i= new Intent(this, ActivityDocente.class);
        startActivity(i);
    }

    public void activity_estudiante(View view){
        Intent i= new Intent(this, ActivityEstudiante.class);
        startActivity(i);
    }
}
