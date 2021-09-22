package mk.gp.emt.userbase.services.forms;

import lombok.Data;
import mk.gp.emt.userbase.domain.models.Person;
import mk.gp.emt.userbase.domain.valueobjects.users.UserRole;

@Data
public class PersonForm {
    String username; //email
    UserRole role;

    String name;
    String surname;
    String password;
    String street;
    String city;

    protected PersonForm(){
        this.username = "";
        this.role = UserRole.ROLE_USER;
        this.name="";
        this.surname="";
        this.password="";
        this.street="";
        this.city="";
    }

    public PersonForm(String username, UserRole role, String name, String surname, String password, String street, String city) {
        this.username = username;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.street = street;
        this.city = city;
    }

    public static PersonForm of(Person person) {
        PersonForm details = new PersonForm();
        details.username = person.getUsername();
        details.role = person.getRole();
        return details;
    }

}
