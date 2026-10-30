package com.kafka.productor.Model;

public class TelemetriaCamion {
    private String id;
    private String camionId;
    private long timestamp;
    private Double temperatura;
    private Double humedad;
    private String estado;

    public void setId(String id){
        this.id= id;
    }
    public String getId(){
        return this.id;
    }
    public String getCamion(){
        return camionId;
    }
    public void setCamionId(String camionId){
        this.camionId= camionId;
    }
    public Long getTimesTamp(){
        return this.timestamp;
    }
    public void setTimesTamp(Long timesTamp){
        this.timestamp = timesTamp;
    }
    public Double getTemperatura(){
        return this.temperatura;
    }
    public void setTemperatura(Double temperatura){
        this.temperatura= temperatura;
    }
    public Double getHumedad(){
        return this.humedad;
    }
    public void setHumedad(Double humedad){
        this.humedad= humedad;
    }
    public String getEstado(){
        return this.estado;
    }
    public void setEstado(String estado){
        this.estado=estado;
    }

}
