package com.OndaByte.GestionComercio.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;

public class Seguridad {
    private static String clave = "IqZks/oD7sogY2zrLdkcluLiezFP/s/UbUsxiGEV/ksvFNREePrGYvX5e6dO7xC0KE7LDkyQMfNW";
    private static int expiracion = 42;
    private static int limite = expiracion;

    /**
     * Expiracion en horas 
     * @param usuario
     * @param expiracion
     * @return token
    **/
    public static String getToken(String usuario){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, expiracion);
        try{
        return Jwts.builder()
            .setSubject(usuario)
            .setExpiration(cal.getTime())
            .signWith(SignatureAlgorithm.HS512, clave.getBytes())
            .compact();
        }
        catch (Exception e){
            Log.log(e, Seguridad.class);
        }
        return null;
    }

    public static String validar(String token){
        try{
            Jwts.parser()
            .setSigningKey(clave.getBytes())
            .parseClaimsJws(token);
            return token;
        }
        catch (ExpiredJwtException e){
            Claims cls = e.getClaims();
            Date exp = cls.getExpiration();
            String usr = cls.getSubject();
            Date aux = new Date();
            long milSeg = aux.getTime() - exp.getTime();
            long hras = milSeg/ 3600000;
            if (hras > limite){
                return null;
            }
            return getToken(usr);
        }
        catch (Exception e){
            Log.log(e, Seguridad.class);
        }
        return null;
    }
}
