package mk.gp.emt.userbase.services.impl;

import lombok.AllArgsConstructor;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.userbase.domain.exceptions.UserNotFoundException;
import mk.gp.emt.userbase.domain.models.Person;
import mk.gp.emt.userbase.domain.repository.PersonRepository;
import mk.gp.emt.userbase.services.PersonService;
import mk.gp.emt.userbase.services.forms.PersonForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {



    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Person findById(PersonId id) {
        return personRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person createPerson(PersonForm personForm) {
        if(personRepository.findAll().stream().anyMatch(p -> p.getUsername().equals(personForm.getUsername())))
            throw new IllegalArgumentException("User already exists with that email!");
        return personRepository.saveAndFlush(new Person(
                personForm.getName(),
                personForm.getSurname(),
                personForm.getUsername(),
                passwordEncoder.encode(personForm.getPassword()),
                personForm.getStreet(),
                personForm.getCity(),
                personForm.getRole()));
    }

    @Override
    public Person loginPerson(String email, String password){
        Person user = personRepository.findAll().stream().filter(person -> person.getUsername().equals(email)).findFirst().orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException();
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findAll().stream().filter(person -> person.getUsername().equals(email)).findFirst().orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findAll().stream().filter(person -> person.getUsername().equals(email)).findFirst().orElseThrow(UserNotFoundException::new);
    }
}
