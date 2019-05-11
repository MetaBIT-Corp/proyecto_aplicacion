package com.example.crud_encuesta.Componentes_Docente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.DatabaseAccess;
import java.util.ArrayList;

public class DAODocente {

    private SQLiteDatabase cx;
    private ArrayList<Docente> lista = new ArrayList<>();
    private Docente docente;
    private Context ct;

    public DAODocente(Context ct){
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

    public boolean insertarUsuario(Usuario usuario){
        ContentValues contenedor = new ContentValues();
        contenedor.put("NOMUSUARIO",usuario.getNOMUSUARIO());
        contenedor.put("CLAVE",usuario.getCLAVE());
        contenedor.put("ROL",usuario.getROL());
        return (cx.insert("USUARIO", null,contenedor)>0);
    }

    public boolean insertar(Docente dc){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_ESCUELA",dc.getId_escuela());
        contenedor.put("CARNET_DCN",dc.getCarnet());
        contenedor.put("ANIO_TITULO",dc.getAnio_titulo());
        contenedor.put("ACTIVO",dc.getActivo());
        contenedor.put("TIPOJORNADA",dc.getTipo_jornada());
        contenedor.put("DESCRIPCIONDOCENTE",dc.getDescripcion());
        contenedor.put("ID_CARGO_ACTUAL",dc.getCargo_actual());
        contenedor.put("ID_SEGUNDO_CARGO",dc.getCargo_secundario());
        contenedor.put("NOMBRE_DOCENTE",dc.getNombre());
        contenedor.put("IDUSUARIO",dc.getId_usuario());
        return (cx.insert("PDG_DCN_DOCENTE",null,contenedor)>0);
    }

    public boolean eliminar(int id){
        return (cx.delete("PDG_DCN_DOCENTE","ID_PDG_DCN="+id, null)>0);
    }

    public boolean editarUsuario(Usuario usuario){
        ContentValues contenedor = new ContentValues();
        contenedor.put("NOMUSUARIO",usuario.getNOMUSUARIO());
        contenedor.put("CLAVE",usuario.getCLAVE());
        contenedor.put("ROL",usuario.getROL());
        return (cx.update("USUARIO", contenedor, "IDUSUARIO="+usuario.getIDUSUARIO(),null)>0);
    }

    public boolean editar(Docente dc){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_ESCUELA",dc.getId_escuela());
        contenedor.put("CARNET_DCN",dc.getCarnet());
        contenedor.put("ACTIVO",dc.getActivo());
        contenedor.put("ANIO_TITULO",dc.getAnio_titulo());
        contenedor.put("TIPOJORNADA",dc.getTipo_jornada());
        contenedor.put("DESCRIPCIONDOCENTE",dc.getDescripcion());
        contenedor.put("ID_CARGO_ACTUAL",dc.getCargo_actual());
        contenedor.put("ID_SEGUNDO_CARGO",dc.getCargo_secundario());
        contenedor.put("NOMBRE_DOCENTE",dc.getNombre());
        return (cx.update("PDG_DCN_DOCENTE",contenedor,"ID_PDG_DCN="+dc.getId(), null)>0);
    }

    public ArrayList<Docente> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM PDG_DCN_DOCENTE", null);

        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new Docente(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getInt(8),
                        cursor.getString(9)));
            }while(cursor.moveToNext());
        }
        return lista;
    }

    public Docente verUno(int id){
        Cursor cursor = cx.rawQuery("SELECT * FROM PDG_DCN_DOCENTE", null);
        cursor.moveToPosition(id);
        docente = new Docente(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getString(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getString(9));
        return docente;
    }
}