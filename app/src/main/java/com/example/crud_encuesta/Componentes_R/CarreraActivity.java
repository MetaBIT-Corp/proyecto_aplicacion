package com.example.crud_encuesta.Componentes_R;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class CarreraActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseAccess access;
    ContentValues contentValues;
    ListView listView;
    ArrayList <Carrera> listaCarreras= new ArrayList<>();
    ArrayList<String> listSpinner=new ArrayList<>();
    ArrayList<Escuela> listaEscuelas= new ArrayList<>();
    CarreraAdapter adapter;

    int id_escuela=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        access=DatabaseAccess.getInstance(CarreraActivity.this);
        db=access.open();

        listaEscuelas=Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME,db);

        listaCarreras=Operaciones_CRUD.todosCarrera(db,listaEscuelas);
        adapter=new CarreraAdapter(CarreraActivity.this,listaCarreras,db,this,listaEscuelas);

        listView=findViewById(R.id.list_view_base);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder d=new AlertDialog.Builder(CarreraActivity.this);

                View v=getLayoutInflater().inflate(R.layout.dialogo_carrera, null);
                final EditText nom=v.findViewById(R.id.in_nom_carrera);
                final Spinner spinner=v.findViewById(R.id.spinner_carrera);

                listSpinner=obtenerLista();
                ArrayAdapter adaptadorSpinner=new ArrayAdapter(CarreraActivity.this,android.R.layout.simple_list_item_1,listSpinner);
                spinner.setAdapter(adaptadorSpinner);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position!=0){
                            id_escuela=listaEscuelas.get(position-1).getId();
                        }else id_escuela=-1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                d.setPositiveButton(R.string.agregar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty() || id_escuela==-1) Toast.makeText(CarreraActivity.this,"Campos Vacios.",Toast.LENGTH_SHORT).show();
                        else{
                            contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_1_CARRERA,id_escuela);
                            contentValues.put(EstructuraTablas.COL_2_CARRERA,nom.getText().toString());
                            Operaciones_CRUD.insertar(db,contentValues,CarreraActivity.this,EstructuraTablas.CARRERA_TABLA_NAME).show();
                            listaCarreras=Operaciones_CRUD.todosCarrera(db,listaEscuelas);
                            adapter.setL(listaCarreras);
                            id_escuela=-1;
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

    public ArrayList<String> obtenerLista(){
        ArrayList<String> listaEs=new ArrayList<>();

        listaEs.add("Seleccione");

        for (int i=0;i<listaEscuelas.size();i++){
            listaEs.add(listaEscuelas.get(i).getNombre());
        }
        return listaEs;
    }

}
