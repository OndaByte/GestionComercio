/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.gruposlots.evajackpot.http;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sl.gruposlots.config.ConfiguracionGeneral;
import sl.gruposlots.evajackpot.JackpotMain;
import sl.gruposlots.evajackpot.controllers.jackpot.JackpotController;
import sl.gruposlots.evajackpot.controllers.jackpot.MachineController;
import static spark.Spark.*; 

/**
 *
 * @author Daniel
 */
public class ApiServiceDemonio implements Runnable {

    private static Logger logger = LogManager.getLogger(ApiServiceDemonio.class.getName());

    
    @Override
    public void run() {
        // Configure and start your Spark API
         logger.debug("Init DEMONIO-EVAJackpot - Servicio Running");  

        try {
            port(Integer.parseInt(ConfiguracionGeneral.getCONFIG_HTTP_API_PORT()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("Error : ApiServiceDemonio - setteo de puerto, Problemas con el parseo del puerto desde el archivo generalconfig.properties");
        }
        get("/config/machine", MachineController.configurar);
        get("/config/jackpot", JackpotController.configurar);
        // Add more Spark API configurations as needed

        // Ensure the thread keeps running
        /*while (true) {
            try {
                Thread.sleep(1000); // Adjust the sleep time as needed
                System.out.println("Api running ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
     
}
