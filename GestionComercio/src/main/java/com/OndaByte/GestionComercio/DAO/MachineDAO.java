/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sl.gruposlots.evajackpot.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sl.gruposlots.evajackpot.models.Machine;

/**
 *
 * @author luciano.gurruchaga
 */
public class MachineDAO implements CRUD<Machine>{
    private String schema = "jackpot";
    private String table = "machines"; 
    private String columnasAll = "id, jackpot_number, serial_number, asset_number, game_id, denominacion, mac_address, time, online, status, created_at, updated_at";
    private String columnasObligatorias = "serial_number, asset_number, game_id, denominacion, mac_address";
            
    private static Logger logger = LogManager.getLogger(MachineDAO.class.getName());
    
    static{
        if(logger.isDebugEnabled()){
            logger.debug("Init logger en MachineDAO");
        }
    }
    
    /**
     * Si no exite la inserto, si existe actualizo macAddress, y el estado a online. 
     * @param m
     * @return 
     */
    @Override
    public int insertar(Machine m) {
       // Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
                // Prepare the SQL statement
        String sql = "INSERT INTO "+this.schema+"."+this.table+" ("+this.columnasObligatorias+") " 
                    + " VALUES(?,?,?,money(?), macaddr(?))"
                    + " ON CONFLICT (serial_number) DO UPDATE SET " 
                    + " mac_address = EXCLUDED.mac_address, "
                    + " denominacion = EXCLUDED.denominacion, "
                    + " online = true;";
        
        int rowsAffected=0;
        // Get a connection from the pool
        Connection connection = null;
        try {
            connection = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(sql);
            statement.setString(1, m.getSerialNumber());
            statement.setInt(2,m.getAssetNumber());
            statement.setString(3, m.getGameId()); 
            statement.setString(4, m.getDenominacion());
            statement.setString(5, m.getMacAddress());
         //   statement.setString(6, m.getTime());


            if(logger.isDebugEnabled()){
                logger.debug("Insert query MachineDAO.insertar() - " + statement.toString());
            }
            // Execute the insert statement
            rowsAffected = statement.executeUpdate();
            
             // statement.close();
            connection.close(); // Este metodo debe retornar ? 
            //  dataSource.close();
            // valor de retorno que todo se ejecuto
          //  return ConnectionConstants.ALL_OK;
        } catch (SQLException ex) {
            logger.error("Error de 'Query' en MachineDAO.insertar() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          //  throw ex;
            //return ConnectionConstants.SQL_ERROR;
        } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.insertar() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.insertar() " + " " + ex2.getMessage()); }
        }         
        return rowsAffected;
    }
    /**
     * Si no exite la inserto, si existe actualizo macAddress, y el estado a online. 
     * @param m
     * @return 
     */
    public int insertarOActualizar(Machine m) {
       // Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
                // Prepare the SQL statement
        String sql = "INSERT INTO "+this.schema+"."+this.table+" ("+this.columnasObligatorias+") " 
                    + " VALUES(?,?,?,money(?), macaddr(?))"
                    + " ON CONFLICT (serial_number) DO UPDATE SET " 
                    + " mac_address = EXCLUDED.mac_address, "
                    + " denominacion = EXCLUDED.denominacion, "
                    + " online = true;";
        
        int rowsAffected=0;
        // Get a connection from the pool
        Connection connection = null;
        try {
            connection = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(sql);
            statement.setString(1, m.getSerialNumber());
            statement.setInt(2,m.getAssetNumber());
            statement.setString(3, m.getGameId()); 
            statement.setString(4, m.getDenominacion());
            statement.setString(5, m.getMacAddress());
         //   statement.setString(6, m.getTime());


            if(logger.isDebugEnabled()){
                logger.debug("Insert query MachineDAO.insertar() - " + statement.toString());
            }
            // Execute the insert statement
            rowsAffected = statement.executeUpdate();
            
             // statement.close();
            connection.close(); // Este metodo debe retornar ? 
            //  dataSource.close();
            // valor de retorno que todo se ejecuto
          //  return ConnectionConstants.ALL_OK;
        } catch (SQLException ex) {
            logger.error("Error de 'Query' en MachineDAO.insertar() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          //  throw ex;
            //return ConnectionConstants.SQL_ERROR;
        } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.insertar() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.insertar() " + " " + ex2.getMessage()); }
        }         
        return rowsAffected;
    }

