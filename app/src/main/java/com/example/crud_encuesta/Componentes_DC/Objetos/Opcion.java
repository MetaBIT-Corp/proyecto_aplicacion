package com.example.crud_encuesta.Componentes_DC.Objetos;

public class Opcion {

    private int id;
    private int id_pregunta;
    private String opcion;
    private int correcta;

    public Opcion() {
    }

    public Opcion(int id_pregunta, String opcion, int correcta) {
        this.id_pregunta = id_pregunta;
        this.opcion = opcion;
        this.correcta = correcta;
    }

    public Opcion(int id, int id_pregunta, String opcion, int correcta) {
        this.id = id;
        this.id_pregunta = id_pregunta;
        this.opcion = opcion;
        this.correcta = correcta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }
}
