package mk.gp.emt.vehicles.domain.models;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.base.AbstractEntity;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.vehicles.domain.valueobjects.mileage.Mileage;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.Manufacturer;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.RegistrationNumber;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.VehicleState;
import mk.gp.emt.vehicles.services.forms.VehicleForm;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")
@Getter
public class Vehicle extends AbstractEntity<VehicleId> {
    private PersonId ownerId;
    @Enumerated(value = EnumType.STRING)
    private Manufacturer manufacturer;
    private String model;
    private Mileage mileage;
    private RegistrationNumber registrationNumber;
    private int horsePower;
    private VehicleState vehicleState;

    private Vehicle(){
        super(VehicleId.randomId(VehicleId.class));
    }

    public static Vehicle build(PersonId ownerId,
                                Manufacturer manufacturer,
                                String model,
                                int kilometers,
                                String rnCity,
                                String rnNumber,
                                String rnSuffix,
                                int horsePower,
                                VehicleState state){
        Vehicle vehicle = new Vehicle();
        vehicle.ownerId = ownerId;
        vehicle.manufacturer = manufacturer;
        vehicle.model = model;
        vehicle.mileage = new Mileage(kilometers);
        vehicle.registrationNumber = new RegistrationNumber(rnCity, rnNumber, rnSuffix);
        vehicle.horsePower = horsePower;
        vehicle.vehicleState = state;
        return vehicle;
    }

    public void modifyVehicle(VehicleForm vehicleForm){
        this.manufacturer = Manufacturer.valueOf(vehicleForm.getManufacturer());
        this.model = vehicleForm.getModel();
        this.mileage = Mileage.valueOf(vehicleForm.getKilometers());
        this.registrationNumber = new RegistrationNumber(vehicleForm.getRnCity(), vehicleForm.getRnNumber(), vehicleForm.getRnSuffix());
        this.horsePower = vehicleForm.getHorsePower();
        this.vehicleState = vehicleForm.getVehicleState();
    }

    public void changeStateToFree(){
        this.vehicleState = VehicleState.FREE;
    }

    public void changeStateToRented(){
        this.vehicleState = VehicleState.ON_RENT;
    }
}
