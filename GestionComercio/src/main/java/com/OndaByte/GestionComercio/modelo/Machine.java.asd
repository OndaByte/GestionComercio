/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sl.gruposlots.evajackpot.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.DecimalFormat;
import org.apache.logging.log4j.LogManager;
//import java.sql.Timestamp;
import org.apache.logging.log4j.Logger; 
import org.json.JSONObject;

/**
 *
 * @author luciano.gurruchaga
 */
public class Machine {
    private int id;
    private int jackpotNumber;
    private String serialNumber;
    private int assetNumber;
    private String gameId;
    private float denominacion;
    private String macAddress;
    private String time;
    private boolean online;
    
    private String status; // enum
    private String created_at; // timestamp
    private String updated_at; // timestamp
    
    
    private static Logger logger = LogManager.getLogger(Machine.class.getName());
    
    static{
        if(logger.isDebugEnabled()){
            logger.debug("Init logger en Machine");
        }
    }  
    
    public Machine(){}
    
    @JsonCreator
    public Machine(@JsonProperty("serialNumber")String serialNumber,@JsonProperty("assetNumber")int assetNumber,@JsonProperty("gameId")String gameId,@JsonProperty("denominacion")float denominacion) 
    {
        this.serialNumber = serialNumber;
        this.assetNumber = assetNumber;
        this.gameId = gameId;
        this.denominacion = denominacion;
    }
    
    @JsonCreator
    public Machine(@JsonProperty("serialNumber")String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJackpotNumber() {
        return jackpotNumber;
    }

    public void setJackpotNumber(int jackpotNumber) {
        this.jackpotNumber = jackpotNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(int assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDenominacion() {
        return formatFloatWithCommas(denominacion);
    }
    
    private static String formatFloatWithCommas(float value) {
        // Create a DecimalFormat instance with a pattern that uses commas as the decimal separator
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        // Format the float value as a string
        return decimalFormat.format(value);
    }
    public void setDenominacion(float denominacion) {
        this.denominacion = denominacion;
    }


    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
 
     public JSONObject toJsonConfig(){
        JSONObject jsonObj= new JSONObject();
        jsonObj.put("minBet", 0); // var_1.n.n
        jsonObj.put("machineId", this.id);
        jsonObj.put("jackpotId", this.jackpotNumber);
        return jsonObj;
    }

     
}
