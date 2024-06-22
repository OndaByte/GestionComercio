package com.OndaByte.GestionComercio;
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
        Spark.get("/test", (req, res) -> "Sin loguear");
        Spark.get("/protegido/test", (req, res) -> "Logueado con exito");
    }
}
