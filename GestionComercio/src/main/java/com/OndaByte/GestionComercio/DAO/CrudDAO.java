package com.OndaByte.GestionComercio.DAO;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;

/**
 * CrudDAO
 * @author Fran
 * @param <T>
 */
public abstract class CrudDAO<T> {
    abstract public Class<T> getTClass();
    
    abstract public String getTablePK();
    
    abstract public String getTableName();
    
    public void insert(T t) {
        Class c = t.getClass();
        String valores = " ("; 
        String columnas = " (";
        String nombre;
        try {
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
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void update(T t) {
        Class c = t.getClass();
        String set="";
        String nombre;
        try {
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
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<T> getAll(){
        Class c = this.getTClass();
        String query = "SELECT * FROM "+ this.getTableName();
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    

    public T get(String id) {
        String query = "SELECT * FROM "+ this.getTableName();
        Class c = this.getTClass();
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c).executeAndFetchUnique();
        }
        catch (Exception e){
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(CrudDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }*/
}
