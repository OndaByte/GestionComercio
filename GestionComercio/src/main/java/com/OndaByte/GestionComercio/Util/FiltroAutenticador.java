package com.OndaByte.GestionComercio.Util;

import spark.Filter;
import spark.Request;
import spark.Response;
import java.util.Base64;
import static spark.Spark.halt;

/**
 * FiltroAutenticador
 * @author Fran
 */
public class FiltroAutenticador {
    public static Filter filtro = (Request request, Response response) -> {
        String header = request.headers("Authorization");
        if (header == null || !header.startsWith("Basic")) {
            halt(401, "Error: no autorizado.");
            return;
        }
        // LOGICA LOGUIN CAMBIAR, ABSTRAER NUEVA CLASE DE SER POSIBLE PUTO EL Q LEE
        String credencialesHash = header.substring("Basic".length()).trim();
        System.out.println(credencialesHash);
        String credenciales = new String(Base64.getDecoder().decode(credencialesHash));
        // usuario:contra
        final String[] aux = credenciales.split(":", 2);

        String usuario = aux[0];
        String contra = aux[1];

        if (!controlCredenciales(usuario, contra)) {
            halt(401, "Error: credenciales invalidas");
        }
    };
     private static boolean controlCredenciales(String usuario, String contra) {
        //Conectar DAO usuarios
        return "usuario".equals(usuario) && "contra".equals(contra);
    }
}
