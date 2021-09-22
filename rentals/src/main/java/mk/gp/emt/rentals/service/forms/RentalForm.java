package mk.gp.emt.rentals.service.forms;

import lombok.Data;
import mk.gp.emt.rentals.domain.models.RentalId;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.Vehicle;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.VehicleId;
import mk.gp.emt.sharedkernel.domain.financial.Currency;
import mk.gp.emt.sharedkernel.domain.users.PersonId;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RentalForm {

    private PersonId rentalOwnerId;

    private RentalId rentalId;

    private Currency currency;

    @Valid
    private VehicleId vehicleId;

    @NotNull
    private PersonId customerId;

    @NotNull LocalDateTime fromDate;
    @NotNull LocalDateTime toDate;
}
