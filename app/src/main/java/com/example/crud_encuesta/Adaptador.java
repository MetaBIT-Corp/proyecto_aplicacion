package com.example.crud_encuesta;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private ArrayList<GrupoEmparejamiento> lista_gpo_emp;
    DaoGrupoEmp dao;
    GrupoEmparejamiento gpo_emp;
    Activity a;

    public Adaptador(ArrayList<GrupoEmparejamiento> lista_gpo_emp, DaoGrupoEmp dao, Activity a) {
        this.lista_gpo_emp = lista_gpo_emp;
        this.dao = dao;
        this.a = a;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
