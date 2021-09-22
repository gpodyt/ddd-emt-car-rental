package mk.gp.emt.sharedkernel.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;

import java.time.Instant;

@Getter
public class DomainEvent {
    private Topic topic;
    private Instant occurredOn;
    private String jsonData;

    public DomainEvent(Topic topic, String jsonData) {
        this.occurredOn = Instant.now();
        this.topic = topic;
        this.jsonData = jsonData;
    }

    public String topic() {
        return topic.name();
    }

}
