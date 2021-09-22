package mk.gp.emt.rentals.ports.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mk.gp.emt.rentals.domain.models.Rental;
import mk.gp.emt.rentals.domain.models.RentalId;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.VehicleId;
import mk.gp.emt.rentals.service.RentalService;
import mk.gp.emt.rentals.service.forms.RentalForm;
import mk.gp.emt.sharedkernel.domain.financial.Currency;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaEventsListener {

    private final RentalService rentalService;

    @KafkaListener(topics = "RENTAL_ADDED", groupId = "json")
    public void consumeRentalAddedEvent(String jsonMessage){
        ObjectMapper mapper = new ObjectMapper();
        RentalForm rentalForm = new RentalForm();
        HashMap<String, String> map;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TypeReference<HashMap<String,String>> typeRef
                = new TypeReference<HashMap<String,String>>() {};
        try {
            map = mapper.readValue(jsonMessage, typeRef);
        }
        catch (Exception e) {
            return;
        }
        rentalForm.setVehicleId(VehicleId.of(map.get("vehicleId")));
        rentalForm.setCustomerId(PersonId.of(map.get("customerId")));
        rentalForm.setFromDate(LocalDateTime.parse(map.get("fromDate"), formatter));
        rentalForm.setToDate(LocalDateTime.parse(map.get("toDate"), formatter));
        rentalService.rent(rentalForm);
    }
    @KafkaListener(topics = "RENTAL_APPROVED", groupId = "json")
    public void consumeRentalApprovedEvent(String jsonMessage){
        rentalService.approveRent(RentalId.of(jsonMessage.substring(0, jsonMessage.length()-1)));
    }
    @KafkaListener(topics = "RENTAL_FINISHED", groupId = "json")
    public void consumeRentalFinishedEvent(String jsonMessage){
        rentalService.finishRent(RentalId.of(jsonMessage.substring(0, jsonMessage.length()-1)));
    }
}
