package com.example.crud_encuesta.Componentes_MT;

public class Tamanio {
    private int opcion_multiple=0;
    private int emparejamiento=0;
    private int respuesta_corta=0;
    private int verdadero_falso=0;

    public Tamanio() {
    }

    public int getOpcion_multiple() {
        return opcion_multiple;
    }

    public void setOpcion_multiple(int opcion_multiple) {
        this.opcion_multiple = opcion_multiple;
    }

    public int getEmparejamiento() {
        return emparejamiento;
    }

    public void setEmparejamiento(int emparejamiento) {
        this.emparejamiento = emparejamiento;
    }

    public int getRespuesta_corta() {
        return respuesta_corta;
    }

    public void setRespuesta_corta(int respuesta_corta) {
        this.respuesta_corta = respuesta_corta;
    }

    public int getVerdadero_falso() {
        return verdadero_falso;
    }

    public void setVerdadero_falso(int verdadero_falso) {
        this.verdadero_falso = verdadero_falso;
    }
}
