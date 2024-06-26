/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sl.gruposlots.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author luciano.gurruchaga
 */
public class ConfiguracionGeneral {

    private final static Properties properties = new Properties();

    /**
     *  configuration file name
     */
    private final static String CONFIG_FILE_NAME = "config"+File.separator+"generalconfig.properties";
    
    private final static String CONST_CONFIG_BROKER_NAME = "MQTT_BROKER_NAME";
    private final static String CONST_CONFIG_BROKER_PORT = "MQTT_BROKER_PORT";
    private final static String CONST_CONFIG_BROKER_USER = "MQTT_BROKER_USER";
    private final static String CONST_CONFIG_BROKER_PASS = "MQTT_BROKER_PASS";
    
    private final static String CONST_CONFIG_PGSQL_DRIVER = "PGSQL_DRIVER";
    private final static String CONST_CONFIG_PGSQL_URL = "PGSQL_URL";
    private final static String CONST_CONFIG_PGSQL_PORT = "PGSQL_PORT";
    private final static String CONST_CONFIG_PGSQL_NAME = "PGSQL_NAME";
    private final static String CONST_CONFIG_PGSQL_USER = "PGSQL_USER";
    private final static String CONST_CONFIG_PGSQL_PASSWORD = "PGSQL_PASSWORD";
    
    private final static String CONST_CONFIG_HTTP_API_PORT = "HTTP_API_PORT"; 
    /**
     *  configuracion MQTT
     */ 
    
    private static String CONFIG_BROKER_NAME = "tcp://10.119.10.90:";
    private static String CONFIG_BROKER_PORT = "1884";
    private static String CONFIG_BROKER_USER = "usuario";
    private static String CONFIG_BROKER_PASS = "qwerty";

    private static String CONFIG_PGSQL_DRIVER = "jdbc:postgresql";
    private static String CONFIG_PGSQL_URL = "10.172.100.29";
    private static String CONFIG_PGSQL_PORT = "5432";
    private static String CONFIG_PGSQL_NAME = "eva_jackpot";
    private static String CONFIG_PGSQL_USER = "postgres";
    private static String CONFIG_PGSQL_PASSWORD = "s04sPsqL";
    
    private static String CONFIG_HTTP_API_PORT = "4567"; 

    private static final int GLOBAL_MAX_COUNT = 1000;
    private static final int GLOBAL_MAX_DAYS = 365;
 
    private static Logger logger = LogManager.getLogger(ConfiguracionGeneral.class.getName());

    static{
        if(logger.isDebugEnabled()){
            logger.debug("Init logger in ConfiguracionGeneral");
        }else{
            logger.error("Se rompio");
        }
        
    }

    private static boolean configInit = false;

    public static boolean isConfigInit() {
        return configInit;
    }

    public static void setConfigInit(boolean configInit) {
        ConfiguracionGeneral.configInit = configInit;
    }

    public static String getCONFIG_BROKER_NAME() {
        return CONFIG_BROKER_NAME;
    }

    public static void setCONFIG_BROKER_NAME(String CONFIG_BROKER_NAME) {
        ConfiguracionGeneral.CONFIG_BROKER_NAME = CONFIG_BROKER_NAME;
    }

    public static String getCONFIG_BROKER_PORT() {
        return CONFIG_BROKER_PORT;
    }

    public static void setCONFIG_BROKER_PORT(String CONFIG_BROKER_PORT) {
        ConfiguracionGeneral.CONFIG_BROKER_PORT = CONFIG_BROKER_PORT;
    }

    public static String getCONFIG_BROKER_USER() {
        return CONFIG_BROKER_USER;
    }

    public static void setCONFIG_BROKER_USER(String CONFIG_BROKER_USER) {
        ConfiguracionGeneral.CONFIG_BROKER_USER = CONFIG_BROKER_USER;
    }

    public static String getCONFIG_BROKER_PASS() {
        return CONFIG_BROKER_PASS;
    }

    public static void setCONFIG_BROKER_PASS(String CONFIG_BROKER_PASS) {
        ConfiguracionGeneral.CONFIG_BROKER_PASS = CONFIG_BROKER_PASS;
    }

