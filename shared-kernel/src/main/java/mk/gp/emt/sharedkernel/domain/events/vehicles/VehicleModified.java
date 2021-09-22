package mk.gp.emt.sharedkernel.domain.events.vehicles;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class VehicleModified extends DomainEvent {

    public VehicleModified(String vehicleAsJson) {
        super(Topic.VEHICLE_MODIFIED, vehicleAsJson);
    }
}
