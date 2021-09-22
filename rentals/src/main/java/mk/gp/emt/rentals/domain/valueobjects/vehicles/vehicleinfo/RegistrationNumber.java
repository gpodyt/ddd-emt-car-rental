package mk.gp.emt.rentals.domain.valueobjects.vehicles.vehicleinfo;

import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;

public class RegistrationNumber implements ValueObject {

    private final String city;
    private final String number;
    private final String suffix;

    protected RegistrationNumber(){
        this.city = "";
        this.number = "";
        this.suffix = "";
    }

    public RegistrationNumber(@NonNull String city, @NonNull String number, @NonNull String suffix){
        this.city = city;
        this.number = number;
        this.suffix = suffix;
    }

    public static RegistrationNumber valueOf(String city, String number, String suffix){
        return new RegistrationNumber(city, number, suffix);
    }
}
