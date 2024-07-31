package com.OndaByte.GestionComercio.control;

import java.util.List;

import com.OndaByte.GestionComercio.DAO.DAOTest;
import com.OndaByte.GestionComercio.modelo.Test;

import spark.Request;
import spark.Response;
import spark.Route;
/**
 * TestController
 */
public class TestController {
    public static Route alta = (Request req, Response res) -> {
        DAOTest dao = new DAOTest();
        Test aux = new Test();
        aux.setTexto("ASDASDASDASDASDASDASD");
        return dao.alta(aux);
    };

    public static Route listar = (Request req, Response res) -> {
        DAOTest dao = new DAOTest();
        List<Test> tests = dao.listar();
        String resul = "[";
        for (Test t : tests){
            resul += t.toString()+",";
        }
        if(resul.length() > 2){
            resul = resul.substring(0,resul.length()-2);
        }
        resul += "]";
        return resul;
    };
}
