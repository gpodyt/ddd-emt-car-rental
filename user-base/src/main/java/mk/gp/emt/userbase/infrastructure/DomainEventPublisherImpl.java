package mk.gp.emt.userbase.infrastructure;

import lombok.AllArgsConstructor;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;
import mk.gp.emt.sharedkernel.infrastructure.DomainEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        this.kafkaTemplate.send(event.topic(),event.getJsonData());
    }
}

