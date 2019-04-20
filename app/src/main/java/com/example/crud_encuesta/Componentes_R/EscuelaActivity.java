package com.example.crud_encuesta.Componentes_R;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class EscuelaActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseAccess access;
    ContentValues contentValues;
    ListView listView;
    ArrayList <Escuela> listaEscuela= new ArrayList<>();
    EscuelaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuela);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        access=DatabaseAccess.getInstance(EscuelaActivity.this);
        db=access.open();
        listView=findViewById(R.id.list_view_base);

        listaEscuela=Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME,db);
        adapter= new EscuelaAdapter(EscuelaActivity.this,listaEscuela,db,this);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder d=new AlertDialog.Builder(EscuelaActivity.this);

                View v=getLayoutInflater().inflate(R.layout.dialogo_escuela, null);

                final EditText nom=v.findViewById(R.id.in_nom_escuela);

                d.setPositiveButton(R.string.agregar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty())Toast.makeText(EscuelaActivity.this,"Campos Vacios.",Toast.LENGTH_SHORT).show();
                        else{
                            db=access.open();
                            contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_1,nom.getText().toString());
                            Operaciones_CRUD.insertar(db,contentValues,EscuelaActivity.this, EstructuraTablas.ESCUELA_TABLA_NAME).show();
                            db=access.openRead();
                            listaEscuela=Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME,db);
                            adapter.setL(listaEscuela);
                        }
                    }
                });

                d.setNegativeButton(R.string.cancelar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                d.setView(v);
                d.show();

            }
        });
    }
}
