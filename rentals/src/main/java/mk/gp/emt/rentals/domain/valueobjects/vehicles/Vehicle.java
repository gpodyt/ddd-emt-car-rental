package mk.gp.emt.rentals.domain.valueobjects.vehicles;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.vehicleinfo.Manufacturer;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.vehicleinfo.VehicleState;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.mileage.Mileage;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.vehicleinfo.RegistrationNumber;

@Getter
public class Vehicle implements ValueObject {

    private final VehicleId id;
    private final PersonId ownerId;
    private final Manufacturer manufacturer;
    private final String model;
    private final Mileage mileage;
    private final RegistrationNumber registrationNumber;
    private final int horsePower;
    private final VehicleState vehicleState;

    private Vehicle(){
        this.id = VehicleId.randomId(VehicleId.class);
        this.ownerId = PersonId.randomId(PersonId.class);
        this.manufacturer = Manufacturer.N_A;
        this.model = "";
        this.mileage = Mileage.valueOf(0);
        this.registrationNumber = RegistrationNumber.valueOf("", "", "");
        this.horsePower = 0;
        this.vehicleState = VehicleState.FREE;
    }

    public Vehicle(VehicleId id, PersonId ownerId, Manufacturer manufacturer, String model, Mileage mileage, RegistrationNumber registrationNumber, int horsePower, VehicleState vehicleState) {
        this.id = id;
        this.ownerId = ownerId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.mileage = mileage;
        this.registrationNumber = registrationNumber;
        this.horsePower = horsePower;
        this.vehicleState = vehicleState;
    }
}
