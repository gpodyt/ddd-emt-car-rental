package mk.gp.emt.sharedkernel.domain.events.vehicles;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class VehicleAdded extends DomainEvent {

    public VehicleAdded(String vehicleAsJson) {
        super(Topic.VEHICLE_ADDED, vehicleAsJson);
    }
}