    public static String getCONFIG_PGSQL_DRIVER() {
        return CONFIG_PGSQL_DRIVER;
    }

    public static void setCONFIG_PGSQL_DRIVER(String CONFIG_PGSQL_DRIVER) {
        ConfiguracionGeneral.CONFIG_PGSQL_DRIVER = CONFIG_PGSQL_DRIVER;
    }

    public static String getCONFIG_PGSQL_URL() {
        return CONFIG_PGSQL_URL;
    }

    public static void setCONFIG_PGSQL_URL(String CONFIG_PGSQL_URL) {
        ConfiguracionGeneral.CONFIG_PGSQL_URL = CONFIG_PGSQL_URL;
    }

    public static String getCONFIG_PGSQL_PORT() {
        return CONFIG_PGSQL_PORT;
    }

    public static void setCONFIG_PGSQL_PORT(String CONFIG_PGSQL_PORT) {
        ConfiguracionGeneral.CONFIG_PGSQL_PORT = CONFIG_PGSQL_PORT;
    }

    public static String getCONFIG_PGSQL_NAME() {
        return CONFIG_PGSQL_NAME;
    }

    public static void setCONFIG_PGSQL_NAME(String CONFIG_PGSQL_NAME) {
        ConfiguracionGeneral.CONFIG_PGSQL_NAME = CONFIG_PGSQL_NAME;
    }

    public static String getCONFIG_PGSQL_USER() {
        return CONFIG_PGSQL_USER;
    }

    public static void setCONFIG_PGSQL_USER(String CONFIG_PGSQL_USER) {
        ConfiguracionGeneral.CONFIG_PGSQL_USER = CONFIG_PGSQL_USER;
    }

    public static String getCONFIG_PGSQL_PASSWORD() {
        return CONFIG_PGSQL_PASSWORD;
    }

    public static void setCONFIG_PGSQL_PASSWORD(String CONFIG_PGSQL_PASSWORD) {
        ConfiguracionGeneral.CONFIG_PGSQL_PASSWORD = CONFIG_PGSQL_PASSWORD;
    }

    public static String getCONFIG_HTTP_API_PORT() {
        return CONFIG_HTTP_API_PORT;
    }

    public static void setCONFIG_HTTP_API_PORT(String CONFIG_HTTP_API_PORT) {
        ConfiguracionGeneral.CONFIG_HTTP_API_PORT = CONFIG_HTTP_API_PORT;
    }

    
    
