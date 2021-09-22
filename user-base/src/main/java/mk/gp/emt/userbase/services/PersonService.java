package mk.gp.emt.userbase.services;

import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.userbase.domain.models.Person;
import mk.gp.emt.userbase.services.forms.PersonForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends UserDetailsService {
    Person findById(PersonId id);
    List<Person> findAll();
    Person createPerson(PersonForm personForm);
    Person loginPerson(String email, String password);
    Person findByEmail(String email);
}
