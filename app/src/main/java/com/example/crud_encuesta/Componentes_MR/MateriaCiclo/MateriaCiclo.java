package com.example.crud_encuesta.Componentes_MR.MateriaCiclo;

public class MateriaCiclo {
    private int id;
    private int id_materia, ciclo;
    private String anio;

    public MateriaCiclo() {}

    public MateriaCiclo(int id, int id_materia, int ciclo, String anio) {
        this.id = id;
        this.id_materia = id_materia;
        this.ciclo = ciclo;
        this.anio = anio;
    }

    public MateriaCiclo(int id_materia, int ciclo, String anio) {
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

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
