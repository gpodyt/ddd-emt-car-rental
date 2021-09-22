package mk.gp.emt.rentals.domain.valueobjects.vehicles;

import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.DomainObjectId;

public class VehicleId extends DomainObjectId {
    private VehicleId() {
        super(VehicleId.randomId(VehicleId.class).getId());
    }

    public VehicleId(String uuid) {
        super(uuid);
    }

    public static VehicleId of(String uuid){
        VehicleId id = new VehicleId(uuid);
        return id;
    }

}
