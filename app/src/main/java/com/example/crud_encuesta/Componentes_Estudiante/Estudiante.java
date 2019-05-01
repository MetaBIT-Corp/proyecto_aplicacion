package com.example.crud_encuesta.Componentes_Estudiante;

public class Estudiante {
    private int id_est;
    private String carnet, nombre, anio_ingreso;
    private int activo;

    public Estudiante() {}

    public Estudiante(int id_est, String carnet, String nombre, int activo, String anio_ingreso) {
        this.id_est = id_est;
        this.carnet = carnet;
        this.nombre = nombre;
        this.activo = activo;
        this.anio_ingreso = anio_ingreso;
    }

    public Estudiante(String carnet, String nombre, int activo, String anio_ingreso) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.activo = activo;
        this.anio_ingreso = anio_ingreso;
    }

    public int getId_est() {
        return id_est;
    }

    public void setId_est(int id_est) {
        this.id_est = id_est;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnio_ingreso() {
        return anio_ingreso;
    }

    public void setAnio_ingreso(String anio_ingreso) {
        this.anio_ingreso = anio_ingreso;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id_est=" + id_est +
                ", carnet='" + carnet + '\'' +
                ", nombre='" + nombre + '\'' +
                ", anio_ingreso='" + anio_ingreso + '\'' +
                ", activo=" + activo +
                '}';
    }
}
