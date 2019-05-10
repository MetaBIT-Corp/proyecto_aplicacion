package com.example.crud_encuesta.Componentes_MateriaCiclo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.crud_encuesta.Componentes_EL.Materia.Materia;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class ActivityMateriaCiclo extends AppCompatActivity {

    private SQLiteDatabase db;
    private DatabaseAccess access;
    private ArrayList<Materia> materias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_ciclo);

        access = DatabaseAccess.getInstance(ActivityMateriaCiclo.this);
        db = access.open();

        materias = DAOMateriaCiclo.materias(db);

        for(int i=0; i <materias.size(); i++){
            System.out.println(materias.get(i).getCodigo_materia()+"----------------------------------------------------------------");
        }
    }
}
