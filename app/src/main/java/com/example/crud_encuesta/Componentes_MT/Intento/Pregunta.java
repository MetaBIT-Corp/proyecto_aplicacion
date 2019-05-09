package com.example.crud_encuesta.Componentes_MT.Intento;

import java.util.ArrayList;
import java.util.List;

class Pregunta{

    String descripcion;
    int modalidad;
    List<PreguntaP> preguntaPList = new ArrayList<>();

    public Pregunta(String descripcion, int modalidad, List<PreguntaP> preguntaPList) {
        this.descripcion = descripcion;
        this.modalidad = modalidad;
        this.preguntaPList = preguntaPList;
    }
}
