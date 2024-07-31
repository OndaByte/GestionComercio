package com.OndaByte.GestionComercio;
import com.OndaByte.GestionComercio.control.UsuarioControl;
import com.OndaByte.GestionComercio.filtros.FiltroAutenticador;


import static spark.Spark.*;
/**
 * App
 * @author Fran
 */
public class App 
{
    public static void main( String[] args )
    {
        before("/protegido/*", FiltroAutenticador.filtro);
        get("/protegido/usuarios", UsuarioControl.usuarios);
        post("/registrar", UsuarioControl.registrar);
        post("/protegido/actualizar", UsuarioControl.cambiarcontra);
        get("/login", UsuarioControl.loginForm);
        post("/login", UsuarioControl.login);
        post("/protegido/eliminar", UsuarioControl.baja);
    }
}
