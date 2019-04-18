package com.example.crud_encuesta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DaoGrupoEmp {

    private SQLiteDatabase cx;
    private ArrayList<GrupoEmparejamiento> lista_gpo_emp;
    private GrupoEmparejamiento gpo_emp;
    private Context ct;
    private String nombreBD = "proy_aplicacion.db";

    public DaoGrupoEmp(Context ct){
        this.ct = ct;
        cx = ct.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
    }

    public boolean insertar(GrupoEmparejamiento gpo_emp){
        return true;
    }

    public boolean eliinar(int id){
        return true;
    }

    public boolean editar(GrupoEmparejamiento gpo_emp){
        return true;
    }

    public ArrayList<GrupoEmparejamiento> verTodos(){
        return lista_gpo_emp;
    }

    public GrupoEmparejamiento verUno(int id){
        return gpo_emp;
    }
}
