package mk.gp.emt.userbase.domain.models;

import lombok.Getter;
import mk.gp.emt.sharedkernel.domain.base.AbstractEntity;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.userbase.domain.valueobjects.users.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
@Getter
public class Person extends AbstractEntity<PersonId> implements UserDetails {
    String name;
    String surname;
    String username; //email
    String password;
    String street;
    String city;

    protected Person(){
        super(PersonId.randomId(PersonId.class));
        name = "";
        surname = "";
        username = "";
        password = "";
        street = "";
        city = "";
        role = UserRole.ROLE_USER;
    }

    public Person(String name, String surname, String username, String password, String street, String city, UserRole role){
        super(PersonId.randomId(PersonId.class));
        this.name=name;
        this.surname=surname;
        this.username = username;
        this.password=password;
        this.street=street;
        this.city=city;
        this.role=role;
    }

    @Enumerated(value = EnumType.STRING)
    UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
