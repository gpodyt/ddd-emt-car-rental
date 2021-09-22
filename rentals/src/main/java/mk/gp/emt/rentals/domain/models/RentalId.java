package mk.gp.emt.rentals.domain.models;

import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.DomainObjectId;

public class RentalId extends DomainObjectId {
    private RentalId() {
        super(RentalId.randomId(RentalId.class).getId());
    }

    public RentalId(@NonNull String uuid) {
        super(uuid);
    }

    public static RentalId of(String uuid){
        RentalId id = new RentalId(uuid);
        return id;
    }
}
