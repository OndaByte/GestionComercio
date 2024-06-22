/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sl.gruposlots.evajackpot.DAO;


/**
 * Java JDBC Connection pool using HikariCP example program
 * 
 * @author pankaj
 */
 
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import sl.gruposlots.config.ConfiguracionGeneral;

public class PoolConexionDB {
    
    private static String DB_URL = "jdbc:postgresql://10.172.100.29:5432/eva_jackpot";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "s04sPsqL";
    private static HikariDataSource hikariDataSource = null;
    private static PoolConexionDB instance;
    private static final boolean poolSwith = false;
    private PoolConexionDB(){
        
            String pathDBCompleto = ConfiguracionGeneral.getCONFIG_PGSQL_DRIVER() + "://"
                                    + ConfiguracionGeneral.getCONFIG_PGSQL_URL() + ":"
                                    + ConfiguracionGeneral.getCONFIG_PGSQL_PORT() + "/"
                                    + ConfiguracionGeneral.getCONFIG_PGSQL_NAME();

            // INIT CONFIGS
            DB_URL = pathDBCompleto;
            DB_USER = ConfiguracionGeneral.getCONFIG_PGSQL_USER();
            DB_PASSWORD = ConfiguracionGeneral.getCONFIG_PGSQL_PASSWORD();
            //END 
        
        
        
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASSWORD);
            config.addDataSourceProperty("minimumIdle", "5");
            //	config.addDataSourceProperty("maximumPoolSize", 15);
            config.setMaximumPoolSize(15); // Límite de 20 conexiones
            //    config.setAutoCommit(false);
            //    config.addDataSourceProperty("cachePrepStmts", "true");
            //    config.addDataSourceProperty("prepStmtCacheSize", "250");
            //   config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            //   config setLoginTimeout(3);
           
           try{
                hikariDataSource = new HikariDataSource(config);//*/
           }catch(Exception e){
                e.printStackTrace(); 
           }
        
        
        
         
    }
    
        public static synchronized PoolConexionDB getInstance()
        {
                if(instance == null)
                {
                   instance = new PoolConexionDB();
                }
                return instance;
        }
     
    public Connection getConnection() throws SQLException {

            return hikariDataSource.getConnection();
    }

    public void close() throws SQLException {

            hikariDataSource.close();
    }

    
}