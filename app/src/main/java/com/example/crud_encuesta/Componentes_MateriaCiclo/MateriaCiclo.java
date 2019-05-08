package com.example.crud_encuesta.Componentes_MateriaCiclo;

public class MateriaCiclo {
    private int id;
    private int id_materia;
    private String ciclo,anio;

    public MateriaCiclo() {}

    public MateriaCiclo(int id_materia, String ciclo, String anio) {
        this.id_materia = id_materia;
        this.ciclo = ciclo;
        this.anio = anio;
    }

    public MateriaCiclo(int id, int id_materia, String ciclo, String anio) {
        this.id = id;
        this.id_materia = id_materia;
        this.ciclo = ciclo;
        this.anio = anio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
