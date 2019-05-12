package com.example.crud_encuesta.Componentes_AP.Models;

public class PensumMateria {
    int idPensumMateria;
    int idMateria;
    int idPensum;

    public PensumMateria(int idPensumMateria, int idMateria, int idPensum) {
        this.idPensumMateria = idPensumMateria;
        this.idMateria = idMateria;
        this.idPensum = idPensum;
    }

    public PensumMateria(int idMateria, int idPensum) {
        this.idMateria = idMateria;
        this.idPensum = idPensum;
    }

    public int getIdPensumMateria() {
        return idPensumMateria;
    }

    public void setIdPensumMateria(int idPensumMateria) {
        this.idPensumMateria = idPensumMateria;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdPensum() {
        return idPensum;
    }

    public void setIdPensum(int idPensum) {
        this.idPensum = idPensum;
    }
}
