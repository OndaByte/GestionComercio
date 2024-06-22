package sl.gruposlots.evajackpot.controllers.jackpot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
 import sl.gruposlots.evajackpot.DAO.MachineDAO;
import sl.gruposlots.evajackpot.JackpotMain;
import sl.gruposlots.evajackpot.models.Machine;
import sl.gruposlots.evajackpot.mqtt.BrokerAsyncService;
import spark.Request;
import spark.Response;
import spark.Route;
import sl.gruposlots.evajackpot.controllers.GestorEvento;
import sl.gruposlots.evajackpot.controllers.GestorSituacionesDeRed;
import sl.gruposlots.evajackpot.models.Jackpot;

/**
 *
 * @author luciano.gurruchaga
 */
public class MachineController implements GestorEvento, GestorSituacionesDeRed {
    
    
    private static Logger logger = LogManager.getLogger(MachineController.class.getName());
    
    static{
        if(logger.isDebugEnabled()){
            logger.debug("Init logger en MachinesController");
        }
    }
    /**
     * Manipulacion de eventos del módulo..
     * @param msgJson
     * @return 
     */
    @Override
    public boolean manejarEvento(String msgJson) {
        MachineDAO mDao = new MachineDAO();
        Machine machine = lercturaJSON(msgJson); 
        String jsonStr="";// mapperObj.writeValueAsString(msg);
        int inserts = 0;
        if(logger.isDebugEnabled()){
            logger.debug("MachinesController : Manejando mensaje arribado broker. ");
        } 
        
        try{
            HashMap<String,Object> configuracionMap = mDao.getConfiguracionBySerialNumber(machine.getSerialNumber());
            Machine machineAux = (Machine) configuracionMap.get("machine"); // aux = configuracion vieja
            // Logica de existencia 
            if(machineAux != null){
                inserts = insertarOActualizarRegistro(machineAux); // Ya existe, actualiza los campos y activa online
                if(inserts>0){ // siempre deberia ser mayor
                    String jackpotMacAddress = (String)configuracionMap.get("jackpotMacAddress"); 
                    String coreMac = JackpotMain.getMacAddress();
                    publicarConfigToBroker(machineAux,jackpotMacAddress,coreMac);
                }
            }else{
                 inserts = insertarOActualizarRegistro(machineAux); // insersion corriente en 'PENDIENTE'
            }
            
           
        }catch(NullPointerException ex){ 
            logger.error("Error de logica en MachineController.insertarRegistro() " + " " + ex.getMessage());       
        } 
  
  
  
         
        
        
        return jsonStr != "" && inserts>0; 
    }
    
    /** 
     * FORMATO : CREAMOS EL OBJETO CON EL PAYLOAD. Y Luego setteamos los campos manualmente.
     * {
     *   "timestamp":"213213",
     *   "macAddress":"BC:DD:C2:30:21:F0",
     *   "payload":{
     *      "serialNumber":"asdasd",
     *      "assetNumber":123213,
     *      "denominacion":"0.01",
     *      "gameId":"WM"
     *   }
     * }	
     * @return 
     */
    public Machine lercturaJSON(String msgJson){
        ObjectMapper mapperObj = new ObjectMapper();//Object o = mapper.readValue(json, Object.class); EJEMPLO.
        Machine machine = null;
        try { 
            System.out.println(" MENSAJE JSONSOANDOASNDO A" + msgJson);
            Map<String, Object> map = mapperObj.readValue(msgJson, new TypeReference<Map<String,Object>>(){});
            String timestamp = (String) map.get("timestamp");
            String macAddress = (String) map.get("macAddress"); 
            String payload = mapperObj.writeValueAsString(map.get("payload")); // Obtengo payLoad
            machine = mapperObj.readValue(payload, Machine.class);  //Creo contador
            machine.setTime(timestamp);
            machine.setMacAddress(macAddress);
            
        } catch (JsonProcessingException ex) { 
            logger.error("Error en lectura del 'JSON' en MachineController.parseoJSON() " + " " + ex.getMessage());       
        }
        return machine;
    }
    /**
     * Get configuracion por serial number, la cosa sería así, 
     * 
     * 
     * 
     * 
     * if  Exita y este offline  (setteado previamente por muerte en ultima voluntad)  
     *          actualizo registro UPDATE EN DB 
     *          configuro Modulo. 
     *      
     * 
     * Si no es así, es por que no existe o esta activo, si esta activo que se muera ... 
     * 
     * si no existe lo damos de alta para futura configuracion ... 
     * 
     * @param m 
     */
    public int insertarOActualizarRegistro(Machine m){
            MachineDAO mDao = new MachineDAO();
            return mDao.insertar(m); // Ya existe, actualiza los campos y activa online
    } 
    /**
     * 
     * @param m configuración de la maquina, 
     * @param jackpotMacAddress macAddress de el jackpot asociado
     * @param coreMacAddress macAddress de el core asociado
     */
    private static void publicarConfigToBroker(Machine m, String jackpotMacAddress,String coreMacAddress){
        BrokerAsyncService servicioMqtt = BrokerAsyncService.getInstance();
        JSONObject jo =  m.toJsonConfig();
        jo.put("macAddressJP", jackpotMacAddress);
        jo.put("macAddressJava", coreMacAddress);
        servicioMqtt.publicar("machine/config/jackpot/"+m.getSerialNumber(),m.toJsonConfig().toString());
    }
    
    public static Route configurar = (Request request, Response response) -> {
        MachineDAO mDAO = new MachineDAO();
        int id = 0;
        if(logger.isDebugEnabled()){
            logger.debug("MachineController : Manejando mensaje arribado http.");
        }
        try {
            id = Integer.parseInt(request.queryParams("id"));
        }catch (NumberFormatException ignored){}
        
        HashMap<String,Object> configuracionMap = null;
        configuracionMap = mDAO.selecionarConfigById(id); 
         
        if(configuracionMap!=null){
            Machine m = (Machine) configuracionMap.get("machine"); 
            String jackpotMacAddress = (String)configuracionMap.get("jackpotMacAddress"); 
            String coreMac = JackpotMain.getMacAddress();
            publicarConfigToBroker(m,jackpotMacAddress,coreMac); 
            
            response.type("application/json");
            response.status(200);
            response.body(m.toJsonConfig().toString());
        }else{
            logger.warn("WARN MachineController.configurar - empty ");
            response.type("application/json");
            response.status(400);
            response.body("{\"error\":\"No pudimos encontrar ningun registro asociado con el id. "+ id +", no se ha publicado nada al broker.\" }");
        }
        return  response.body();
    };

    @Override
    public boolean manejarUltimaVolundad(String messageJson) {
        if(logger.isDebugEnabled())
            logger.debug("Machinecontroller manejarUltimaVolundad.parseoJSON() ");       

        ObjectMapper mapperObj = new ObjectMapper();//Object o = mapper.readValue(json, Object.class); EJEMPLO.
        MachineDAO mDAO = new MachineDAO();
        Machine m = null;            
        try{
            m = mapperObj.readValue(messageJson, Machine.class);
        } catch (JsonProcessingException ex) { 
            logger.error("Error en lectura del 'JSON' en manejarUltimaVolundad.parseoJSON() " + " " + ex.getMessage());       
        }
        return mDAO.actualizar(m) > 0;
    }

    @Override
    public boolean manejarActivo(String messageJson) {
        if(logger.isDebugEnabled())
            logger.debug("MachineController manejarActivo - SE IGNORA");       
        // Se activan en el canal info
        return true;

    }

    
}
