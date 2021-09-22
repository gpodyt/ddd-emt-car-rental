package mk.gp.emt.rentals.domain.valueobjects.time;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Embeddable
@Getter
public class Period implements ValueObject {

    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;

    protected Period(){
        this.fromDate = null;
        this.toDate = null;
    }

    public Period(LocalDateTime from, LocalDateTime to){
        if(from.compareTo(to) > -1){
            throw new IllegalArgumentException("From date is larger or equal to to date!");
        }
        else if(from.plusMonths(1).compareTo(to) < 1)
            throw new IllegalArgumentException("Rental period cannot be longer than 1 month!");
        this.fromDate = from;
        this.toDate = to;
    }

    public int days(){
        return (int)ChronoUnit.DAYS.between(fromDate, toDate);
    }
}
