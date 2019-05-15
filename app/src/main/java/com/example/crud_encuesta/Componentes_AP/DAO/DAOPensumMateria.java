package com.example.crud_encuesta.Componentes_AP.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_AP.Models.PensumMateria;
import com.example.crud_encuesta.Componentes_EL.Materia.Materia;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DAOPensumMateria {
    SQLiteDatabase baseDeDatos;
    ArrayList<PensumMateria> pensumMaterias = new ArrayList<>();
    PensumMateria pensumMateria;
    Context contexto;
    DatabaseAccess dba;
    String nombreBD = "proy_aplicacion";

    //constructor
    public DAOPensumMateria(Context context) {
        this.contexto = context;

        this.dba = DatabaseAccess.getInstance(context);
        baseDeDatos = this.dba.open();

        /*
         *Abrir base de datos
         * DatabaseAccess dba = DatabaseAccess.getInstance(context);
         * baseDeDatos = dba.open();
         *
         * */
    }

    public Boolean Insertar(PensumMateria pensumMateria) {
        baseDeDatos = this.dba.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CAT_MAT", pensumMateria.getIdMateria());
        contentValues.put("ID_PENUM", pensumMateria.getIdPensum());

        return (baseDeDatos.insert("PENSUM_MATERIA",
                null, contentValues) > 0);
    }

    public Boolean Eliminar(Integer id) {
        baseDeDatos = this.dba.open();
        return (baseDeDatos.delete(
                "PENSUM_MATERIA",
                "ID_PENSUM_MATERIA=" + id,
                null) > 0);
    }

    public Boolean Editar(PensumMateria pensumMateria) {
        baseDeDatos = this.dba.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CAT_MAT", pensumMateria.getIdMateria());
        contentValues.put("ID_PENUM", pensumMateria.getIdPensum());

        return (baseDeDatos.update(
                "PENSUM_MATERIA",
                contentValues,
                "ID_PENSUM_MATERIA =" + pensumMateria.getIdPensumMateria(),
                null) > 0);
    }

    public ArrayList<PensumMateria> verTodos(int id_pensum) {
        baseDeDatos = this.dba.open();
        pensumMaterias.clear(); //limpiamos lista del adapter
        Cursor cursor = baseDeDatos.rawQuery(
                "Select * FROM PENSUM_MATERIA WHERE ID_PENUM  = " + id_pensum,
                null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                pensumMaterias.add(new PensumMateria(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2)
                ));

            } while (cursor.moveToNext());
        }
        return pensumMaterias;
    }

    public ArrayList<String> SpinnerMaterias(int id_pensum) {
        baseDeDatos = this.dba.open();
        int b1,b2;
        ArrayList<String> materias = new ArrayList<String>();
        materias.add("Seleccione");

        Cursor cursorPensumMateria = baseDeDatos.rawQuery(
                "SELECT * FROM PENSUM_MATERIA WHERE ID_PENUM = " + id_pensum,
                null
        );
        Cursor cursorMateria = baseDeDatos.rawQuery(
                "Select * FROM CAT_MAT_MATERIA",
                null);
        if (cursorPensumMateria.moveToFirst() && cursorMateria.moveToFirst()) {

            cursorPensumMateria.moveToFirst();
            cursorMateria.moveToFirst();

            ArrayList<PensumMateria> listapm = new ArrayList<PensumMateria>();
            ArrayList<Materia> listam = new ArrayList<Materia>();
            do {
                listapm.add(new PensumMateria(
                        cursorPensumMateria.getInt(0),
                        cursorPensumMateria.getInt(1),
                        cursorPensumMateria.getInt(2)
                ));
            }while (cursorPensumMateria.moveToNext());

            do {
                listam.add(new Materia(
                        cursorMateria.getInt(0),
                        cursorMateria.getString(1),
                        cursorMateria.getString(2),
                        cursorMateria.getInt(3),
                        cursorMateria.getInt(4)
                ));
            }while (cursorMateria.moveToNext());

            for (int j = 0; j <= listam.size()-1; j++){
                b1=0;
                for (int i =0;i<=listapm.size()-1;i++){
                    if(listam.get(j).getId() == listapm.get(i).getIdMateria()){
                        b1=1;
                    }
                }
                if (b1==0){
                    materias.add(listam.get(j).getId()+ " - " + listam.get(j).getCodigo_materia());
                }
            }

        } else {
            if (cursorMateria.moveToFirst()) {
                cursorMateria.moveToFirst();
                do {
                    materias.add(cursorMateria.getInt(0) + " - " + cursorMateria.getString(2));
                } while (cursorMateria.moveToNext());
            }
        }
        return materias;
    }

    public ArrayList<String> SpinnerMateriasEditar(int id_pensum, int id_materia) {
        baseDeDatos = this.dba.open();
        int b1;
        ArrayList<String> materias = new ArrayList<String>();
        materias.add("Seleccione");

        Cursor cursorPensumMateria = baseDeDatos.rawQuery(
                "SELECT * FROM PENSUM_MATERIA WHERE ID_PENUM = " + id_pensum,
                null
        );
        Cursor cursorMateria = baseDeDatos.rawQuery(
                "Select * FROM CAT_MAT_MATERIA",
                null);
        if (cursorPensumMateria.moveToFirst() && cursorMateria.moveToFirst()) {

            cursorPensumMateria.moveToFirst();
            cursorMateria.moveToFirst();

            ArrayList<PensumMateria> listapm = new ArrayList<PensumMateria>();
            ArrayList<Materia> listam = new ArrayList<Materia>();
            do {
                listapm.add(new PensumMateria(
                        cursorPensumMateria.getInt(0),
                        cursorPensumMateria.getInt(1),
                        cursorPensumMateria.getInt(2)
                ));
            }while (cursorPensumMateria.moveToNext());

            do {
                listam.add(new Materia(
                        cursorMateria.getInt(0),
                        cursorMateria.getString(1),
                        cursorMateria.getString(2),
                        cursorMateria.getInt(3),
                        cursorMateria.getInt(4)
                ));
            }while (cursorMateria.moveToNext());

            for (int j = 0; j <= listam.size()-1; j++){
                b1=0;
                for (int i =0;i<=listapm.size()-1;i++){
                    if(listam.get(j).getId() == listapm.get(i).getIdMateria() && listam.get(j).getId()!= id_materia){
                        b1=1;
                    }
                    //Toast.makeText(contexto,""+listapm.get(i).getIdMateria(),Toast.LENGTH_SHORT).show();
                }
                if (b1==0){
                    materias.add(listam.get(j).getId()+ " - " + listam.get(j).getCodigo_materia());
                }
            }

        } else {
            if (cursorMateria.moveToFirst()) {
                cursorMateria.moveToFirst();
                do {
                    materias.add(cursorMateria.getInt(0) + " - " + cursorMateria.getString(2));
                } while (cursorMateria.moveToNext());
            }
        }
        return materias;
    }

    public String getNombreMateria(PensumMateria pensumMateria) {
        baseDeDatos = this.dba.open();
        String nombreMateria = null;
        Cursor cursorMateria = baseDeDatos.rawQuery(
                "SELECT * FROM CAT_MAT_MATERIA WHERE ID_CAT_MAT =" + pensumMateria.getIdMateria(),
                null
        );
        if (cursorMateria.moveToFirst()) {
            cursorMateria.moveToFirst();
            nombreMateria = cursorMateria.getString(1) + " - " + cursorMateria.getString(2);
        }
        return nombreMateria;
    }

    //retorna la lista de materias de un pensumMateria
    public ArrayList<PensumMateria> verUno(String materia, int id_pensum) {
        baseDeDatos = this.dba.open();
        pensumMaterias.clear();
        Cursor cursorMateria = baseDeDatos.rawQuery(
                "Select * FROM CAT_MAT_MATERIA WHERE NOMBRE_MAR LIKE '%" + materia + "%'",
                null);
        if (cursorMateria.moveToFirst()) {
            cursorMateria.moveToFirst();
            do {
                Cursor cursorPensumMateria = baseDeDatos.rawQuery(
                        "Select * FROM PENSUM_MATERIA WHERE ID_CAT_MAT = " + cursorMateria.getInt(0)+ " AND ID_PENUM ="+ id_pensum,
                        null
                );
                if (cursorPensumMateria.moveToFirst()) {
                    cursorPensumMateria.moveToFirst();
                    do {
                        pensumMaterias.add(new PensumMateria(
                                cursorPensumMateria.getInt(0),
                                cursorPensumMateria.getInt(1),
                                cursorPensumMateria.getInt(2)
                        ));
                    } while (cursorPensumMateria.moveToNext());
                }
            } while (cursorMateria.moveToNext());
        }
        return pensumMaterias;
    }
}
