package mk.gp.emt.sharedkernel.domain.events.vehicles;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class RentalAddedForV extends DomainEvent {

    public RentalAddedForV(String rentalAsJson) {
        super(Topic.RENTAL_ADDEDV, rentalAsJson);
    }
}
