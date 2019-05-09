package com.example.crud_encuesta.Componentes_MT.Intento;

import java.util.ArrayList;
import java.util.List;

public class PreguntaP {
    String pregunta;
    int id;
    int respuesta;
    float ponderacion;
    List<Integer> ides = new ArrayList<>();
    List<String> opciones = new ArrayList<>();

    public PreguntaP(String pregunta, int id, int respuesta, float ponderacion, List<Integer> ides, List<String> opciones) {
        this.pregunta = pregunta;
        this.id = id;
        this.respuesta = respuesta;
        this.ponderacion = ponderacion;
        this.ides = ides;
        this.opciones = opciones;
    }

}
