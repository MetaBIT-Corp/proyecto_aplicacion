package com.example.crud_encuesta.Componentes_MT.Area;

public class Area {
    int id;
    String titulo;
    int id_materia;
    int id_docente;
    int id_tipo_itemm;

    public Area(int id, String titulo, int id_materia, int id_docente, int id_tipo_itemm) {
        this.id = id;
        this.titulo = titulo;
        this.id_materia = id_materia;
        this.id_docente = id_docente;
        this.id_tipo_itemm = id_tipo_itemm;
    }

    public Area(String titulo, int id_materia, int id_docente, int id_tipo_itemm) {
        this.id = id;
        this.titulo = titulo;
        this.id_materia = id_materia;
        this.id_docente = id_docente;
        this.id_tipo_itemm = id_tipo_itemm;
    }

    public Area(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.id_materia = id_materia;
        this.id_docente = id_docente;
        this.id_tipo_itemm = id_tipo_itemm;
    }
}
