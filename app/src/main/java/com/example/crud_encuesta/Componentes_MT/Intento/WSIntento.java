package com.example.crud_encuesta.Componentes_MT.Intento;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WSIntento implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public WSIntento(Context context){
        this.context=context;

        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context, "Petición realizada con éxito", Toast.LENGTH_LONG).show();
    }

    public void inicio_intento(int id_est, int id_encuesta, int id_clave, String fecha_inicio, int numero_intento){
        //progress("Cargando...");
        String id_encuestaStr=Integer.toString(id_encuesta);
        fecha_inicio=fecha_inicio.replace(" ", "%20");

        if(id_encuesta==0) id_encuestaStr="null";

        //Local
        /*String url = "http://192.168.1.3:8001/encuesta/intento_create.php?" +
                "id_est="+id_est+
                "&id_encuesta="+id_encuesta+
                "&id_clave="+id_clave+
                "&fecha_inicio_intento="+fecha_inicio+
                "&numero_intento="+numero_intento;*/

        //Remoto
        String url = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/MT16007/intento_create.php?" +
                "id_est="+id_est+
                "&id_encuesta="+id_encuestaStr+
                "&id_clave="+id_clave+
                "&numero_intento="+numero_intento+
                "&fecha_inicio_intento="+fecha_inicio;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    public void terminar_intento(int id_intento, String fecha_final, double nota){
        //Local
        /*String url = "http://192.168.1.3:8001/encuesta/intento_update.php?" +
                "id_intento="+id_intento+
                "&fecha_final_intento="+fecha_final+
                "&nota_intento="+nota;*/

        //Remoto
        String url = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/MT16007/intento_update.php?" +
                "id_intento="+id_intento+
                "&fecha_final_intento="+fecha_final+
                "&nota_intento="+nota;

        url = url.replace(" ", "%20");

        System.out.println("Terminar intento: "+url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    public void modelo_respuesta(int id_opcion, int id_pregunta, int id_intento, String texto_respuesta){
        //Local
        /*String url = "http://192.168.1.3:8001/encuesta/respuesta_create.php?" +
                "id_opcion="+id_opcion+
                "&id_pregunta="+id_pregunta+
                "&id_intento="+id_intento+
                "&texto_respuesta="+texto_respuesta;*/

        //Remoto
        String url = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/MT16007/respuesta_create.php?" +
                "id_opcion="+id_opcion+
                "&id_pregunta="+id_pregunta+
                "&id_intento="+id_intento+
                "&texto_respuesta="+texto_respuesta;

        url = url.replace(" ", "%20");

        System.out.println("modelo respuesta: "+url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    public void progress(String mensaje){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
    }
}