    @Override
    public Machine selecionarById(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Machine m =null;
        Connection connection = null;
        try {
            String sqlQuery = " SELECT id, jackpot_number, serial_number " +
                         " FROM " + this.schema + "." + this.table +
                         " WHERE id = ? AND status='ACTIVO';";
            connection = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(sqlQuery);
            
            // Set the parameter for the query
            statement.setInt(1, id);
            
            if(logger.isDebugEnabled()){
                logger.debug("Select query MachineDAO.selecionarById() - " + statement.toString());
            }
            resultSet = statement.executeQuery();

            m = rsToMachine(resultSet); //Handdler ResultSet

          } catch (SQLException ex ) {
            logger.error("Error de 'Query' en MachineDAO.selecionarById() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.selecionarById() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.selecionarById() " + " " + ex2.getMessage()); }
        }
         return m; 
    }
    
    public HashMap<String,Object> selecionarConfigById(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        HashMap<String,Object> config = null;
        Connection connection = null;
        try {
            String sqlQuery = " SELECT id, jackpot_number, serial_number " +
                         " FROM " + this.schema + "." + this.table +
                         " ma JOIN jackpot.jackpots ja ON ma.jackpot_number=ja.jackpot_number " +
                         " WHERE id = ? AND status='ACTIVO';";
            connection = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(sqlQuery);
            
            // Set the parameter for the query
            statement.setInt(1, id);
            
            if(logger.isDebugEnabled()){
                logger.debug("Select query MachineDAO.selecionarConfigById() - " + statement.toString());
            }
            resultSet = statement.executeQuery();

            config = rsToMachineConfig(resultSet); //Handdler ResultSet

          } catch (SQLException ex ) {
            logger.error("Error de 'Query' en MachineDAO.selecionarConfigById() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.selecionarConfigById() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.selecionarConfigById() " + " " + ex2.getMessage()); }
        }
         return config; 
    }
    
    /**
     * Adicionamos tabla jackpot, para devolver la macAdress del Jackpot al Modulo. Así se suscribe al topico de lastWill de jackpot
     * @param serialNumber
     * @return 
     */
    public HashMap<String,Object> getConfiguracionBySerialNumber(String serialNumber) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        HashMap<String,Object> config = null;
        Connection connection = null;
        try {
            String sqlQuery = " SELECT id, jackpot_number, serial_number " +
                         " FROM " + this.schema + "." + this.table +
                         " ma JOIN jackpot.jackpots ja ON ma.jackpot_number=ja.jackpot_number " +
                         " WHERE serial_number = ? AND status='ACTIVO';";
            
            connection = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(sqlQuery);
            
            // Set the parameter for the query
            statement.setString(1, serialNumber);
            
            if(logger.isDebugEnabled()){
                logger.debug("Select query MachineDAO.getConfiguracionBySerialNumber() - " + statement.toString());
            }
            resultSet = statement.executeQuery();

            config = rsToMachineConfig(resultSet); //Handdler ResultSet

          } catch (SQLException ex ) {
            logger.error("Error de 'Query' en MachineDAO.getConfiguracionBySerialNumber() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.getConfiguracionBySerialNumber() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) {logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.getconfiguracionBySerialNumber() " + " " + ex2.getMessage()); }
        }
         return config; 
    }

    @Override
    public void eliminarById(int id) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int actualizar(Machine entidad) {
        Connection objConn = null;
        PreparedStatement statement = null;
        int rowsAffected = 0; 
         String strQuery =   " UPDATE " + this.schema + "." +this.table +" SET online = true " +
                             " WHERE serial_number =? ;";
        Connection connection = null;

        try{
            //conexion DB
            objConn = PoolConexionDB.getInstance().getConnection();
            // Create a statement
            statement = connection.prepareStatement(strQuery);
            
            // Set the parameter for the query
            statement.setString(1, entidad.getSerialNumber());
            
            if(logger.isDebugEnabled()){
                logger.debug("Insert query MachineDAO.actualizar() - " + statement.toString());
            }
            
            rowsAffected = statement.executeUpdate();
            
             // statement.close();
            connection.close(); // Este metodo debe retornar ? 

            // valor de retorno que todo se ejecuto
            // return ConnectionConstants.ALL_OK;

        } catch (SQLException ex) {
            logger.error("Error de 'Query' en MachineDAO.actualizar() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
          //  throw ex;
            //return ConnectionConstants.SQL_ERROR;
        } finally {
            //cerramos la sentencia
            try { if (statement != null) statement.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'PreparedStatement' en MachineDAO.actualizar() " + " " + ex2.getMessage()); }
            //cerramos la conexion
            try { if (connection != null) connection.close(); } catch(Exception ex2) { logger.error("Error de Conexion cerrando 'Connection' en MachineDAO.actualizar() " + " " + ex2.getMessage()); }
        }         
        return rowsAffected;
    }
     
    private Machine rsToMachine(ResultSet rs) {
        Machine m = null;
        try {
            if (rs.next()){
                m = new Machine();
                m.setId(rs.getInt("id"));//clave
                m.setJackpotNumber(rs.getInt("jackpot_number"));//clave
                m.setSerialNumber(rs.getString("serial_number"));
                
                if(logger.isDebugEnabled()){
                    logger.debug("SELECT query MachineDAO.rsToMachine() - correcto");
                }
            }else{
                logger.error(MachineDAO.class.getName() + ".Machine.rsToMachine() Error: empty ResultSet" );
            }

        } catch (SQLException ex) {
            logger.warn(MachineDAO.class.getName() + ".Machine.rsToMachine() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
        }finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.warn(MachineDAO.class.getName() + ".Machine.rsToMachine() Error rs.close: " + ex.getErrorCode() + ", " + ex.getMessage());
                }
            }
        }
        return m;
    }
    
    private HashMap<String,Object> rsToMachineConfig(ResultSet rs) {
        HashMap<String,Object> configMap = new HashMap<>();
        Machine m = null;
        try {
            if (rs.next()){ 
                m = new Machine();
                m.setId(rs.getInt("id"));//clave
                m.setJackpotNumber(rs.getInt("jackpot_number"));//clave
                m.setSerialNumber(rs.getString("serial_number"));
                
                configMap.put("machine",m);
                configMap.put("jackpotMacAddress",rs.getString("jackpot_mac_address"));
                
                System.out.println("machine" + configMap.get("machine").toString() );
                System.out.println("jackpotMachine" + configMap.get("jackpotMacAddress") );
                
                
                if(logger.isDebugEnabled()){
                    logger.debug("SELECT query MachineDAO.rsToMachine() - correcto");
                }
            }else{
                logger.error(MachineDAO.class.getName() + ".Machine.rsToMachine() Error: empty ResultSet" );
            }

        } catch (SQLException ex) {
            logger.warn(MachineDAO.class.getName() + ".Machine.rsToMachine() Error: " + ex.getErrorCode() + ", " + ex.getMessage());
        }finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.warn(MachineDAO.class.getName() + ".Machine.rsToMachine() Error rs.close: " + ex.getErrorCode() + ", " + ex.getMessage());
                }
            }
        }
        return configMap;
    }
    
}
