package com.example.crud_encuesta.Componentes_MT.ClaveArea;

public class ClaveArea {
    String area;
    String clave;
    int id_ca;
    int aleatorio;
    int numero_preguntas;
    int peso;

    public ClaveArea(String area, String clave, int id_ca, int aleatorio, int numero_preguntas, int peso) {
        this.area = area;
        this.clave = clave;
        this.id_ca = id_ca;
        this.aleatorio = aleatorio;
        this.numero_preguntas = numero_preguntas;
        this.peso = peso;
    }
}
