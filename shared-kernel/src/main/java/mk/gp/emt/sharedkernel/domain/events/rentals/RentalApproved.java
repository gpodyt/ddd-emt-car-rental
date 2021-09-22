package mk.gp.emt.sharedkernel.domain.events.rentals;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class RentalApproved extends DomainEvent {

    public RentalApproved(String rentalId) {
        super(Topic.RENTAL_APPROVED, rentalId);
    }
}
