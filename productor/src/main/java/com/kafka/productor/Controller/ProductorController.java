package com.kafka.productor.Controller;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.productor.Model.TelemetriaCamion;

import tools.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/kafka")
public class ProductorController {
    private final KafkaTemplate <String, String> kafkaTemplate;
    @Autowired 
    private ObjectMapper objectMapper;
    @Autowired
    public ProductorController(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate= kafkaTemplate; 
    }

    @GetMapping("/send")
    public String sendMessage(
        @RequestParam String message, 
        @RequestParam (required=false) String key){
        String topicName= "tutorial-particiones";

        if(key!= null && !key.isEmpty()){
            kafkaTemplate.send(topicName, key, message);
            return "Mensaje enviado el topic" + topicName
            + "con Key:"+ key
            + " | Mensaje:" + message;        
        }else{
            kafkaTemplate.send(topicName, message);
             return "Mensaje enviado el topic" + topicName
            + " | Mensaje:" + message;
        }
    }

    @PostMapping("/telemetria-camion")
    public String postMethodName() {
        
        String nombreTopico = "telemtria-camion";
        String[] camiones={"CAMION-01", "CAMION-02", "CAMION-03"};
        String[] estados={"EN_RUTA", "DETENIDO", "REFRIGERANDO", "ALERTA_TEMPERATURA"};
        Random random = new Random();
        
        try {
            for(int i = 1; i<50;i++){
                String camionId= camiones[random.nextInt(camiones.length)];
                Double temperatura= -25.0+ (random.nextDouble()*15.0);
                Double humedad= -50.0+(random.nextDouble()*30.0); 
                String estado= estados[random.nextInt(estados.length)];
                long timestamp = System.currentTimeMillis()+(i*1000L);
                TelemetriaCamion telemetriaCamion= new TelemetriaCamion();
                String id= UUID.randomUUID().toString().substring(0,8);

                telemetriaCamion.setId(id);
                telemetriaCamion.setCamionId(camionId);
                telemetriaCamion.setEstado(estado);
                telemetriaCamion.setHumedad(Math.round(humedad*100.0)/100.0);
                telemetriaCamion.setTemperatura(Math.round(temperatura*100.0)/100.0);
                telemetriaCamion.setTimesTamp(timestamp);
                
                String jsonMessage =objectMapper.writeValueAsString(telemetriaCamion);  
                kafkaTemplate.send(nombreTopico, camionId, jsonMessage);
            }
            return "Mensajes enviados correctamente";
        } catch (Exception e) {
            return "Error al generar los registros ";
        }
    }
    
    
}