    public static void init() {
        if (!isConfigInit()){
            //String strPath = ConfiguracionGeneral.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            //strPath = strPath.replace("build/classes/", "");

            String strPath = ConfiguracionGeneral.class.getProtectionDomain().getCodeSource().getLocation().getFile();
       //     String projectDir = new File(strPath).getParent();//strPath = strPath.replace("build/classes/", "").replace(SlotsOnline.JAR_FILE, "") + CONFIG_FILE_NAME;

            String strAbsolutePath = strPath.replace(Constantes.JAR_FILE, "classes"+File.separator) + CONFIG_FILE_NAME;
           // String strAbsolutePath = strPath + CONFIG_FILE_NAME;
             if(logger.isInfoEnabled()){
                logger.info("ConfiguracionGeneral path: " + strPath);
                logger.info("ConfiguracionGeneral config file: " + CONFIG_FILE_NAME);
                logger.info("ConfiguracionGeneral strAbsolutePath: " + strAbsolutePath);
            }

            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(strAbsolutePath);
                properties.load(inStream);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_NAME).equals(""))
                    ConfiguracionGeneral.CONFIG_BROKER_NAME = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_NAME);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_PORT).equals(""))
                    ConfiguracionGeneral.CONFIG_BROKER_PORT = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_PORT);
               
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_USER).equals(""))
                    ConfiguracionGeneral.CONFIG_BROKER_USER = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_USER);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_PASS).equals(""))
                    ConfiguracionGeneral.CONFIG_BROKER_PASS = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_BROKER_PASS);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_DRIVER).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_DRIVER = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_DRIVER);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_URL).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_URL = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_URL);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_PORT).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_PORT = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_PORT);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_NAME).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_NAME = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_NAME);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_USER).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_USER = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_USER);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_PASSWORD).equals(""))
                    ConfiguracionGeneral.CONFIG_PGSQL_PASSWORD = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_PGSQL_PASSWORD);
                
                if (!properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_HTTP_API_PORT).equals(""))
                    ConfiguracionGeneral.CONFIG_HTTP_API_PORT = properties.getProperty(ConfiguracionGeneral.CONST_CONFIG_HTTP_API_PORT);
                
                /*
                if (!properties.getProperty(ConfiguracionGeneral.CONST_MSD_GENERIC_COUNT).equals("")){
                    ConfiguracionGeneral.MSD_GENERIC_COUNT = Integer.parseInt(properties.getProperty(ConfiguracionGeneral.CONST_MSD_GENERIC_COUNT));
                    if (ConfiguracionGeneral.MSD_GENERIC_COUNT > ConfiguracionGeneral.MSD_GLOBAL_MAX_COUNT)
                        ConfiguracionGeneral.MSD_GENERIC_COUNT = ConfiguracionGeneral.MSD_GLOBAL_MAX_COUNT;
                }else{
                    ConfiguracionGeneral.MSD_GENERIC_COUNT = ConfiguracionGeneral.MSD_GLOBAL_MAX_COUNT;
                }
            */
            } catch (FileNotFoundException ex) {
                logger.fatal(ConfiguracionGeneral.class.getName() + " Error al inicializar configuraciones generales " + ex.getMessage());
            } catch (IOException ex) {
                logger.fatal(ConfiguracionGeneral.class.getName() + " Error al inicializar configuraciones generales " + ex.getMessage());
                //se debe llamar a este metodo para terminar la aplicacion
                System.exit(0);
            } catch (NumberFormatException ex) {
                logger.fatal(ConfiguracionGeneral.class.getName() + " Error al inicializar configuraciones generales " + ex.getMessage());
                //se debe llamar a este metodo para terminar la aplicacion
                System.exit(0);
            } catch (Exception ex) {
                logger.fatal(ConfiguracionGeneral.class.getName() + " Error al inicializar configuraciones generales " + ex.getMessage());
                //se debe llamar a este metodo para terminar la aplicacion
                System.exit(0);
            }finally{
                setConfigInit(true);

                try {
                    if (inStream != null)
                        inStream.close();
                } catch (IOException e) {
                    logger.fatal(ConfiguracionGeneral.class.getName() + " Error al cerrar archivo de configuracion " + e.getMessage());
                }
            }
        }
    }
    /*
    public static void main(String[] args) {
        ConfiguracionGeneral.init();
        System.out.println("SNMP_TRAP_LISTEN_ADDRESS: " + ConfiguracionGeneral.getSnmpTrapListenAddress());
        System.out.println("SNMP_READ_COMMUNITY: " + ConfiguracionGeneral.getSnmpReadCommunity());
        System.out.println("SNMP_WRITE_COMMUNITY: " + ConfiguracionGeneral.getSnmpWriteCommunity());
        System.out.println("SNMP_TRAP_COMMUNITY: " + ConfiguracionGeneral.getSnmpTrapCommunity());
        System.out.println("SNMP_MIB_FILE: " + ConfiguracionGeneral.getSnmpMibFile());
        System.out.println("SNMP_MIB_INTERNAL_NAME: " + ConfiguracionGeneral.getSnmpMibInternalName());
        System.out.println("SNMP_UDP_TRANSPORT_PROTOCOL: " + ConfiguracionGeneral.getSnmpUdpTransportProtocol());
        System.out.println("SNMP_TCP_TRANSPORT_PROTOCOL: " + ConfiguracionGeneral.getSnmpTcpTransportProtocol());
        System.out.println("SNMP_TRAP_LISTEN_PORT: " + ConfiguracionGeneral.getSnmpTrapListenPort());
        System.out.println("SNMP_AGENT_PORT: " + ConfiguracionGeneral.getSnmpAgentPort());
        System.out.println("SNMP_DEFAULT_TIMEOUT: " + ConfiguracionGeneral.getSnmpDefaultTimeout());
        System.out.println("SNMP_DEFAULT_RETRY: " + ConfiguracionGeneral.getSnmpDefaultRetry());
    }
     * 
     */

}
