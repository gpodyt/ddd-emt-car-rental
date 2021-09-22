package mk.gp.emt.rentals.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.gp.emt.rentals.domain.exceptions.RentalIdDoesNotExistException;
import mk.gp.emt.rentals.domain.models.Rental;
import mk.gp.emt.rentals.domain.models.RentalId;
import mk.gp.emt.rentals.domain.repository.RentalRepository;
import mk.gp.emt.rentals.domain.valueobjects.vehicles.Vehicle;
import mk.gp.emt.rentals.service.RentalService;
import mk.gp.emt.rentals.service.forms.RentalForm;
import mk.gp.emt.sharedkernel.domain.users.PersonId;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final Validator validator;
    private final RestTemplate restTemplate;

    public RentalServiceImpl(RentalRepository rentalRepository, Validator validator) {
        this.rentalRepository = rentalRepository;
        this.validator = validator;
        this.restTemplate = new RestTemplate();
        this.restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
    }

    private UriComponentsBuilder vehicleUri() {
        return UriComponentsBuilder.fromUriString("http://localhost:9090/api");
    }

    @Override
    public RentalId rent(RentalForm rentalForm) {
        Objects.requireNonNull(rentalForm, "Rental must not be null!");
        var constraintViolations = validator.validate(rentalForm);
        if(constraintViolations.size()>0){
            throw new ConstraintViolationException("The rental form is not valid!", constraintViolations);
        }
        Rental newRental = null;
        if(rentalRepository.findAll().stream().map(r -> r.getVehicleId().getId()).anyMatch(id -> id.equals(rentalForm.getVehicleId().getId())))
            throw new IllegalArgumentException("Vehicle with id " + rentalForm.getVehicleId().getId() + " is currently being rented!");
        Rental test = toDomainObject(rentalForm);
        newRental = rentalRepository.saveAndFlush(test);
        return newRental.getId();
    }

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<Rental> findById(RentalId id) {
        return rentalRepository.findById(id);
    }

    @Override
    public void approveRent(RentalId id) {
       Rental rentalToApprove = rentalRepository.findById(id).orElseThrow(RentalIdDoesNotExistException::new);
       rentalToApprove.approveRent();
    }

    @Override
    public void finishRent(RentalId id) {
        Rental rentalToFinish = rentalRepository.findById(id).orElseThrow(RentalIdDoesNotExistException::new);
        rentalToFinish.finishRent();
        rentalRepository.saveAndFlush(rentalToFinish);
        restTemplate.exchange(vehicleUri().path("/freevehicle").build().toUri(), HttpMethod.POST,new HttpEntity<>(rentalToFinish.getVehicleId().getId()), Void.class);
    }

    private Rental toDomainObject(RentalForm rentalForm){
        Vehicle vehicleToRent;
        try {
            vehicleToRent = restTemplate.exchange(vehicleUri().path("/vehicle/" + rentalForm.getVehicleId().getId()).build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<Vehicle>() {
            }).getBody();
        }
        catch (Exception e){
            return null;
        }
        rentalForm.setRentalOwnerId(vehicleToRent.getOwnerId());
        return new Rental(rentalForm.getRentalOwnerId(),
                rentalForm.getVehicleId(),
                rentalForm.getCustomerId(),
                rentalForm.getFromDate(),
                rentalForm.getToDate(),
                (double)vehicleToRent.getHorsePower()/5 - (double)vehicleToRent.getMileage().getKilometers()/10000);
    }

    @Override
    public List<Rental> findAllByUser(String id) {
        return rentalRepository.findAll().stream().filter(rental -> rental.getRentalOwnerId().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findAllWhereUserIsCustomer(String id) {
        return rentalRepository.findAll().stream().filter(rental -> rental.getCustomerId().getId().equals(id))
                .collect(Collectors.toList());
    }
}
