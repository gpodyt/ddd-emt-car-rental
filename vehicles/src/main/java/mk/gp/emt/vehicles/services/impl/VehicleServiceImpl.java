package mk.gp.emt.vehicles.services.impl;


import lombok.AllArgsConstructor;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.vehicles.domain.exceptions.VehicleNotFoundException;
import mk.gp.emt.vehicles.domain.models.Vehicle;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import mk.gp.emt.vehicles.domain.repository.VehicleRepository;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.Manufacturer;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.VehicleState;
import mk.gp.emt.vehicles.services.VehicleService;
import mk.gp.emt.vehicles.services.forms.VehicleForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final Validator validator;

    @Override
    public Vehicle findById(VehicleId id) {
        return vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
    }

    @Override
    public Vehicle editVehicle(VehicleForm vehicleForm) {
       Vehicle vehicleToEdit = findById(vehicleForm.getVehicleId());
       vehicleToEdit.modifyVehicle(vehicleForm);
       return vehicleRepository.saveAndFlush(vehicleToEdit);
    }

    @Override
    public void removeVehicle(VehicleId id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle createVehicle(VehicleForm vehicleForm) {
        Objects.requireNonNull(vehicleForm, "Vehicle must not be null!");
        var constraintViolations = validator.validate(vehicleForm);
        if(constraintViolations.size()>0)
            throw new ConstraintViolationException("The vehicle form is not valid", constraintViolations);
        if(vehicleRepository.findAll().stream().anyMatch(v -> v.getRegistrationNumber().getNumber().equals(vehicleForm.getRnNumber())
                && v.getRegistrationNumber().getCity().equals(vehicleForm.getRnCity())
                && v.getRegistrationNumber().getSuffix().equals(vehicleForm.getRnSuffix())))
            throw new IllegalArgumentException("A vehicle with that registration plate already exists!");
        Vehicle vehicle = Vehicle.build(
                PersonId.of(vehicleForm.getOwnerId()),
                Manufacturer.valueOf(vehicleForm.getManufacturer()),
                vehicleForm.getModel(),
                vehicleForm.getKilometers(),
                vehicleForm.getRnCity(),
                vehicleForm.getRnNumber(),
                vehicleForm.getRnSuffix(),
                vehicleForm.getHorsePower(),
                VehicleState.PRIVATE);
        return vehicleRepository.saveAndFlush(vehicle);
    }

    @Override
    public List<Vehicle> findAllFreeVehicles() {
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getVehicleState().equals(VehicleState.FREE))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> findAllVehiclesByUser(String id) {
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getOwnerId().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public void freeVehicle(VehicleId id) {
        Vehicle toChange = findById(id);
        toChange.changeStateToFree();
        vehicleRepository.saveAndFlush(toChange);
    }

    @Override
    public void rentVehicle(VehicleId id) {
        findById(id).changeStateToRented();
    }
}
