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

    public static Route registrar = (Request req, Response res) -> {
        DAOUsuario dao = new DAOUsuario();
        String usuario = req.queryParams("usuario");
        String contra = req.queryParams("contra");
        
        //ESTO TENGO QUE MOVERLO A MANEJADOR DE EXCEPCIONES/CONTROLES
        if(usuario == null || contra == null) {
            res.status(400);
            return "Usuario y Contraseña requeridos";
        }
        System.out.println(usuario+contra);

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
        List<String> campos = new ArrayList();
        List<String> valores = new ArrayList();
        List<Integer> condiciones = new ArrayList();
        campos.add("usuario");
        valores.add(usuario);
        condiciones.add(0);
        Usuario aux = dao.filtrar(campos, valores, condiciones).get(0);
        boolean autenticar = BCrypt.checkpw(contra, aux.getContra());
        if (autenticar){
            res.status(200);
            return "Loguin exitoso";
        }
        else{
            res.status(500);
            return "Error al loguear";
        }
    };
}
