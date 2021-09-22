package mk.gp.emt.vehicles.services.forms;

import lombok.Data;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.Manufacturer;
import mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo.VehicleState;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class VehicleForm {

    private VehicleId vehicleId;
    @NotNull
    private String ownerId;
    @Max(500000)
    private int kilometers;
    private String manufacturer;
    private String model;
    @Size(min=2, max=2)
    private String rnCity;
    @Size(min=3, max=4)
    private String rnNumber;
    @Size(min=2, max=2)
    private String rnSuffix;
    @Min(50)
    @Max(2000)
    private int horsePower;
    private VehicleState vehicleState;
}
