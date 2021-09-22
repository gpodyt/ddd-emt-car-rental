package mk.gp.emt.vehicles.ports.controllers;

import lombok.AllArgsConstructor;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.vehicles.domain.models.Vehicle;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.Manufacturer;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.VehicleState;
import mk.gp.emt.vehicles.services.VehicleService;
import mk.gp.emt.vehicles.services.forms.VehicleForm;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = {"http://localhost:9092", "http://localhost:9091"})
@RequestMapping("/api")
@AllArgsConstructor
public class RestController {
    private final VehicleService vehicleService;

    @GetMapping("/admin/allvehicles")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.findAll();
    }

    @GetMapping("/publicvehicles")
    public List<Vehicle> getPublicVehicles(){
        return vehicleService.findAllFreeVehicles();
    }

    @GetMapping("/myvehicles/{id}")
    public List<Vehicle> getUserVehicles(@PathVariable String id){
        return vehicleService.findAllVehiclesByUser(id);
    }

    @GetMapping("/manufacturers")
    public List<String> getManufacturers(){
        return Arrays.stream(Manufacturer.values()).map(
                Enum::name
        ).collect(Collectors.toList());
    }

    @GetMapping("/vehicle/{id}")
    public Vehicle getVehicleById(@PathVariable String id){
        return vehicleService.findById(VehicleId.of(id));
    }

    @PostMapping("/freevehicle")
    public void freeVehicleById(@RequestBody String jsonId){
        vehicleService.freeVehicle(VehicleId.of(jsonId));
    }
}
