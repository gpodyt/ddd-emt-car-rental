package mk.gp.emt.sharedkernel.infrastructure;

import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
