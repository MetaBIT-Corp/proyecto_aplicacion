package com.example.crud_encuesta.Componentes_R;

public class EstructuraTablas {
    //Modelo Escuela
    private int id;
    private String nombre;

    //Constantes de nombres de tabla Escuela
    public static final String ESCUELA_TABLA_NAME = "ESCUELA";
    public static final String COL_0 = "ID_ESCUELA";
    public static final String COL_1 = "NOMBRE_ESCUELA";

    //Constantes de Tabla Carrera
    public static final String CARRERA_TABLA_NAME = "CARRERA";
    public static final String COL_0_CARRERA = "ID_CARRERA";
    public static final String COL_1_CARRERA = "ID_ESCUELA";
    public static final String COL_2_CARRERA = "NOMBRE_CARRERA";

    //Constantes de Tabla Materia
    public static final String MATERIA_TABLA_NAME = "CAT_MAT_MATERIA";
    public static final String COL_0_MATERIA = "ID_CAT_MAT";
    public static final String COL_1_MATERIA = "ID_PENUM";
    public static final String COL_2_MATERIA = "ID_CARRERA";
    public static final String COL_3_MATERIA = "CODIGO_MAT";
    public static final String COL_4_MATERIA = "NOMBRE_MAR";
    public static final String COL_5_MATERIA = "ES_ELECTIVA";
    public static final String COL_6_MATERIA = "MAXIMO_CANT_PREGUNTAS";

    //Constantes de Tabla Pensum
    public static final String PENSUM_TABLA_NAME = "PENSUM";
    public static final String COL_0_PENSUM = "ID_PENUM";
    public static final String COL_1_PENSUM = "ANIO_PENSUM";

    //Constantes de Tabla Encuesta
    public static final String ENCUESTA_TABLA_NAME = "ENCUESTA";
    public static final String COL_0_ENCUESTA = "ID_ENCUESTA";
    public static final String COL_1_ENCUESTA = "ID_PDG_DCN";
    public static final String COL_2_ENCUESTA = "TITULO_ENCUESTA";
    public static final String COL_3_ENCUESTA = "DESCRIPCION_ENCUESTA";
    public static final String COL_4_ENCUESTA = "FECHA_INICIO_ENCUESTA";
    public static final String COL_5_ENCUESTA = "FECHA_FINAL_ENCUESTA";
}
