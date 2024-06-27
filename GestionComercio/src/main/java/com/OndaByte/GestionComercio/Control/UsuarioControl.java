package com.OndaByte.GestionComercio.Control;
import java.util.List;

import com.OndaByte.GestionComercio.DAO.DAOUsuario;
import com.OndaByte.GestionComercio.Modelo.Usuario;

import spark.Request;
import spark.Response;
import spark.Route;

public class UsuarioControl {
    static Route login = (Request req, Response res) -> {
        String json = req.queryParams("usuario");
        //ObjectMapper mapper = new ObjectMapper();
        //Usuario usuario = mapper.readValue(json, Usuario.class);
        System.out.println(json);
        return "";
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
        Usuario aux = new Usuario();
        System.out.println(aux.toString());
        aux.setUsuario("Dismaster");
        aux.setId(57);
        return dao.alta(aux);
    };
}
