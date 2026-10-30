package com.kafka.consumidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kafka.consumidor.Model.TelemetriaCamion;
import com.kafka.consumidor.Repository.TelemetriaRepository;

import tools.jackson.databind.ObjectMapper;

@Component
public class ConsumidorComponent {
   @Autowired 
   private TelemetriaRepository telemetriaRepository;
   @Autowired 
   private ObjectMapper objectMapper;

      @KafkaListener(topics = "tutorial-particiones", groupId = "prueba-group-1")
    public void listen(
        @Payload String message,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset,
        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {

        // Imprimir información de manera clara y visual
        System.out.println("\n" + "=".repeat(100));
        System.out.printf(" MENSAJE RECIBIDO%n");
        System.out.printf(" 📍 Partición: %d%n", partition);
        System.out.printf(" Offset: %d%n", offset);
        System.out.printf(" 🔑 Key: %s%n", key != null ? key : "(sin key)");
        System.out.printf(" Mensaje: %s%n", message);
        System.out.println("=".repeat(100) + "\n");
    }
    @KafkaListener(topics = "telemtria-camion",groupId = "prueba-group-1")
    public void telemetria(
        @Payload String message,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset,
        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
          try{
            TelemetriaCamion telemetriaCamion = objectMapper.readValue(message,TelemetriaCamion.class);  

            System.out.println("Camion recibido: "+ telemetriaCamion.getCamion());
            System.out.println("Temperatura: "+telemetriaCamion.getTemperatura());
            telemetriaRepository.guardar(telemetriaCamion);
        
          }catch(Exception e){
            System.err.println("fallo el envio del camion error"+e.getMessage());
          }  
    }
}
