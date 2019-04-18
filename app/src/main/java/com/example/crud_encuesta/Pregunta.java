package com.example.crud_encuesta;

public class Pregunta {

    private int id;
    private int id_grupo_emp;
    private String pregunta;

    public Pregunta() {
    }

    public Pregunta(int id_grupo_emp, String pregunta) {
        this.id_grupo_emp = id_grupo_emp;
        this.pregunta = pregunta;
    }

    public Pregunta(int id, int id_grupo_emp, String pregunta) {
        this.id = id;
        this.id_grupo_emp = id_grupo_emp;
        this.pregunta = pregunta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_grupo_emp() {
        return id_grupo_emp;
    }

    public void setId_grupo_emp(int id_grupo_emp) {
        this.id_grupo_emp = id_grupo_emp;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }



}
