package com.OndaByte.GestionComercio.Control;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.SourceLocator;

import com.OndaByte.GestionComercio.DAO.DAOUsuario;
import com.OndaByte.GestionComercio.Modelo.Usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioControl {
    //ESTE FILTRO ES SOLO PARA TESTEO ACTUALMENTE, DEMASIADO POWERFULL SI LE HABILITO POR HTTP A FILTRAR, HAY QUE HACER CONTROLES
    public static Route usuariosFiltrar = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        List<String> campos = new ArrayList();
        List<String> valores = new ArrayList();
        List<Integer> condiciones = new ArrayList();
        //campos.add("creado");
        //campos.add("creado");
        //campos.add("estado");

        //valores.add("2024-06-29");
        //valores.add("2024-11-02");
        //valores.add("ACTIVO");
        //condiciones.add(0);
        condiciones.add(1);
        //condiciones.add(0);
        List<Usuario> usuarios = dao.filtrar(campos, valores, condiciones);
        String resul = "[";
        for (Usuario u : usuarios){
            resul += u.toString()+",";
        }
        if(resul.length() > 2){
            resul = resul.substring(0,resul.length()-2);
        }
        resul += "]";
        return resul;
    };

    public static Route usuarios = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        List<Usuario> usuarios = dao.listar();
        String resul = "[";
        for (Usuario u : usuarios){
            resul += u.toString()+",";
        }
        if(resul.length() > 2){
            resul = resul.substring(0,resul.length()-2);
        }
        resul += "]";
        return resul;
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
    
    
    public static Route login= (Request req, Response res) -> {
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
            res.status(200);
            return "Loguin exitoso";
        }
        else{
            res.status(500);
            return "Error al loguear";
        }
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
