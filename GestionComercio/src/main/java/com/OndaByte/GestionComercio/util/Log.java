package com.OndaByte.GestionComercio.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log
 */
public class Log {

    public static void log(Exception e, Class c){
        Logger.getLogger(c.getName()).log(Level.SEVERE, null, e);
    }
}
