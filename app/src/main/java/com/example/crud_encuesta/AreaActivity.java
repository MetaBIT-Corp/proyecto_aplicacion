package com.example.crud_encuesta;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar myTopToolBar;
    private Spinner spItems;
    private ArrayAdapter<String> comboAdapter;
    private List <String> items;
    private String[] valorItem = new String[]{"Opción múltiple","V/F","Emparejamient","Resouesta corta", "Ordenamiento"};
    private ImageView add;
    private EditText mArea;
    private ListView listView;
    private int seleccion_item=0;
    private int id_cat_mat = 1;
    private int id_pdg_dcn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        cargarItems();
        r_area();

        add = (ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar_area();
            }
        });

    }

    public void cargarItems(){
        spItems = (Spinner) findViewById(R.id.items);
        spItems.setOnItemSelectedListener(this);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();
        //SQLiteDatabase db = databaseAccess.database();

        items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre_tipo_item FROM tipo_item", null);

        while (cursor.moveToNext()) {
            items.add(cursor.getString(0));
        }
        cursor.close();

        databaseAccess.close();

        comboAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        spItems.setAdapter(comboAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.items:

                Toast.makeText(this, "Modalidad: " + valorItem[position], Toast.LENGTH_SHORT).show();
                seleccion_item = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void agregar_area(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AreaActivity.this);
        final View mView =getLayoutInflater().inflate(R.layout.area_agregar, null);

        mBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                c_area(mView);
            }
        });

        mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void c_area(View v){
        //View mView =getLayoutInflater().inflate(R.layout.area_agregar, null);
        mArea = (EditText)v.findViewById(R.id.etArea);

        String titulo_area = mArea.getText().toString();

        if(!titulo_area.isEmpty()){
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            SQLiteDatabase db = databaseAccess.open();
            ContentValues registro = new ContentValues();

            registro.put("titulo", titulo_area);
            registro.put("id_tipo_item", seleccion_item);
            registro.put("id_cat_mat", id_cat_mat);
            registro.put("id_pdg_dcn", id_pdg_dcn);

            db.insert("area", null, registro);
            Toast.makeText(this, "Area agregada con éxito", Toast.LENGTH_SHORT).show();
            
            databaseAccess.close();
        }else{
            Toast.makeText(this, "Ingrese el título del área", Toast.LENGTH_SHORT).show();
        }

    }

    public void r_area(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        listView = (ListView)findViewById(R.id.list_areas);
        List<String> areas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT titulo FROM area", null);

        if(cursor!=null){
            while(cursor.moveToNext()){
                areas.add(cursor.getString(0));
            }
        }else{
            Toast.makeText(this, "La tabla está vacia", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        databaseAccess.close();

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areas);
        listView.setAdapter(adapter);
    }


}
