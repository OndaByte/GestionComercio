package com.OndaByte.GestionComercio.Control;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.SourceLocator;

import com.OndaByte.GestionComercio.DAO.DAOUsuario;
import com.OndaByte.GestionComercio.Modelo.Usuario;
import com.OndaByte.GestionComercio.Util.Seguridad;

import spark.Request;
import spark.Response;
import spark.Route;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioControl {

    public static Route usuarios = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        List<Usuario> usuarios = dao.listar();
        return usuarios.toString();
    };

    public static Route cambiarcontra = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        String usuario = req.queryParams("usuario");
        String contra = req.queryParams("contra");
        String nueva = req.queryParams("nueva");
        
        //ESTO TENGO QUE MOVERLO A MANEJADOR DE EXCEPCIONES/CONTROLES
        if(usuario == null || contra == null) {
            res.status(400);
            return "Usuario y Contraseña requeridos";
        }
        Usuario aux = getUsuario(usuario);
        if (BCrypt.checkpw(contra, aux.getContra())){
            aux.setContra(BCrypt.hashpw(nueva, BCrypt.gensalt()));
            if(dao.modificar(aux)){
                res.status(201);
                return "Contraseña actualizada";
            }
            else{
                res.status(404);
                return "ERROR: No se pudo actualizar la contraseña";
            }
        }
        else{
            res.status(500);
            return "Error al loguear";
        }
    };

    public static Route registrar = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        String usuario = req.queryParams("usuario");
        String contra = req.queryParams("contra");
        
        //ESTO TENGO QUE MOVERLO A MANEJADOR DE EXCEPCIONES/CONTROLES
        if(usuario == null || contra == null) {
            res.status(400);
            return "Usuario y Contraseña requeridos";
        }
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(usuario);
        nuevo.setContra(BCrypt.hashpw(contra, BCrypt.gensalt()));
        if(dao.alta(nuevo)){
            res.status(201);
            return "Registro exitoso";
        }
        else{
            res.status(500);
            return "Error al registrar";
        }
    };

    public static Route loginForm = (Request req, Response res) -> {
        res.status(501);
        return "No implementado sory";
    };
    
    public static Route login = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        String usuario = req.queryParams("usuario");
        String contra = req.queryParams("contra");
        //ESTO TENGO QUE MOVERLO A MANEJADOR DE EXCEPCIONES/CONTROLES
        if(usuario == null || contra == null) {
            res.status(400);
            return "Usuario y Contraseña requeridos";
        }
        Usuario aux = getUsuario(usuario);
        if (BCrypt.checkpw(contra, aux.getContra())){
            System.out.println("asdasdasdasd");
            res.header("Token",Seguridad.getToken(usuario));
            System.out.println("asdasd");
            res.status(200);
            return "Loguin exitoso";
        }
        else{
            res.status(500);
            return "Error al loguear";
        }
    };

    public static Route baja = (Request req, Response res) -> {
        String id = req.queryParams("id");
        String borrar = req.queryParams("borrar");
        DAOUsuario dao = new DAOUsuario();
        return dao.baja(id,Boolean.valueOf(borrar));
    };

    private static Usuario getUsuario(String usuario){
        DAOUsuario dao = new DAOUsuario();
        List<String> campos = new ArrayList();
        List<String> valores = new ArrayList();
        List<Integer> condiciones = new ArrayList();
        campos.add("usuario");
        valores.add(usuario);
        condiciones.add(0);
        return dao.filtrar(campos, valores, condiciones).get(0);
    }
}
