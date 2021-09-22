package mk.gp.emt.userbase.domain.repository;

import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.userbase.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, PersonId> {
}
