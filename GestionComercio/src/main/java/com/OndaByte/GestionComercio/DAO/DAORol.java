package com.OndaByte.GestionComercio.DAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sql2o.Connection;

import com.OndaByte.GestionComercio.modelo.Permiso;
import com.OndaByte.GestionComercio.modelo.Rol;

/**
 * DAORol
 */
public class DAORol extends ABMDAO<Rol> {
    private String clave = "id";

	@Override
	public Class<Rol> getClase() {
        return Rol.class;
	}

	@Override
	public String getClave() {
        return this.clave;
	}

    public List<Permiso> getPermisosRol(int id){
        String query = "SELECT Permiso.* FROM Permiso JOIN Rol_Permiso ON Rol_Permiso.permiso_id = Permiso.id WHERE Rol_Permiso.rol_id=:id";
        try(Connection con = DAOSql2o.getSql2o().open()){
            return con.createQuery(query).addParameter("id",id).executeAndFetch(Permiso.class);
        }
      catch(Exception e) {
            Logger.getLogger(DAORol.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<Rol> getRolesUsuario(int id){
        String query = "SELECT Rol.* FROM Rol JOIN Usuario_Rol ON Rol.id = Usuario_Rol.rol_id WHERE Usuario_Rol.usuario_id=:id";
        try(Connection con = DAOSql2o.getSql2o().open()){
            return con.createQuery(query).addParameter("id",id).executeAndFetch(Rol.class);
        }
      catch(Exception e) {
            Logger.getLogger(DAORol.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
