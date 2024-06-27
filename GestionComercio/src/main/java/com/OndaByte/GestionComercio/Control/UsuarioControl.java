package com.OndaByte.GestionComercio.Control;
import java.util.List;

import com.OndaByte.GestionComercio.DAO.DAOUsuario;
import com.OndaByte.GestionComercio.Modelo.Usuario;

import spark.Request;
import spark.Response;
import spark.Route;
//import com.fasterxml.jackson.databind.ObjectMapper;

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
        List<Usuario> usuarios = dao.getAll();
        for (Usuario u : usuarios){
            System.out.println(u.toString());
        }
        return "";
    };
}
