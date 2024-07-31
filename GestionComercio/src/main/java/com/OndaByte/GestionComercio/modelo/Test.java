package com.OndaByte.GestionComercio.modelo;

/**
 * Test
 * @author Fran
 */
public class Test {
    private int id;
    private String texto;
    private String estado = "ACTIVO";

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

    public String toString(){
        return "{id : "+this.id+", texto : \""+this.texto+"\" }";
    }
}
