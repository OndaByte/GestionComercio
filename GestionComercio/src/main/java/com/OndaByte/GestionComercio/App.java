package com.OndaByte.GestionComercio;
import com.OndaByte.GestionComercio.Control.UsuarioControl;
import com.OndaByte.GestionComercio.Util.FiltroAutenticador;

import spark.Spark;

/**
 * App
 * @author Fran
 */
public class App 
{
    public static void main( String[] args )
    {
        Spark.before("/protegido/*", FiltroAutenticador.filtro);
        Spark.get("/protegido/test", (req, res) -> "Logueado con exito");
        Spark.get("/usuarios", UsuarioControl.usuarios);
        Spark.get("/registrar", UsuarioControl.registrar);
    }
}
