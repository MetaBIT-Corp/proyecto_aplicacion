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
    public static final String COL_1_CARRERA= "ID_ESCUELA";
    public static final String COL_2_CARRERA= "NOMBRE_CARRERA";
}
