package com.example.crud_encuesta.Componentes_MR.Docente;

public class Docente {

    private int id;
    private String carnet, nombre, descripcion, anio_titulo;
    private int tipo_jornada, cargo_actual, cargo_secundario,activo;
    private int id_escuela, id_usuario;

    public Docente(){}

    public Docente(int id, int id_escuela, String carnet, String anio_titulo, int activo, int tipo_jornada, String descripcion, int cargo_actual, int cargo_secundario,String nombre) {
        this.id = id;
        this.id_escuela = id_escuela;
        this.carnet = carnet;
        this.anio_titulo = anio_titulo;
        this.activo = activo;
        this.tipo_jornada = tipo_jornada;
        this.descripcion = descripcion;
        this.cargo_actual = cargo_actual;
        this.cargo_secundario = cargo_secundario;
        this.nombre = nombre;
    }

    public Docente(int id, int id_escuela, String carnet, String anio_titulo, int activo, int tipo_jornada, String descripcion, int cargo_actual, int cargo_secundario,String nombre, int id_usuario) {
        this.id = id;
        this.id_escuela = id_escuela;
        this.carnet = carnet;
        this.anio_titulo = anio_titulo;
        this.activo = activo;
        this.tipo_jornada = tipo_jornada;
        this.descripcion = descripcion;
        this.cargo_actual = cargo_actual;
        this.cargo_secundario = cargo_secundario;
        this.nombre = nombre;
        this.id_usuario = id_usuario;
    }

    public Docente(int id_escuela, String carnet, String anio_titulo, int activo, int tipo_jornada, String descripcion, int cargo_actual, int cargo_secundario,String nombre,int id_usuario) {
        this.id_escuela = id_escuela;
        this.carnet = carnet;
        this.anio_titulo = anio_titulo;
        this.activo = activo;
        this.tipo_jornada = tipo_jornada;
        this.descripcion = descripcion;
        this.cargo_actual = cargo_actual;
        this.cargo_secundario = cargo_secundario;
        this.nombre = nombre;
        this.id_usuario = id_usuario;
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

    public int getCargo_secundario() {
        return cargo_secundario;
    }

    public void setCargo_secundario(int cargo_secundario) {
        this.cargo_secundario = cargo_secundario;
    }

    public int getId_escuela() {
        return id_escuela;
    }

    public void setId_escuela(int id_escuela) {
        this.id_escuela = id_escuela;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "carnet='" + carnet + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", anio_titulo=" + anio_titulo +
                ", tipo_jornada=" + tipo_jornada +
                ", cargo_actual=" + cargo_actual +
                ", cargo_secundario=" + cargo_secundario +
                ", activo=" + activo +
                '}';
    }
}
