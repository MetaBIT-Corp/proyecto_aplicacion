package com.example.crud_encuesta.Componentes_EL.Escuela;

public class Escuela {
    private int id;
    private String nombre;
    private String cod;
    private int facultad;

    public Escuela(){

    }

    public Escuela(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getFacultad() {
        return facultad;
    }

    public void setFacultad(int facultad) {
        this.facultad = facultad;
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
        return this.nombre;
    }
}
