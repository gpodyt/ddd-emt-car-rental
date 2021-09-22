package mk.gp.emt.sharedkernel.domain.events.rentals;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class RentalFinished extends DomainEvent {

    public RentalFinished(String rentalId) {
        super(Topic.RENTAL_FINISHED, rentalId);
    }
}
