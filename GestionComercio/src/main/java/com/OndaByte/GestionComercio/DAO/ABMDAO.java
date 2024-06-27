package com.OndaByte.GestionComercio.DAO;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;

import com.OndaByte.GestionComercio.Modelo.ObjetoBD;

/**
 * ABMDAO generico
 * @author Fran
 * @param <T>
 */
public abstract class ABMDAO <T> {
    abstract public Class<T> getTClass();
    
    abstract public String getTablePK();
    
    abstract public String getTableName();
    
    public void alta(T t) {
        try {
            Class c = t.getClass();
            if(c.getSuperclass() != ObjetoBD.class){
                throw (new Exception("La entidad debe heredar de ObjetoBD"));
            }
            String valores = " ("; 
            String columnas = " (";
            String nombre;
            for (Field f : c.getDeclaredFields()) {
                nombre=f.getName();
                columnas = columnas + " " + nombre + " ,";
                valores = valores + " :" + nombre + " ,";
            }
            valores = valores.substring(0,valores.length()-1) + ")";
            columnas = columnas.substring(0,columnas.length()-1)+ ")";

            String query = "INSERT INTO " + getTableName() + " " + columnas + " VALUES " + valores;
            Connection con = DAOSql2o.getSql2o().open();
            con.createQuery(query).bind(t).executeUpdate();
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void modificar(T t) {
        
        try {
            Class c = t.getClass();
            String set="";
            String nombre;
            if(c.getSuperclass() != ObjetoBD.class){
                throw (new Exception("La entidad debe heredar de ObjetoBD"));
            }
            for (Field f : c.getDeclaredFields()) {
                nombre=f.getName();
                if(nombre.equals(this.getTablePK())){
                    continue;
                }
                set = set + nombre + "=:" + nombre+", ";
            }
            if(set.length()>2)
                set = set.substring(0,set.length()-2);

            String query = "UPDATE " + getTableName() + " SET " + set + " WHERE "+this.getTablePK() + "=:"+this.getTablePK();
            System.out.println(query);
            Connection con = DAOSql2o.getSql2o().open();
            con.createQuery(query).bind(t).executeUpdate();
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean baja(){
        try{
            throw(new Exception("No implementado"));
        }
        catch(Exception e) {
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public List<T> getAll(){
        Class c = this.getTClass();
        String query = "SELECT * FROM "+ this.getTableName() + " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTableName() +".id";
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<T> getAll(String... ids){
         try{
            throw(new Exception("No implementado"));
        }
        catch(Exception e) {
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public T get(String id) {
        Class c = this.getTClass();
        String query = "SELECT * FROM "+ this.getTableName() + " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTableName() +".id";
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return (T) con.createQuery(query).executeScalar(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /*
    public void addAndFiltro(String ){
        String query = "SELECT * FROM "+ this.getTableName();
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public T get(T t){
        Class c = t.getClass();
        
        String query = "SELECT * FROM"+ this.getTableName() +" WHERE "+this.getTablePK() + "=:"+this.getTablePK();
        try{
            Connection con = DAOSql2o.getSql2o().open();
            con.createQuery(query).bind(t).executeAndFetch(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }*/
}
