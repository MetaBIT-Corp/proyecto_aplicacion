package com.example.crud_encuesta.Componentes_MT.Intento;

import java.util.List;

public class PreguntaRevision {
    String pregunta;
    String descripcion;
    String texto_eleccion;
    int modalidad;
    int eleccion;
    int respuesta;
    List<String> opciones;
    List<Integer> ides;

    public PreguntaRevision(String pregunta, String descripcion, String texto_eleccion, int modalidad, int eleccion, int respuesta, List<String> opciones, List<Integer> ides) {
        this.pregunta = pregunta;
        this.descripcion = descripcion;
        this.texto_eleccion = texto_eleccion;
        this.modalidad = modalidad;
        this.eleccion = eleccion;
        this.respuesta = respuesta;
        this.opciones = opciones;
        this.ides = ides;
    }
}
