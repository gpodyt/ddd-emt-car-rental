package mk.gp.emt.vehicles.domain.valueobjects.vehicleinfo;

import lombok.Getter;
import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class RegistrationNumber implements ValueObject {

    @Column(length = 2)
    private final String city;
    @Column(length = 4)
    private final String number;
    @Column(length = 2)
    private final String suffix;

    protected RegistrationNumber(){
        this.city = "";
        this.number = "";
        this.suffix = "";
    }

    public RegistrationNumber(@NonNull String city, @NonNull String number, @NonNull String suffix){
        if(!validate(city, number, suffix))
            throw new IllegalArgumentException("The registration number is not valid!");
        this.city = city;
        this.number = number;
        this.suffix = suffix;
    }

    public static RegistrationNumber valueOf(String city, String number, String suffix){
        return new RegistrationNumber(city, number, suffix);
    }

    private boolean validate(String city, String number, String suffix){
        for(char c:city.toCharArray()){
            if(!Character.isLetter(c))
                return false;
        }
        for(char c:suffix.toCharArray()){
            if(!Character.isLetter(c))
                return false;
        }
        for(char c:number.toCharArray()){
            if(!Character.isDigit(c))
                return false;
        }
        return true;
    }

}
