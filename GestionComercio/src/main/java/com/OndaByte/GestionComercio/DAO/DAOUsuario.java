package com.OndaByte.GestionComercio.DAO;

import com.OndaByte.GestionComercio.Modelo.Usuario;

public class DAOUsuario extends ABMDAO<Usuario>{
    private String clave = "id";

    public DAOUsuario(){
        this.setHereda();
    }

    public Class<Usuario> getClase(){
        return Usuario.class;
    };

    public String getClave(){return this.clave;}
    
}
