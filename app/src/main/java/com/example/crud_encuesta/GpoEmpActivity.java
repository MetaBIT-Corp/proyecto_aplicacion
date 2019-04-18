package com.example.crud_encuesta;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
        ListView list = (ListView)findViewById(R.id.lista);
        Button agregar = (Button)findViewById(R.id.btn_nuevo);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GpoEmpActivity.this);
                dialog.setTitle("Nuevo Grupo Emparejamiento");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_gpo_emp);
                dialog.show();
            }
        });
    }
}
