package com.example.crud_encuesta.Componentes_MT;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class ClaveActivity extends AppCompatActivity {
    private int id_encuesta = 1;
    private ImageView add_area;
    private ListView listView;
    private int[] iconos = {R.drawable.infoazul, R.drawable.addgris, R.drawable.edit1, R.drawable.ic_delete};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);

        listView = (ListView)findViewById(R.id.list_claves);
        listView.setAdapter(new ClaveAdapter(claves(), ides(), this, iconos));

        add_area = (ImageView)findViewById(R.id.add_clave);
        add_area.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                agregar_clave(v);
            }
        });
    }

    public List<String> claves(){
        List<String> claves = new ArrayList<>();
        DatabaseAccess database = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = database.open();

        Cursor cursor = db.rawQuery("SELECT numero_clave FROM clave", null);

        if(cursor!=null){
            while (cursor.moveToNext()){
                claves.add(cursor.getString(0));
            }
        }

        database.close();

        return claves;
    }

    public List<String> ides(){
        List<String> claves = new ArrayList<>();
        DatabaseAccess database = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = database.open();

        Cursor cursor = db.rawQuery("SELECT id_clave FROM clave", null);

        if(cursor!=null){
            while (cursor.moveToNext()){
                claves.add(cursor.getString(0));
            }
        }

        database.close();

        return claves;
    }

    public void agregar_clave(View v){
        int clave_nueva;
        int tamanio=claves().size();

        if(tamanio==0){
            clave_nueva = 1;
            Toast.makeText(this, "No hay", Toast.LENGTH_SHORT).show();
        }else{
            clave_nueva = Integer.parseInt(claves().get(tamanio-1));
            clave_nueva++;
            Toast.makeText(this, "Sí hay", Toast.LENGTH_SHORT).show();
        }

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        ContentValues registro = new ContentValues();
        //registro.put("id_clave", clave_nueva);
        registro.put("id_encuesta", id_encuesta);
        registro.put("numero_clave", clave_nueva);

        db.insert("clave", null, registro);
        databaseAccess.close();

    }


}
