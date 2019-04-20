package com.example.crud_encuesta.Componentes_R;

public class Carrera {

    private int id;
    private Escuela escuela;
    private String nombre;

    public Carrera() {
    }

    public Carrera(int id, Escuela escuela, String nombre) {
        this.id = id;
        this.escuela = escuela;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return "Id Carrera: "+this.id+"\nNombre Carrera: "+this.nombre+"\n\nDatos Escuela\n"+escuela;
    }
}
