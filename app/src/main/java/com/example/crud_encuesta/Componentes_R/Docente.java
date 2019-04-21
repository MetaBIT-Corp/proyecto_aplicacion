package com.example.crud_encuesta.Componentes_R;

public class Docente {

    int id;
    String carnet,nombre,descripcion,anio_titulo;
    int tipo_jornada,cargo_actual,cargo_segundario,id_escuela;
    boolean activo;



    public int getId_escuela() {
        return id_escuela;
    }

    public void setId_escuela(int id_escuela) {
        this.id_escuela = id_escuela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnio_titulo() {
        return anio_titulo;
    }

    public void setAnio_titulo(String anio_titulo) {
        this.anio_titulo = anio_titulo;
    }

    public int getTipo_jornada() {
        return tipo_jornada;
    }

    public void setTipo_jornada(int tipo_jornada) {
        this.tipo_jornada = tipo_jornada;
    }

    public int getCargo_actual() {
        return cargo_actual;
    }

    public void setCargo_actual(int cargo_actual) {
        this.cargo_actual = cargo_actual;
    }

    public int getCargo_segundario() {
        return cargo_segundario;
    }

    public void setCargo_segundario(int cargo_segundario) {
        this.cargo_segundario = cargo_segundario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String toString(){
        return nombre+"";
    }
}
