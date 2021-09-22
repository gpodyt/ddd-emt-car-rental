package mk.gp.emt.userbase.port;

import mk.gp.emt.sharedkernel.domain.events.rentals.RentalAdded;
import mk.gp.emt.sharedkernel.domain.events.rentals.RentalApproved;
import mk.gp.emt.sharedkernel.domain.events.rentals.RentalFinished;
import mk.gp.emt.sharedkernel.domain.events.vehicles.RentalAddedForV;
import mk.gp.emt.sharedkernel.domain.events.vehicles.VehicleAdded;
import mk.gp.emt.sharedkernel.domain.events.vehicles.VehicleModified;
import mk.gp.emt.sharedkernel.domain.events.vehicles.VehicleRemoved;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import mk.gp.emt.sharedkernel.infrastructure.DomainEventPublisher;
import mk.gp.emt.userbase.domain.models.Person;
import mk.gp.emt.userbase.domain.valueobjects.users.UserRole;
import mk.gp.emt.userbase.services.PersonService;
import mk.gp.emt.userbase.services.forms.PersonForm;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class RestController {

    private final PersonService personService;
    private final DomainEventPublisher domainEventPublisher;
    private final RestTemplate restTemplate;

    public RestController(PersonService personService, DomainEventPublisher domainEventPublisher){
        this.personService = personService;
        this.domainEventPublisher = domainEventPublisher;
        this.restTemplate = new RestTemplate();
        this.restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
    }

    private UriComponentsBuilder rentalUri() {
        return UriComponentsBuilder.fromUriString("http://localhost:9091/api");
    }

    private UriComponentsBuilder vehicleUri() {
        return UriComponentsBuilder.fromUriString("http://localhost:9090/api");
    }


    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAllUsers(){
        return ResponseEntity.ok(personService.findAll().stream().map(person ->
                new PersonForm(person.getUsername(), person.getRole(), person.getName(), person.getSurname(), "", person.getStreet(), person.getCity()))
                .collect(Collectors.toList()));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        return ResponseEntity.ok(personService.findById(PersonId.of(id)));
    }

    @GetMapping("/admin/rentals")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAllRentals(){
        try {
            return ResponseEntity.ok(restTemplate.exchange(rentalUri().path("/admin/allrentals").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/admin/vehicles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAllVehicles(){
        try {
            return ResponseEntity.ok(restTemplate.exchange(vehicleUri().path("/admin/allvehicles").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<?>> getPublicVehicles(){
        try {
            return ResponseEntity.ok(restTemplate.exchange(vehicleUri().path("/publicvehicles").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/myvehicles")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<?>> getMyVehicles(HttpServletRequest request){
        String userId = personService.findByEmail(request.getRemoteUser()).getId().getId();
        try {
            return ResponseEntity.ok(restTemplate.exchange(vehicleUri().path("/myvehicles/" + userId).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/myrentals")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<?>> getMyRentals(HttpServletRequest request){
        String userId = personService.findByEmail(request.getRemoteUser()).getId().getId();
        try {
            return ResponseEntity.ok(restTemplate.exchange(rentalUri().path("/myrentals/" + userId).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/askedrentals")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAskedRentals(HttpServletRequest request){
        String userId = personService.findByEmail(request.getRemoteUser()).getId().getId();
        try {
            return ResponseEntity.ok(restTemplate.exchange(rentalUri().path("/askedrentals/" + userId).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/manufacturers")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<?>> getManufacturers(){
        try {
            return ResponseEntity.ok(restTemplate.exchange(vehicleUri().path("/manufacturers").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/addvehicle")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> addNewVehicle(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new VehicleAdded(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getvehicle/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getVehicle(@PathVariable String id){
        try {
            return ResponseEntity.ok(restTemplate.exchange(vehicleUri().path("/vehicle/" + id).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<Object>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/editvehicle")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> editVehicle(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new VehicleModified(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removevehicle")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> removeVehicle(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new VehicleRemoved(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addrent")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> addNewRent(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new RentalAdded(jsonPayload));
        domainEventPublisher.publish(new RentalAddedForV(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approverent")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> approveRent(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new RentalApproved(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getrent/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getRent(@PathVariable String id){
        try {
            return ResponseEntity.ok(restTemplate.exchange(rentalUri().path("/rent/" + id).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<?>>() {
            }).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/finishrent")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> finishRent(@RequestBody String jsonPayload){
        domainEventPublisher.publish(new RentalFinished(jsonPayload));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/currentuser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request){
        if(request.getRemoteUser()==null || request.getRemoteUser().isEmpty())
            return ResponseEntity.badRequest().body(new Object(){
                public final String message = "There is no user currently logged in!";
            });
        else
            return ResponseEntity.ok(personService.findByEmail(request.getRemoteUser()));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> jsonPayload){
        UserRole role = UserRole.ROLE_USER;
        if(jsonPayload.get("username").contains("admin"))
            role = UserRole.ROLE_ADMIN;
        try {
            personService.createPerson(new PersonForm(
                    jsonPayload.get("username"),
                    role,
                    jsonPayload.get("name"),
                    jsonPayload.get("surname"),
                    jsonPayload.get("password"),
                    jsonPayload.get("street"),
                    jsonPayload.get("city")));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new Object(){
                public final String message = "Email already in use!";
            });
        }
        return ResponseEntity.ok(new Object(){
            public final String message = "User registered successfully!";
        });
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody Map<String, String> jsonPayload){
        Person person = null;
        try {
            person = personService.loginPerson(jsonPayload.get("username"), jsonPayload.get("password"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new Object(){
                public final String message = "Email or password incorrect!";
            });
        }
        return ResponseEntity.ok(person);
    }

}
