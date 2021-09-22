package mk.gp.emt.rentals.ports.controllers;

import lombok.AllArgsConstructor;
import mk.gp.emt.rentals.domain.models.Rental;
import mk.gp.emt.rentals.domain.models.RentalId;
import mk.gp.emt.rentals.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "http://localhost:9092")
@RequestMapping("/api")
@AllArgsConstructor
public class RestController {
    private final RentalService rentalService;

    @GetMapping("/admin/allrentals")
    public List<Rental> getAllRentals(){
        return rentalService.findAll();
    }

    @GetMapping("/myrentals/{id}")
    public List<Rental> getUserRentals(@PathVariable String id){
        return rentalService.findAllByUser(id);
    }

    @GetMapping("/askedrentals/{id}")
    public List<Rental> getUserAskedRentals(@PathVariable String id){
        return rentalService.findAllWhereUserIsCustomer(id);
    }

    @GetMapping("/rent/{id}")
    public Rental getRentalById(@PathVariable String id){
        return rentalService.findById(RentalId.of(id)).get();
    }
}
