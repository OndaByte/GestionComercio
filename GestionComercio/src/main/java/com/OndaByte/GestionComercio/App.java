package com.OndaByte.GestionComercio;
import com.OndaByte.GestionComercio.Control.TestController;
import com.OndaByte.GestionComercio.Control.UsuarioControl;
import com.OndaByte.GestionComercio.Filtros.FiltroAutenticador;


import static spark.Spark.*;
/**
 * App
 * @author Fran
 */
public class App 
{
    public static void main( String[] args )
    {
        //before("/protegido/*", FiltroAutenticador.filtro);
        get("/usuarios", UsuarioControl.usuarios);
        post("/registrar", UsuarioControl.registrar);
        post("/actualizar", UsuarioControl.cambiarcontra);
        get("/login", UsuarioControl.login);
        post("/eliminar", UsuarioControl.baja);
    }
}
