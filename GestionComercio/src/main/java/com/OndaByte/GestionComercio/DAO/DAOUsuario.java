package com.OndaByte.GestionComercio.DAO;

import com.OndaByte.GestionComercio.Modelo.Usuario;

public class DAOUsuario extends CrudDAO<Usuario>{
    private String tablePK = "id";
    private String tableName = "Usuario";

    public Class<T> getTClass(){
        return Usuario.class;
    };
    
    public String getTablePK(){return this.tablePK};
    
    public String getTableName(){return this.tableName};

}