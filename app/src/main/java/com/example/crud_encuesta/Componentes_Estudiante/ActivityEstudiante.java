package com.example.crud_encuesta.Componentes_Estudiante;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.crud_encuesta.R;

public class ActivityEstudiante extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        Button agregar = (Button) findViewById(R.id.btn_nuevo_estudiante);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo =new Dialog(ActivityEstudiante.this);
                dialogo.setContentView(R.layout.dialogo_estudiante);
                dialogo.setTitle("Registro de Estudiante");
                dialogo.setCancelable(true);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

}
