package com.OndaByte.GestionComercio;
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
        before("/protegido/*", FiltroAutenticador.filtro);
        get("/protegido/test", (req, res) -> "Logueado con exito");
        get("/usuarios", UsuarioControl.usuarios);
        get("/usuarios/filtrar", UsuarioControl.usuariosFiltrar);
        post("/registrar", UsuarioControl.registrar);
        post("/actualizar", UsuarioControl.cambiarcontra);
        get("/login", UsuarioControl.login);
    }
}
