package com.example.crud_encuesta.Componentes_R;

public class Escuela {
    private int id;
    private String nombre;

    public Escuela(){

    }

    public Escuela(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return "ID: " + this.id + " - Nombre: " + this.nombre;
    }
}
