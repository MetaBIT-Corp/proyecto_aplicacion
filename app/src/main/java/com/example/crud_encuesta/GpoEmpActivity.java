package com.example.crud_encuesta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class GpoEmpActivity extends AppCompatActivity {

    private DaoGrupoEmp dao;
    private Adaptador adaptador;
    private ArrayList<GrupoEmparejamiento> lista_gpo_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grupo_emparejamiento);
        dao = new DaoGrupoEmp(this);
        lista_gpo_emp = dao.verTodos();
        adaptador = new Adaptador(lista_gpo_emp,this,dao);
    }
}
