package com.example.crud_encuesta.Componentes_MT.ClaveArea;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class VerAreasActivity extends AppCompatActivity {
    DAOClaveArea daoClaveArea;
    List<ClaveArea> claveAreas = new ArrayList<>();
    VerAreasAdapter verAreasAdapter;

    private int id_clave;
    private String clave;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_areas);

        id_clave = getIntent().getIntExtra("id", 0);
        clave = getIntent().getStringExtra("clave");


        daoClaveArea = new DAOClaveArea(this);
        claveAreas = daoClaveArea.getAreasClave(id_clave, clave);
        verAreasAdapter = new VerAreasAdapter(this, claveAreas, daoClaveArea, id_clave, clave);

        listView = (ListView)findViewById(R.id.lsVerAreas);
        listView.setAdapter(verAreasAdapter);

    }

    public List<String> areas(SQLiteDatabase db, int ide){
        List<String> areas = new ArrayList<>();
        int id_area;

        Cursor cursor = db.rawQuery("SELECT * FROM clave_area WHERE id_clave="+ide,null);
        while (cursor.moveToNext()){
            id_area = cursor.getInt(1);
            Cursor cursor2 = db.rawQuery("SELECT titulo FROM area WHERE id_area="+id_area,null);
            cursor2.moveToFirst();
            areas.add(cursor2.getString(0));
            cursor2.close();
        }
        cursor.close();

        return areas;
    }

    public List<Integer> numero_preguntas(SQLiteDatabase db, int ide){
        List<Integer> cantidad = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT numero_preguntas FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            cantidad.add(cursor.getInt(0));
        }
        cursor.close();

        return cantidad;
    }

    public List<Integer> aleatorio(SQLiteDatabase db, int ide){
        List<Integer> aleatorio = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT aleatorio FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            aleatorio.add(cursor.getInt(0));
        }
        cursor.close();

        return aleatorio;
    }

    public List<Integer> peso(SQLiteDatabase db, int ide){
        List<Integer> pesos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT peso FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            pesos.add(cursor.getInt(0));
        }
        cursor.close();
        return pesos;
    }

    public List<Integer> id_ca(SQLiteDatabase db, int ide){
        List<Integer> ids_ca = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id_clave_area FROM clave_area WHERE id_clave="+ide,null);

        while (cursor.moveToNext()){
            ids_ca.add(cursor.getInt(0));
        }
        cursor.close();
        return ids_ca;
    }

    public List<ClaveArea> getAreasClave(){
        List<ClaveArea> areasClave = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        List<Integer> numero_preguntas = new ArrayList<>();
        List<Integer> aleatorios = new ArrayList<>();
        List<Integer> pesos = new ArrayList<>();
        List<Integer> ids_ca = new ArrayList<>();

        String area;
        int num_p;
        int aleat;
        int peso;
        int id_ca;

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        areas = areas(db, id_clave);
        numero_preguntas = numero_preguntas(db, id_clave);
        aleatorios = aleatorio(db, id_clave);
        pesos = peso(db, id_clave);
        ids_ca = id_ca(db, id_clave);

        for (int i = 0; i<areas.size(); i++) {
            area = areas.get(i);
            num_p = numero_preguntas.get(i);
            aleat = aleatorios.get(i);
            peso = pesos.get(i);
            id_ca = ids_ca.get(i);

            areasClave.add(new ClaveArea(area, clave, id_ca, aleat, num_p, peso));
        }

        databaseAccess.close();

        return  areasClave;
    }
}
