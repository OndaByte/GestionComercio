package com.OndaByte.GestionComercio.DAO;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;

import com.OndaByte.GestionComercio.util.Log;

/**
 * ABMDAO generico SQL para entidades simples, soporta maximo una herencia de entidad, ej Usuario extend ObjetoBD
 * @author Fran
 * @param <T>
 */
public abstract class ABMDAO <T> {
    private boolean hereda=false; //Si hereda en la bd de una abstraccion, ej ObjetoBD
    private Class padre = null; //Tengo que implementar esto, que cuando lo setee true de alta al padre y al hijo correspondiente, en false trabajo con tablas simples

    abstract public Class<T> getClase();
    
    abstract public String getClave();
    
    public String getTabla(){return this.getClase().getSimpleName();}

    public void setHereda(){
        this.padre=this.getClase().getSuperclass();
        this.hereda = true;
    }

    private Class getPadre(){return this.padre;}
    
    public boolean alta(T t) {
        try(Connection con = DAOSql2o.getSql2o().beginTransaction()){
            if(t.getClass() != this.getClase()){
                throw (new Exception("ERROR: el objeto pasado por parametro es del tipo incorrecto, el tipo de este DAO es: "+this.getClase().getName()));
            }
            String valores = " ("; 
            String columnas = " (";
            String nombre="";
            String query;
            int id = -1;
            if(hereda){
                for (Field f : this.getPadre().getDeclaredFields()) {
                    nombre=f.getName();
                    if(nombre.equals(this.getClave()) || nombre.equals("creado")) continue;
                    columnas = columnas + " " + nombre + " ,";
                    valores = valores + " :" + nombre + " ,";
                }
                valores = valores.substring(0,valores.length()-1) + ")";
                columnas = columnas.substring(0,columnas.length()-1)+ ")";
                query = "INSERT INTO "+ this.padre.getSimpleName() +" "+ columnas + " VALUES " + valores;
                id = con.createQuery(query, true).bind(this.getPadre().cast(t)).executeUpdate().getKey(int.class);
                valores = " ("; 
                columnas = " (";
            }
            for (Field f : this.getClase().getDeclaredFields()) {
                nombre=f.getName();
                columnas = columnas + " " + nombre + " ,";
                valores = valores + " :" + nombre + " ,";
            }
            if(hereda){
                columnas = columnas + " id";
                valores = valores + " "+id;
            }else {
                valores = valores.substring(0,valores.length()-1);
                columnas = columnas.substring(0,columnas.length()-1);
            }
            columnas +=")";
            valores +=")";
            query = "INSERT INTO " + this.getTabla() + " " + columnas + " VALUES " + valores;
            con.createQuery(query).bind(t).executeUpdate();
            con.commit();
            return true;
        }
        catch (Exception e){
            Log.log(e, ABMDAO.class);
        }
        return false;
    }

    public boolean modificar(T t) {
        try(Connection con = DAOSql2o.getSql2o().beginTransaction()){
            if(t.getClass() != this.getClase()){
                throw (new Exception("ERROR: el objeto pasado por parametro es del tipo incorrecto, el tipo de este DAO es: "+this.getClase().getName()));
            }
            String nombre="";
            String set="";
            String query;
            if(hereda){
                for (Field f : this.getPadre().getDeclaredFields()) {
                    nombre=f.getName();
                    if(nombre.equals(this.getClave()) || nombre.equals("creado")) continue;
                    set = set + nombre + "=:" + nombre+", ";
                }
                if(set.length()>2)
                    set = set.substring(0,set.length()-2);
                query = "UPDATE " + this.padre.getSimpleName() + " SET " + set + " WHERE "+this.getClave() + "=:"+this.getClave();
                con.createQuery(query, true).bind(this.getPadre().cast(t)).executeUpdate();
                set="";
            }
            for (Field f : this.getClase().getDeclaredFields()) {
                nombre=f.getName();
                if(nombre.equals(this.getClave())) continue;
                set = set + nombre + "=:" + nombre+", ";
            }
            if(set.length()>2)
                set = set.substring(0,set.length()-2);
            query = "UPDATE " + this.getTabla() + " SET " + set + " WHERE "+this.getClave() + "=:"+this.getClave();
            
            con.createQuery(query).bind(t).executeUpdate();
            con.commit();
            return true;
        }
        catch (Exception e){
            Log.log(e, ABMDAO.class);
        }
        return false;
    }

    public boolean baja(String id, boolean borrar){
        try(Connection con = DAOSql2o.getSql2o().open()){
            String query;
            query = (borrar ? "DELETE FROM ": "UPDATE ") 
                + (hereda ? this.padre.getSimpleName() : this.getTabla()) 
                + (borrar ? " " : " SET estado=\"INACTIVO\" ")+"WHERE "+this.getClave() + "=:"+this.getClave()
                + (borrar ? "" : " AND estado=\"ACTIVO\"");
            con.createQuery(query, true).addParameter(this.getClave(), id).executeUpdate();
            return true;
        }
        catch(Exception e) {
            Log.log(e, ABMDAO.class);
        }
        return false;
    }
    
    public List<T> listar(){
        try{
            Class c = this.getClase();
            String query = "SELECT * FROM "+ this.getTabla() + 
                (hereda ? 
                 (" INNER JOIN "+this.getPadre().getSimpleName()+" WHERE "+this.getPadre().getSimpleName()+".id="+this.getTabla()+".id AND ") 
                 : " WHERE ") +"estado=\"ACTIVO\"";
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Log.log(e, ABMDAO.class);
        }
        return null;
    }

    public List<T> listar(String... ids){
        try{
            Class c = this.getClase();
            String aux="";
            for (String id : ids){
                aux += this.getTabla()+"."+this.getClave()+"="+id+" OR ";
            }
            aux = aux.length() > 2 ? aux.substring(0,aux.length()-4) : aux;

            String query = "SELECT DISTINCT * FROM "+ this.getTabla() + 
                (hereda ? 
                 (" INNER JOIN "+this.getPadre().getSimpleName()+" WHERE ("+this.getPadre().getSimpleName()+".id="+ this.getTabla() +".id) AND ("+aux+")")
                 : " WHERE "+aux) +" AND estado=\"ACTIVO\"";
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch(Exception e) {
            Log.log(e, ABMDAO.class);
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
            String tablaCampoAux = "";
            for (String campo : campos){
                if (!Arrays.stream(objetobd.getDeclaredFields()).anyMatch(x -> x.getName().equals(campo))){
                    esPrimitivo = false;
                    if(!Arrays.stream(c.getDeclaredFields()).anyMatch(x -> x.getName().equals(campo))){
                        throw(new Exception("El campo no existe"));
                    } 
                    tablaCampoAux =  this.getTabla();
                }
                else{
                    esPrimitivo = true;
                    tablaCampoAux = this.padre.getSimpleName();
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
            String query = "SELECT * FROM "+ this.getTabla() + 
                (hereda ? " INNER JOIN ObjetoBD WHERE ObjetoBD.id="+ this.getTabla() +".id AND" : "");
            query+= queryAux; 
            Connection con = DAOSql2o.getSql2o().open();
            return con.createQuery(query).executeAndFetch(c);
        }
        catch (Exception e){
            Log.log(e, ABMDAO.class);
        }
        return null;
    }
}
