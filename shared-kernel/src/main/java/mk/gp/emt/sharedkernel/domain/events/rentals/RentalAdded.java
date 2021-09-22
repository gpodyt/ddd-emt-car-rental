package mk.gp.emt.sharedkernel.domain.events.rentals;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class RentalAdded extends DomainEvent {

    public RentalAdded(String rentalAsJson) {
        super(Topic.RENTAL_ADDED, rentalAsJson);
    }
}
