package com.OndaByte.GestionComercio.modelo;

/**
 * Permiso
 */
public class Permiso {
    private int id;
    private String nombre;

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
    public String toString(){
        return "{ id : "+id+", nombre : "+nombre+" }";
    }
}
