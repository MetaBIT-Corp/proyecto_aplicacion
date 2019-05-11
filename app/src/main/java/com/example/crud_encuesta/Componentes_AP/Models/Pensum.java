package com.example.crud_encuesta.Componentes_AP.Models;

public class Pensum {
    int idPensum;
    int idCarrera;
    int anio;

    public Pensum(int idPensum, int idCarrera, int anio) {
        this.idPensum = idPensum;
        this.idCarrera = idCarrera;
        this.anio = anio;
    }

    public Pensum(int idCarrera, int anio) {
        this.idCarrera = idCarrera;
        this.anio = anio;
    }

    public Pensum() {
    }

    public int getIdPensum() {
        return idPensum;
    }

    public void setIdPensum(int idPensum) {
        this.idPensum = idPensum;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
