package mk.gp.emt.vehicles.domain.repository;

import mk.gp.emt.vehicles.domain.models.Vehicle;
import mk.gp.emt.vehicles.domain.models.VehicleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, VehicleId> {
}
