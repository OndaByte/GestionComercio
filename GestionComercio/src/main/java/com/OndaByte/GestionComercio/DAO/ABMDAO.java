package com.OndaByte.GestionComercio.DAO;

import java.lang.reflect.Field;
import java.util.Arrays;
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
    abstract public Class<T> getClase();
    
    abstract public String getClave();
    
    abstract public String getTabla();
    
    public boolean alta(T t) {
        try(Connection con = DAOSql2o.getSql2o().beginTransaction()){
            Class c = t.getClass();
            Class objetobd = c.getSuperclass();
            if(objetobd != ObjetoBD.class){
                throw (new Exception("La entidad debe heredar de ObjetoBD"));
            }
            String valores = " ("; 
            String columnas = " (";
            String nombre="";
            for (Field f : objetobd.getDeclaredFields()) {
                nombre=f.getName();
                if(nombre.equals("id")) continue;
                columnas = columnas + " " + nombre + " ,";
                valores = valores + " :" + nombre + " ,";
            }
            valores = valores.substring(0,valores.length()-1) + ")";
            columnas = columnas.substring(0,columnas.length()-1)+ ")";
            String query = "INSERT INTO ObjetoBD " + columnas + " VALUES " + valores;
            int id = con.createQuery(query, true).bind((ObjetoBD) t).executeUpdate().getKey(int.class);

            valores = " ("; 
            columnas = " (";
            nombre="";
            for (Field f : c.getDeclaredFields()) {
                nombre=f.getName();
                columnas = columnas + " " + nombre + " ,";
                valores = valores + " :" + nombre + " ,";
            }
            columnas = columnas + " id)";
            valores = valores + " "+id+")";
            query = "INSERT INTO " + getTabla() + " " + columnas + " VALUES " + valores;
            con.createQuery(query).bind(t).executeUpdate();
            con.commit();
            return true;
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean modificar(T t) {
        try {
            Class c = t.getClass();
            String set="";
            String nombre;

            if(c.getSuperclass() != ObjetoBD.class){
                throw (new Exception("La entidad debe heredar de ObjetoBD"));
            }
            for (Field f : c.getDeclaredFields()) {
                nombre=f.getName();
                if(nombre.equals(this.getClave())){
                    continue;
                }
                set = set + nombre + "=:" + nombre+", ";
            }
            if(set.length()>2)
                set = set.substring(0,set.length()-2);

            String query = "UPDATE " + getTabla() + " SET " + set + " WHERE "+this.getClave() + "=:"+this.getClave();
            Connection con = DAOSql2o.getSql2o().open();
            con.createQuery(query).bind(t).executeUpdate();
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
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
    
    public List<T> listar(){
        Class c = this.getClase();
        String query = "SELECT * FROM "+ this.getTabla() + " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTabla() +".id";
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<T> listar(String... ids){
         try{
            throw(new Exception("No implementado"));
        }
        catch(Exception e) {
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public T listar(String id) {
        Class c = this.getClase();
        String query = "SELECT * FROM "+ this.getTabla() + " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTabla() +".id";
        try{
            Connection con = DAOSql2o.getSql2o().open();
            return (T) con.createQuery(query).executeScalar(c);
        }
        catch (Exception e){
            Logger.getLogger(ABMDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<T> filtrar(List<String> campos, List<String> valores, List<Integer> condiciones){
        try{
            if(campos == null || valores == null || condiciones == null || condiciones.size() != campos.size() || campos.size() != valores.size()){
                throw(new Exception("Las listas deben tener el mismo tamaño"));
            }
            Class c = this.getClase();
            Class objetobd = c.getSuperclass();
            String queryAux = " ";
            int i = 0; 
            boolean esPrimitivo = false;
            String tablaCampoAux = "ObjetoBD";
            for (String campo : campos){
                // si uno de los campos no existe
                if (!Arrays.stream(objetobd.getDeclaredFields()).anyMatch(x -> x.getName().equals(campo))){
                    esPrimitivo = false;
                    if(!Arrays.stream(c.getDeclaredFields()).anyMatch(x -> x.getName().equals(campo))){
                        throw(new Exception("El campo no existe"));
                    } 
                    tablaCampoAux =  this.getTabla();
                }
                else{
                    esPrimitivo = true;
                    tablaCampoAux = "ObjetoBD";
                }
                switch (condiciones.get(i)) {
                    case 0:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+"=\""+valores.get(i)+"\" AND ";
                        break;
                    case 1:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+"<=\""+valores.get(i)+"\" AND ";
                        break;
                    case 2:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+"<\""+valores.get(i)+"\" AND ";
                        break;
                    case 3:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+">=\""+valores.get(i)+"\" AND ";
                        break;
                    case 4:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+">\""+valores.get(i)+"\" AND ";
                        break;
                    case 5:
                        queryAux +=tablaCampoAux+"."+campos.get(i)+" LIKE \""+valores.get(i)+"\" AND ";
                        break;
                }
                i++;
            }
            if(queryAux.length() > 1){queryAux = queryAux.substring(0, queryAux.length()-5);}
            String query = "SELECT * FROM "+ this.getTabla() + " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTabla() +".id AND";
            query+= queryAux; 
        
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
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
    } */
}
