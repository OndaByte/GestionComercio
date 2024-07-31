package com.OndaByte.GestionComercio.filtros;

import spark.Filter;
import spark.Request;
import spark.Response;

import com.OndaByte.GestionComercio.util.Seguridad;


/**
 * FiltroAutenticador
 * @author Fran
 */
public class FiltroAutenticador {
    public static Filter filtro = (Request req, Response res) -> {
        String aux = req.headers("Token");
        if (aux != null){
            String token = Seguridad.validar(aux);
            if(token != null && token.equals(aux)){
                res.header("Token",token);
                res.status(204);
            }
            else{
                res.redirect("/login");
            }
        }
        else{
            res.redirect("/login");
        }
    };
}
