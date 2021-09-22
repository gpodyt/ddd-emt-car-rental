package mk.gp.emt.vehicles.domain.valueobjects.mileage;

import lombok.Getter;
import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Mileage implements ValueObject {

    private final int kilometers;

    protected Mileage(){
        this.kilometers = 0;
    }

    public Mileage(@NonNull int kilometers){
        this.kilometers = kilometers;
    }

    public static Mileage valueOf(int kilometers){
        return new Mileage(kilometers);
    }
}
