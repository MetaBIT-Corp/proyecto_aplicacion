package com.example.crud_encuesta.Componentes_EL;

public class Materia {

    //private Pensum pensum;
    //private Carrera carrera;

    private int id;
    private String nombre;
    private String codigo_materia;
    private int electiva;
    private int maximo_preguntas;

    public Materia(){}

    public Materia(int id, String nombre, String codigo_materia, int electiva, int maximo_preguntas) {
        this.id = id;
        this.nombre = nombre;
        this.codigo_materia = codigo_materia;
        this.electiva = electiva;
        this.maximo_preguntas = maximo_preguntas;
    }

    public Materia(String nombre, String codigo_materia, int electiva, int maximo_preguntas) {
        this.nombre = nombre;
        this.codigo_materia = codigo_materia;
        this.electiva = electiva;
        this.maximo_preguntas = maximo_preguntas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_materia() {
        return codigo_materia;
    }

    public void setCodigo_materia(String codigo_materia) {
        this.codigo_materia = codigo_materia;
    }

    public int getElectiva() {
        return electiva;
    }

    public void setElectiva(int electiva) {
        this.electiva = electiva;
    }

    public int getMaximo_preguntas() {
        return maximo_preguntas;
    }

    public void setMaximo_preguntas(int maximo_preguntas) {
        this.maximo_preguntas = maximo_preguntas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }*/


    public String toString(){
        String cadena;
        cadena=nombre;
        return cadena;
    }
}
