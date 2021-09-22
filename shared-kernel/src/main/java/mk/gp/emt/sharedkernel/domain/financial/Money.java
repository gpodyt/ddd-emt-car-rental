package mk.gp.emt.sharedkernel.domain.financial;

import lombok.Getter;
import lombok.NonNull;
import mk.gp.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Getter
public class Money implements ValueObject {
    @Enumerated(value = EnumType.STRING)
    private final Currency currency;
    private final double amount;

    protected Money(){
        this.currency = null;
        this.amount = 0.0;
    }

    public Money(@NonNull Currency currency, double amount){
        this.currency = currency;
        this.amount = amount;
    }

    public static Money valueOf(Currency currency, double amount){
        return new Money(currency, amount);
    }

    public Money add(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
        }
        return new Money(currency,amount + money.amount);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
        }
        return new Money(currency,amount - money.amount);
    }

    public Money convert(@NonNull Currency fromCurrency, double fromAmount, @NonNull Currency toCurrency){
        if(fromCurrency.name().equals("USD"))
            fromAmount = fromAmount * 0.85;
        else if(fromCurrency.name().equals("MKD"))
            fromAmount = fromAmount * 0.016;
        if(toCurrency.name().equals("MKD"))
            return new Money(toCurrency, fromAmount*61.38);
        if(toCurrency.name().equals("USD"))
            return new Money(toCurrency, fromAmount*1.17);
        return new Money(toCurrency, fromAmount);
    }

    public Money multiply(int m)  {
        return new Money(currency,amount*m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

}
