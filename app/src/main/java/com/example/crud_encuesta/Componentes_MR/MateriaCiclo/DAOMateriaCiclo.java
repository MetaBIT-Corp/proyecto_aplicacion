package com.example.crud_encuesta.Componentes_MR.MateriaCiclo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.crud_encuesta.Componentes_EL.Materia.Materia;
import com.example.crud_encuesta.DatabaseAccess;
import java.util.ArrayList;

public class DAOMateriaCiclo {

    private SQLiteDatabase cx;
    private ArrayList<MateriaCiclo> lista = new ArrayList<>();
    private MateriaCiclo materiaCiclo;
    private Context ct;

    public DAOMateriaCiclo(Context ct) {
        this.ct = ct;
        DatabaseAccess db = DatabaseAccess.getInstance(ct);
        cx = db.open();
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
        return (cx.delete("MATERIA_CICLO","ID_MAT_CI="+id, null)>0);
    }

    public boolean editar(MateriaCiclo materiaCiclo){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_CAT_MAT", materiaCiclo.getId_materia());
        contenedor.put("CICLO", materiaCiclo.getCiclo());
        contenedor.put("ANIO", materiaCiclo.getAnio());
        return (cx.update("MATERIA_CICLO",contenedor,"ID_MAT_CI="+materiaCiclo.getId(), null)>0);
    }

    public ArrayList<MateriaCiclo> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM MATERIA_CICLO", null);

        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new MateriaCiclo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return lista;
    }

    public MateriaCiclo verUno(int id){

        Cursor cursor = cx.rawQuery("SELECT * FROM MATERIA_CICLO", null);

        cursor.moveToPosition(id);

        materiaCiclo = new MateriaCiclo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3));

        return materiaCiclo;
    }

    public static ArrayList<Materia> materias(SQLiteDatabase db){

        ArrayList<Materia> materias = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CAT_MAT_MATERIA",null);

        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                materias.add(new Materia(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)));
            }while (cursor.moveToNext());
        }
        return materias;
    }

    public static ArrayList<String> listaMaterias(ArrayList<Materia> materias){
        ArrayList<String> listaMaterias = new ArrayList<>();
        for (int i=0; i<materias.size();i++){
            listaMaterias.add(materias.get(i).getCodigo_materia());
        }
        return listaMaterias;
    }
}
