package com.example.crud_encuesta.Componentes_EL;

public class Materia {
    private int id;
    /*private Pensum pensum;
    private Carrera carrera;*/
    private String codigo_materia;
    private String nombre;
    private boolean electiva;
    private int maximo_preguntas;

    public Materia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCodigo_materia() {
        return codigo_materia;
    }

    public void setCodigo_materia(String codigo_materia) {
        this.codigo_materia = codigo_materia;
    }

    public boolean isElectiva() {
        return electiva;
    }

    public void setElectiva(boolean electiva) {
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


    public String toString(){
        String cadena;
        cadena=nombre;
        return cadena;
    }
}
