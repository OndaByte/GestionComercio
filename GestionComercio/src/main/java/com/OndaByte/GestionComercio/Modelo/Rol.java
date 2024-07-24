package com.OndaByte.GestionComercio.Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Rol
 */
public class Rol {
    private int id;
    private String nombre;
    private List<Permiso> permisos;

    public Rol (){
       permisos = new ArrayList<>();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
}
