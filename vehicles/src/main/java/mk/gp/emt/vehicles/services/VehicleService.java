package mk.gp.emt.vehicles.services;

import mk.gp.emt.vehicles.domain.models.Vehicle;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import mk.gp.emt.vehicles.services.forms.VehicleForm;

import java.util.List;

public interface VehicleService {
    Vehicle findById(VehicleId id);
    Vehicle createVehicle(VehicleForm vehicleForm);
    Vehicle editVehicle(VehicleForm vehicleForm);
    void removeVehicle(VehicleId id);
    void freeVehicle(VehicleId id);
    void rentVehicle(VehicleId id);
    List<Vehicle> findAll();
    List<Vehicle> findAllFreeVehicles();
    List<Vehicle> findAllVehiclesByUser(String id);
}
