package com.example.crud_encuesta.Componentes_AP.Models;

import java.util.Scanner;

public class Usuario {
    int IDUSUARIO;
    String NOMUSUARIO;
    String CLAVE;
    int ROL;

    public Usuario() {
    }

    public Usuario(int IDUSUARIO, String NOMUSUARIO, String CLAVE, int ROL) {
        this.IDUSUARIO = IDUSUARIO;
        this.NOMUSUARIO = NOMUSUARIO;
        this.CLAVE = CLAVE;
        this.ROL = ROL;
    }

    public Usuario(String NOMUSUARIO, String CLAVE, int ROL) {
        this.NOMUSUARIO = NOMUSUARIO;
        this.CLAVE = CLAVE;
        this.ROL = ROL;
    }

    public int getIDUSUARIO() {
        return IDUSUARIO;
    }

    public void setIDUSUARIO(int IDUSUARIO) {
        this.IDUSUARIO = IDUSUARIO;
    }

    public String getNOMUSUARIO() {
        return NOMUSUARIO;
    }

    public void setNOMUSUARIO(String NOMUSUARIO) {
        this.NOMUSUARIO = NOMUSUARIO;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public int getROL() {
        return ROL;
    }

    public void setROL(int ROL) {
        this.ROL = ROL;
    }
}

