package com.example.crud_encuesta.Componentes_AP.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Models.Turno;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DAOUsuario {
    SQLiteDatabase baseDeDatos;
    Usuario usuario;
    Context contexto;
    String nombreBD= "proy_aplicacion";

    //constructor
    public DAOUsuario(Context context){
        this.contexto = context;

        DatabaseAccess dba = DatabaseAccess.getInstance(context);
        baseDeDatos = dba.open();

        /*
         *Abrir base de datos
         * DatabaseAccess dba = DatabaseAccess.getInstance(context);
         * baseDeDatos = dba.open();
         *
         * */
    }




    //retorna true si se realiza el login con éxito
    public Boolean loginUsuario(String pass, String user){
        provisional();
        Boolean login = false;
        int id_usuario;
        int id_sesion;
        Cursor cursor = baseDeDatos.rawQuery(
                "Select * FROM USUARIO WHERE NOMUSUARIO= '" + user + "' AND CLAVE = '" +pass+"'",
                null
        );
        if(cursor.moveToFirst()){
           id_usuario = cursor.getInt(0);
           Cursor cursorSesion = baseDeDatos.rawQuery(
                   "Select * FROM SESIONUSUARIO WHERE IDUSUARIO = "+ id_usuario,
                   null
           );
           if (cursorSesion.moveToFirst()){
               id_sesion = cursorSesion.getInt(0);
               ContentValues contentValues = new ContentValues();
               contentValues.put("INSESION", 1);
               baseDeDatos.update("SESIONUSUARIO",contentValues,"IDSESION =" + id_sesion,null);
               login = true;
           }else{
               Toast.makeText(contexto,
                       "La sesión no existe",
                       Toast.LENGTH_SHORT).show();
           }
        }else {
            Toast.makeText(contexto,
                    "El usuario no existe",
                    Toast.LENGTH_SHORT).show();
        }
        return login;
    }

    public Boolean logoutUsuario(int id_user){
        Boolean logout = false;
        int id_usuario;
        int id_sesion;
        Cursor cursor = baseDeDatos.rawQuery(
                "Select * FROM USUARIO WHERE IDUSUARIO = " + id_user,
                null
        );
        if(cursor.moveToFirst()){
            id_usuario = cursor.getInt(0);
            Cursor cursorSesion = baseDeDatos.rawQuery(
                    "Select * FROM SESIONUSUARIO WHERE IDUSUARIO = "+ id_usuario,
                    null
            );
            if (cursorSesion.moveToFirst()){
                id_sesion = cursorSesion.getInt(0);
                ContentValues contentValues = new ContentValues();
                contentValues.put("INSESION", 0);
                baseDeDatos.update("SESIONUSUARIO",contentValues,"IDSESION =" + id_sesion,null);
                logout = true;
            }else{
                Toast.makeText(contexto,
                        "El usuario no tiene sesión",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(contexto,
                    "Id de usuario no valido",
                    Toast.LENGTH_SHORT).show();
        }
        return logout;
    }

    //provisional
    public void provisional(){
        Cursor cursor = baseDeDatos.rawQuery(
                "Select * FROM USUARIO",
                null
        );
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                logoutUsuario(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
    }

    public Usuario getUsuarioLogueado(){
        Usuario usuario = new Usuario();

        Cursor cursorSesion = baseDeDatos.rawQuery(
                "Select * FROM SESIONUSUARIO WHERE INSESION = "+ 1,
                null
        );
        if (cursorSesion.moveToFirst()){
            Cursor cursorUsuario = baseDeDatos.rawQuery(
                    "Select * FROM USUARIO WHERE IDUSUARIO = "+ cursorSesion.getInt(2),
                    null
            );
            if (cursorUsuario.moveToFirst()){
                usuario.setIDUSUARIO(cursorUsuario.getInt(0));
                usuario.setNOMUSUARIO(cursorUsuario.getString(1));
                usuario.setCLAVE(cursorUsuario.getString(2));
                usuario.setROL(cursorUsuario.getInt(3));
                Toast.makeText(
                        contexto,
                        "Usuario: " +usuario.getNOMUSUARIO()+" Rol: " + usuario.getROL(),
                        Toast.LENGTH_SHORT
                ).show();
            }else {
                usuario = null;
            }

        }else {
            usuario= null;
        }
        return usuario;
    }
}