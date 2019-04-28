package com.example.crud_encuesta.Componentes_MT;

import java.util.ArrayList;
import java.util.List;

class Pregunta{
    /*String pregunta;
    int id;
    int respuesta;
    float ponderacion;
    int modalidad;
    List<String> opciones = new ArrayList<>();
    List<Integer> ides = new ArrayList<>();

    public Pregunta(String pregunta, int id, int respuesta, float poderacion, int modallidad, List<String> opciones, List<Integer> ides) {
        this.pregunta = pregunta;
        this.id = id;
        this.ponderacion = poderacion;
        this.respuesta = respuesta;
        this.modalidad=modalidad;
        this.opciones = opciones;
        this.ides = ides;
    }*/

    String descripcion;
    int modalidad;
    List<PreguntaP> preguntaPList = new ArrayList<>();

    public Pregunta(String descripcion, int modalidad, List<PreguntaP> preguntaPList) {
        this.descripcion = descripcion;
        this.modalidad = modalidad;
        this.preguntaPList = preguntaPList;
    }
}
