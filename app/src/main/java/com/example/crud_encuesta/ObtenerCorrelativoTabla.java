package com.example.crud_encuesta;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;


import org.json.JSONObject;

public class ObtenerCorrelativoTabla implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private String host = "https://eisi.fia.ues.edu.sv/encuestas/pdm115_ws/";
    public int correlativo;
    public String nomcampoid;
    public void peticion(String nomtabla, String nomcampoid){

        this.nomcampoid = nomcampoid;
        String url = host + "ws_getCorrelativoTabla.php?nomtabla="+nomtabla+"&nomcampoid="+nomcampoid;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("ERROR", ""+error.toString());
    }

    public int obtenerCorrelativo(){
        return correlativo;
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONObject json = response.optJSONObject("resultado");
        correlativo = json.optInt(nomcampoid);
    }
}
