package com.example.crud_encuesta.Componentes_MateriaCiclo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.crud_encuesta.DatabaseAccess;
import java.util.ArrayList;

public class DAOMateriaCiclo {

    private SQLiteDatabase cx;
    private ArrayList<MateriaCiclo> lista = new ArrayList<>();
    private MateriaCiclo materiaCiclo;
    private Context ct;

    public DAOMateriaCiclo(Context ct){
        this.ct = ct;
        DatabaseAccess dba = DatabaseAccess.getInstance(ct);
        cx = dba.open();
    }

    public Context getCt() {
        return ct;
    }
    public void setCt(Context ct) {
        this.ct = ct;
    }

    public boolean insertar(MateriaCiclo materiaCiclo){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_CAT_MAT", materiaCiclo.getId_materia());
        contenedor.put("CICLO", materiaCiclo.getCiclo());
        contenedor.put("ANIO", materiaCiclo.getAnio());
        return (cx.insert("MATERIA_CICLO",null,contenedor)>0);
    }

    public boolean eliminar(int id){
        return (cx.delete("MATERIA_CICLO", "ID_MAT_CI="+id, null)>0);
    }

    public boolean editar(MateriaCiclo materiaCiclo){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_MAT_CI", materiaCiclo.getId());
        contenedor.put("ID_CAT_MAT", materiaCiclo.getId_materia());
        contenedor.put("CICLO", materiaCiclo.getCiclo());
        contenedor.put("ANIO", materiaCiclo.getAnio());
        return (cx.update("MATERIA_CICLO",contenedor, "ID_MAT_CI="+materiaCiclo.getId(),null)>0);
    }

    public ArrayList<MateriaCiclo> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM MATERIA_CICLO",null);

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new MateriaCiclo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(0),
                        cursor.getString(0)));
            }while(cursor.moveToNext());
        }
        return lista;
    }
}
