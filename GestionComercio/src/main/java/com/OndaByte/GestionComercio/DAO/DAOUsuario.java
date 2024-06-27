package com.OndaByte.GestionComercio.DAO;

import com.OndaByte.GestionComercio.Modelo.Usuario;

public class DAOUsuario extends ABMDAO<Usuario>{
    private String tablePK = "id";
    private String tableName = "Usuario";

    public Class<Usuario> getTClass(){
        return Usuario.class;
    };

    public String getTablePK(){return this.tablePK;}
    
    public String getTableName(){return this.tableName;}
}
