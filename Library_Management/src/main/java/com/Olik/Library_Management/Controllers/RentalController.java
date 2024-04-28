package com.Olik.Library_Management.Controllers;
import com.Olik.Library_Management.CustomExeception.BookNotAvailableException;
import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.CustomExeception.RentalIdNotvalid;
import com.Olik.Library_Management.CustomExeception.RentalIsOverDueException;

import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;
import com.Olik.Library_Management.Services.Implementation.RentalServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Slf4j
public class RentalController {

    @Autowired
    private RentalServiceImplementation rentalService;



    @PostMapping("/createRent")
    public ResponseEntity<?> creatRentForABook(@RequestBody RentalrequestDto rentalRequestDto) {
        try {
            // Try to create a rental for the provided book.
            RentalDto createdRental = rentalService.createRental(rentalRequestDto);

            // Log a success message indicating the rental creation.
            log.info("Rental created successfully for book id: {}", rentalRequestDto.getBookId());

            // Return a ResponseEntity with HTTP status 200 (OK) and the created rental information.
            return ResponseEntity.ok(createdRental);
        } catch (BookNotFoundException | BookNotAvailableException e) {
            // If a BookNotFoundException or BookNotAvailableException is caught, log the error and return a ResponseEntity
            // with HTTP status 404 (NOT FOUND) and the error message.
            log.error("Error creating rental for book id: {}", rentalRequestDto.getBookId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // If any other exception occurs, log the error and return a ResponseEntity with HTTP status 500 (INTERNAL SERVER ERROR)
            // and the error message.
            log.error("Internal server error while creating rental for book id: {}", rentalRequestDto.getBookId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/getAllRentalRecords")
    public ResponseEntity<?> getAllRentals() { // this method will return all the rentals present in database
        try {
            List<RentalDto> rentals = rentalService.getAllRentals();
            return ResponseEntity.ok(rentals);
        }
        catch (Exception e){
            log.error("Internal server error while returning List of rentals");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/returnBook/{rentalId}") // Endpoint for returning a book associated with the provided rentalId.
    public ResponseEntity<String> returnBook(@PathVariable("rentalId") Integer rentalId) {
        try {
            // Attempt to return the book for the given rentalId.
            String message = rentalService.bookReturn(rentalId);
            log.info("Book returned successfully for rental id: {}", rentalId);
            return ResponseEntity.ok(message);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RentalIdNotvalid | RentalIsOverDueException e) {
            // return error message if rentalId is not valid or if rental is overdue.
            log.error("Error returning book for rental id: {}", rentalId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log and return internal server error message for any other exceptions. like sql exception, Hibernate exception
            log.error("Internal server error while returning book for rental id: {}", rentalId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
