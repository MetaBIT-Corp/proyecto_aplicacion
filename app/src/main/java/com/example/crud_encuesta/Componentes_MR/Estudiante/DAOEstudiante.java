package com.example.crud_encuesta.Componentes_MR.Estudiante;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.Componentes_EL.Encuesta.Encuesta;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.ObtenerCorrelativoTabla;
import com.example.crud_encuesta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DAOEstudiante implements Response.Listener<JSONObject>, Response.ErrorListener{

    private SQLiteDatabase cx;
    private ArrayList<Estudiante> lista = new ArrayList<>();
    private Estudiante estudiante;
    private Usuario usuario;
    private Context ct;
    private ProgressDialog progreso;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private String host = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/";
    private int id_usuario;
    private ObtenerCorrelativoTabla obtenerCorrelativoTabla;

    public DAOEstudiante(Context ct){
        this.ct = ct;
        DatabaseAccess dba = DatabaseAccess.getInstance(ct);
        cx = dba.open();

        request = Volley.newRequestQueue(ct);

        progreso = new ProgressDialog(ct);
        String cargando = ct.getResources().getString(R.string.ws_cargando);
        progreso.setMessage(cargando);

        obtenerCorrelativoTabla = new ObtenerCorrelativoTabla();
    }

    public boolean insertarUsuario(Usuario usuario){

        ContentValues contenedor = new ContentValues();
        contenedor.put("NOMUSUARIO",usuario.getNOMUSUARIO());
        contenedor.put("CLAVE",usuario.getCLAVE());
        contenedor.put("ROL",usuario.getROL());

        return (cx.insert("USUARIO", null,contenedor) > 0) ;
    }

    /*private int getCorrelativoUsuario() {

        obtenerCorrelativoTabla.peticion("USUARIO", "IDUSUARIO");
        int correlativoRemoto = obtenerCorrelativoTabla.obtenerCorrelativo();

        Cursor cursor = cx.rawQuery("SELECT IDUSUARIO  FROM USUARIO",null);
        if (cursor.moveToLast()){
            if(Integer.parseInt(cursor.getString(0)) > correlativoRemoto){
                return Integer.parseInt(cursor.getString(0));
            }else{
                return correlativoRemoto;
            }
        }
        return 0;
    }*/

    public boolean insertar(Estudiante estd){
        ContentValues contenedor = new ContentValues();
        contenedor.put("CARNET", estd.getCarnet());
        contenedor.put("NOMBRE", estd.getNombre());
        contenedor.put("ACTIVO", estd.getActivo());
        contenedor.put("ANIO_INGRESO", estd.getAnio_ingreso());
        contenedor.put("IDUSUARIO", estd.getId_usuario());

        long resultado = cx.insert("ESTUDIANTE", null, contenedor);
        boolean respuesta = false;

        if(resultado > 0){
            respuesta = true;

            String url = host+"DC16009/ws_insertar_estudiante.php?" +
                    "carnet="+estd.getCarnet()+"&" +
                    "nombre="+estd.getNombre()+"&" +
                    "activo="+estd.getActivo()+"&" +
                    "anio_ingreso="+estd.getAnio_ingreso();

            cargarWebService(url);
        }
        return respuesta;
    }

    /*private int getCorrelativoEstudiante() {

        obtenerCorrelativoTabla.peticion("ESTUDIANTE", "ID_EST");
        int correlativoRemoto = obtenerCorrelativoTabla.obtenerCorrelativo();

        Cursor cursor = cx.rawQuery("SELECT ID_EST  FROM ESTUDIANTE",null);
        if (cursor.moveToLast()){
            if(Integer.parseInt(cursor.getString(0)) > correlativoRemoto){
                return Integer.parseInt(cursor.getString(0));
            }else{
                return correlativoRemoto;
            }

        }
        return 0;
    }*/

    private void cargarWebService(String url) {
        progreso.show();

        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,null, this, this);

        request.add(jsonObjectRequest);

    }

    public boolean eliminar(int id){
        wsEliminar(id);
        return (cx.delete("ESTUDIANTE", "ID_EST="+id, null))>0;
    }

    public boolean editarUsuario(Usuario usuario){
        ContentValues contenedor = new ContentValues();
        contenedor.put("IDUSUARIO",usuario.getIDUSUARIO());
        contenedor.put("NOMUSUARIO",usuario.getNOMUSUARIO());
        contenedor.put("CLAVE",usuario.getCLAVE());
        contenedor.put("ROL",usuario.getROL());
        return (cx.update("USUARIO", contenedor, "IDUSUARIO="+usuario.getIDUSUARIO(),null))>0;
    }

    public boolean editar(Estudiante estd){
        cargarWebServiceEditar(estd);
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_EST", estd.getId());
        contenedor.put("CARNET", estd.getCarnet());
        contenedor.put("NOMBRE", estd.getNombre());
        contenedor.put("ACTIVO", estd.getActivo());
        contenedor.put("ANIO_INGRESO", estd.getAnio_ingreso());
        return (cx.update("ESTUDIANTE", contenedor, "ID_EST="+estd.getId(), null))>0;
    }

    /*public ArrayList<Estudiante> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM ESTUDIANTE",null);

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Estudiante(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            }while (cursor.moveToNext());
        }
        return lista;
    }*/

    public ArrayList<Estudiante> verTodos(AdaptadorEstudiante adaptadorEstudiante, ListView listView){
        request = Volley.newRequestQueue(ct);
        wsConsulta(adaptadorEstudiante, listView);

        return lista;
    }

    public void wsConsulta(final AdaptadorEstudiante adaptadorEstudiante, final ListView listView) {

        progreso.show();
        String url = host+"MR11139/WSReadEstudiantes.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.optJSONArray("ESTUDIANTE");
                    lista.clear();
                    for (int i = 0; i<json.length();i++){
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        lista.add(estudiante = new Estudiante(
                                jsonObject.optInt("ID_EST"),
                                jsonObject.optString("CARNET"),
                                jsonObject.optString("NOMBRE"),
                                jsonObject.optInt("ACTIVO"),
                                jsonObject.optString("ANIO_INGRESO"),
                                jsonObject.optInt("IDUSUARIO")
                        ));
                    }

                    listView.setAdapter(adaptadorEstudiante);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.add(jsonObjectRequest);
        progreso.hide();
    }

    public ArrayList<Estudiante> verBusqueda(String parametro){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM ESTUDIANTE WHERE NOMBRE LIKE '%"+parametro+"%' OR CARNET LIKE'%"+parametro+"%'",null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new Estudiante(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            }while(cursor.moveToNext());
        }
        return lista;
    }

    public Estudiante verUno(int id){
        Cursor cursor = cx.rawQuery("SELECT * FROM ESTUDIANTE", null);
        cursor.moveToPosition(id);
        estudiante = new Estudiante(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getInt(5));
        return estudiante;
    }

    public Usuario usuarioNombre(String nombre){
        Cursor cursor = cx.rawQuery("SELECT * FROM USUARIO WHERE NOMUSUARIO = '" +nombre+"'", null);
        cursor.moveToPosition(0);
        usuario = new Usuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));

        return usuario;
    }

    /***
     * WS Para eliminar Estudiante y el Usuario
     * @autor Ricardo Estupinian
     * @param id Identificador unico del Estudiante
     */
    private void wsEliminar(int id) {
        request = Volley.newRequestQueue(ct);
        String cargando = ct.getResources().getString(R.string.ws_cargando);
        progreso.show();
        String url = host+"EL16002/ws_EliminarEstudiante.php?id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,null,this,this);
        request.add(jsonObjectRequest);
        progreso.hide();
    }

    public void cargarWebServiceEditar(Estudiante estudiante) {
        request = Volley.newRequestQueue(ct);
        String host = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/";
        String ws= "AP16014/ws_update_estudiante.php?";
        String url = host+ ws +
                "id_est=" + estudiante.getId() +
                "&anio_ingreso=" + estudiante.getAnio_ingreso() +
                "&carnet=" + estudiante.getCarnet() +
                "&nombre=" + estudiante.getNombre()+
                "&id_usuario=" + estudiante.getId_usuario()+
                "&activo="+estudiante.getActivo();
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                this,
                this
        );
        request.add(jsonObjectRequest);
    }

    public ArrayList<Estudiante> verBusqueda(AdaptadorEstudiante adaptadorEstudiante, ListView listView, String parametro){
        lista.clear();
        wsConsultaBusqueda(adaptadorEstudiante, listView, parametro);
        return lista;
    }

    public void wsConsultaBusqueda(final AdaptadorEstudiante adaptadorEstudiante, final ListView listView, String parametro) {

        progreso.show();
        String url = host+"MR11139/WSBusquedaEstudiante.php?parametro="+parametro;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.optJSONArray("ESTUDIANTE");
                    lista.clear();
                    for (int i = 0; i<json.length();i++){
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        lista.add(estudiante = new Estudiante(
                                jsonObject.optInt("ID_EST"),
                                jsonObject.optString("CARNET"),
                                jsonObject.optString("NOMBRE"),
                                jsonObject.optInt("ACTIVO"),
                                jsonObject.optString("ANIO_INGRESO"),
                                jsonObject.optInt("IDUSUARIO")
                        ));
                    }

                    listView.setAdapter(adaptadorEstudiante);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.add(jsonObjectRequest);
        progreso.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(ct, R.string.ws_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(ct, R.string.ws_exito, Toast.LENGTH_SHORT).show();
    }

}