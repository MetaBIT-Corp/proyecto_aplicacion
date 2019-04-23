package com.example.crud_encuesta.Componentes_MT;

import java.util.ArrayList;
import java.util.List;

class Pregunta{
    String pregunta;
    int id;
    int respuesta;
    float ponderacion;
    List<String> opciones = new ArrayList<>();
    List<Integer> ides = new ArrayList<>();

    public Pregunta(String pregunta, int id, int respuesta, float poderacion, List<String> opciones, List<Integer> ides) {
        this.pregunta = pregunta;
        this.id = id;
        this.ponderacion = poderacion;
        this.respuesta = respuesta;
        this.opciones = opciones;
        this.ides = ides;
    }
}
