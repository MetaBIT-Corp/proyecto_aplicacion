package com.example.crud_encuesta.Componentes_EL.Encuesta;
import com.example.crud_encuesta.Componentes_Docente.Docente;

public class Encuesta {

    private int id;
    private String titulo,descripcion,fecha_in,fecha_fin;
    private Docente id_docente;

    public Encuesta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Docente getId_docente() {
        return id_docente;
    }

    public void setId_docente(Docente id_docente) {
        this.id_docente = id_docente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(String fecha_in) {
        this.fecha_in = fecha_in;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String toString(){
        return "Titulo: "+titulo+"\nDescripcion: "+descripcion+"\nFecha Inicio: "+fecha_in+"\nFecha Fin: "+fecha_fin+"\nDocente que la Creo: "+id_docente;
    }
}
