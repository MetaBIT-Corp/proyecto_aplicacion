package com.example.crud_encuesta;

public class GrupoEmparejamiento {

    private int id;
    private int id_area;
    private String descripcion;

    public GrupoEmparejamiento() {

    }

    public GrupoEmparejamiento(int id, int id_area, String descripcion) {
        this.id = id;
        this.id_area = id_area;
        this.descripcion = descripcion;
    }

    public GrupoEmparejamiento(int id_area, String descripcion) {
        this.id_area = id_area;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
