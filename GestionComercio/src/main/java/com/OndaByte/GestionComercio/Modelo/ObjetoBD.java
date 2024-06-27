package com.OndaByte.GestionComercio.Modelo;

import java.util.Date;

/**
 * ObjetoBD generico, todos los objetos de cualquier BD estara en la tabla ObjetoBD
 * Objetos especifico heredaran de ObjetoBD y tendran sus tablas propias que referenciaran a la tabla ObjetoBD
 * @author Fran
 */
public abstract class ObjetoBD {
    private Long id;
    private Date creado=new Date();
    private Date ultMod=new Date();
    private String estado="ACTIVO";
    private String tipo;
    
	public ObjetoBD(String tipo){ this.tipo = tipo;}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreado() {
		return creado;
	}
	public void setCreado(Date creado) {
		this.creado = creado;
	}
	public Date getUltMod() {
		return ultMod;
	}
	public void setUltMod(Date ultMod) {
		this.ultMod = ultMod;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    public String getTipo() {
		return tipo;
	}
    public String toString(){
        return "id : "+id+", "+"creado : "+creado.toString()+", ultMod : "+ultMod.toString()+", estado : "+estado+", tipo : "+tipo;
    }
}
