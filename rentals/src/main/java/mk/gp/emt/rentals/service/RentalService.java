package mk.gp.emt.rentals.service;

import mk.gp.emt.rentals.domain.exceptions.RentalIdDoesNotExistException;
import mk.gp.emt.rentals.domain.models.Rental;
import mk.gp.emt.rentals.domain.models.RentalId;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.Vehicle;
import mk.gp.emt.rentals.service.forms.RentalForm;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    RentalId rent(RentalForm rentalForm);
    List<Rental> findAll();
    Optional<Rental> findById(RentalId id);
    void approveRent(RentalId id);
    void finishRent(RentalId id);
    List<Rental> findAllByUser(String id);
    List<Rental> findAllWhereUserIsCustomer(String id);
}
