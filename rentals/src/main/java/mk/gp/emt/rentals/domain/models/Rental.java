package mk.gp.emt.rentals.domain.models;

import lombok.Getter;
import lombok.NonNull;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.Vehicle;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.VehicleId;
import mk.gp.emt.rentals.domain.valueobjects.enums.RentalState;
import mk.gp.emt.rentals.domain.valueobjects.time.Period;
import mk.gp.emt.sharedkernel.domain.base.AbstractEntity;
import mk.gp.emt.sharedkernel.domain.financial.Currency;
import mk.gp.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="rentals")
@Getter
public class Rental extends AbstractEntity<RentalId> {

    private PersonId rentalOwnerId;

    private VehicleId vehicleId;

    private PersonId customerId;

    private Period rentalPeriod;

    @Enumerated(value = EnumType.STRING)
    private RentalState rentalState;

    private Money price;

    private Rental(){
        super(RentalId.randomId(RentalId.class));
    }

    public Rental(@NonNull PersonId rentalOwnerId,
                  @NonNull VehicleId vehicleId,
                  @NonNull PersonId customerId,
                  @NonNull LocalDateTime from,
                  @NonNull LocalDateTime to,
                  double euroPricePerDay){
        super(RentalId.randomId(RentalId.class));
        this.rentalOwnerId = rentalOwnerId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.rentalPeriod = new Period(from, to);
        this.rentalState = RentalState.WAITING_APPROVAL;
        euroPricePerDay = Math.round(euroPricePerDay*100);
        euroPricePerDay = euroPricePerDay/100;
        if(euroPricePerDay<10)
            this.price = new Money(Currency.EUR, 10*rentalPeriod.days());
        else
            this.price = new Money(Currency.EUR, euroPricePerDay*rentalPeriod.days());
    }

    public void approveRent(){
        this.rentalState = RentalState.ONGOING;
    }

    public void finishRent(){
        this.rentalState = RentalState.FINISHED;
    }
}
