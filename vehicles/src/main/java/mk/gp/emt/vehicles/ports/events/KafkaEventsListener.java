package mk.gp.emt.vehicles.ports.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mk.gp.emt.vehicles.domain.models.Vehicle;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import mk.gp.emt.vehicles.services.VehicleService;
import mk.gp.emt.vehicles.services.forms.VehicleForm;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaEventsListener {

    private final VehicleService vehicleService;

    @KafkaListener(topics = "VEHICLE_ADDED", groupId = "json")
    public void consumeVehicleAddedEvent(String jsonMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        VehicleForm vehicleForm;
        try{
            vehicleForm = objectMapper.readValue(jsonMessage, VehicleForm.class);
        }
        catch(Exception e){
            return;
        }
        vehicleService.createVehicle(vehicleForm);
    }

    @KafkaListener(topics = "RENTAL_ADDEDV", groupId = "json")
    public void consumeRentalAddedEvent(String jsonMessage){
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map;
        TypeReference<HashMap<String,String>> typeRef
                = new TypeReference<HashMap<String,String>>() {};
        try {
            map = mapper.readValue(jsonMessage, typeRef);
        }
        catch (Exception e) {
            return;
        }
        vehicleService.rentVehicle(VehicleId.of(map.get("vehicleId")));
    }

    @KafkaListener(topics = "VEHICLE_MODIFIED", groupId = "json")
    public void consumeVehicleModifiedEvent(String jsonMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        VehicleForm vehicleForm;
        try{
            vehicleForm = objectMapper.readValue(jsonMessage, VehicleForm.class);
        }
        catch(Exception e){
            return;
        }
        vehicleService.editVehicle(vehicleForm);
    }

    @KafkaListener(topics = "VEHICLE_RENTED", groupId = "json")
    public void consumeVehicleRentedEvent(String jsonMessage){
        vehicleService.rentVehicle(VehicleId.of(jsonMessage.substring(0, jsonMessage.length()-1)));
    }

    @KafkaListener(topics = "VEHICLE_REMOVED", groupId = "json")
    public void consumeVehicleRemovedEvent(String jsonMessage){
        vehicleService.removeVehicle(VehicleId.of(jsonMessage.substring(0, jsonMessage.length()-1)));
    }

}
