package mk.gp.emt.rentals.domain.repository;

import mk.gp.emt.rentals.domain.models.Rental;
import mk.gp.emt.rentals.domain.models.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, RentalId> {
}
