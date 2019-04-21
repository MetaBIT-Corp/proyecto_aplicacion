package com.example.crud_encuesta.Componentes_MT;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class VerAreasActivity extends AppCompatActivity {
    private List<String> ides=new ArrayList<>();
    private List<String> claves=new ArrayList<>();
    private int id_seleccion;
    private ListView listView;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_areas);

        ides = getIntent().getStringArrayListExtra("ides");
        claves = getIntent().getStringArrayListExtra("claves");
        id_seleccion = getIntent().getIntExtra("id", 0);

        listView = (ListView)findViewById(R.id.lsVerAreas);
        listView.setAdapter(new VerAreasAdapter(this, areas(), numero_preguntas(), aleatorio(), peso(), clave()));

        back = (ImageView)findViewById(R.id.back_area);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public List<String> areas(){
        List<String> areas = new ArrayList<>();

        int ide = Integer.parseInt(ides.get(id_seleccion));
        int id_area;

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Cursor cursor = db.rawQuery("SELECT * FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            id_area = cursor.getInt(1);
            Cursor cursor2 = db.rawQuery("SELECT titulo FROM area WHERE id_area="+id_area,null);
            cursor2.moveToFirst();
            areas.add(cursor2.getString(0));
            cursor2.close();
        }
        cursor.close();
        databaseAccess.close();

        return areas;
    }

    public String clave(){
        return claves.get(id_seleccion);
    }

    public List<Integer> numero_preguntas(){
        List<Integer> cantidad = new ArrayList<>();

        int ide = Integer.parseInt(ides.get(id_seleccion));

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Cursor cursor = db.rawQuery("SELECT numero_preguntas FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            cantidad.add(cursor.getInt(0));
        }
        cursor.close();
        databaseAccess.close();

        return cantidad;
    }

    public List<Integer> aleatorio(){
        List<Integer> aleatorio = new ArrayList<>();

        int ide = Integer.parseInt(ides.get(id_seleccion));

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Cursor cursor = db.rawQuery("SELECT aleatorio FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            aleatorio.add(cursor.getInt(0));
        }
        cursor.close();
        databaseAccess.close();

        return aleatorio;
    }

    public List<Integer> peso(){
        List<Integer> pesos = new ArrayList<>();

        int ide = Integer.parseInt(ides.get(id_seleccion));

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Cursor cursor = db.rawQuery("SELECT peso FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            pesos.add(cursor.getInt(0));
        }
        cursor.close();
        databaseAccess.close();

        return pesos;
    }
}
