package mk.gp.emt.sharedkernel.domain.events.vehicles;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.config.Topic;
import mk.gp.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class VehicleRemoved extends DomainEvent {

    public VehicleRemoved(String vehicleId) {
        super(Topic.VEHICLE_REMOVED, vehicleId);
    }
}
