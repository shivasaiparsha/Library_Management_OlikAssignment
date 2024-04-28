package com.Olik.Library_Management.Services.Implementation;

import com.Olik.Library_Management.CustomExeception.BookNotAvailableException;
import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.CustomExeception.RentalIdNotvalid;
import com.Olik.Library_Management.CustomExeception.RentalIsOverDueException;
import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Models.Rental;
import com.Olik.Library_Management.Repositories.BookRepository;
import com.Olik.Library_Management.Repositories.RentalRepository;
import com.Olik.Library_Management.Services.Interfaces.RentalInterface;
import com.Olik.Library_Management.Transformers.RentalTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImplementation implements RentalInterface {


    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public RentalDto createRental(RentalrequestDto rentalDTO) throws BookNotAvailableException, BookNotFoundException, SQLException {

        if (rentalDTO.getRenterName() == null || rentalDTO.getBookId() == null || rentalDTO.getReturnDate() == null) {
            throw new IllegalArgumentException("One or more required fields are missing in the rental request data.");
        }

        Book book = bookRepository.findById(rentalDTO.getBookId()) // check weather the book is found in the db
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + rentalDTO.getBookId()));

        if (!book.isBookAvailable()) { // check weather the book is available for the rent
            throw new BookNotAvailableException("Book with id " + book.getId() + " is not available for rental");
        }

        Rental rental = new Rental();  // creating rental object
        rental.setRenterName(rentalDTO.getRenterName());
        rental.setRentalDate(LocalDate.now()); // setting the current data as  rental date
        rental.setReturnDate(rentalDTO.getReturnDate()); // assumed rental date
        rental.setBook(book);

        book.setBookAvailable(false); // book is not available to the rent
        bookRepository.save(book);

        rental = rentalRepository.save(rental); // saving the rental object

        RentalDto rentalDto = RentalTransformer.createRental(rental);
        return rentalDto;
    }

    @Override
    public List<RentalDto> getAllRentals() {

          List<Rental> rentalList=rentalRepository.findAll(); // find all the rentals prent in db
        return rentalList.stream().map((Rental rental) -> RentalTransformer.createRental(rental)).collect(Collectors.toList());

    }

    @Override
    public String bookReturn(Integer rentalId) throws IllegalArgumentException, RentalIdNotvalid,Exception,IllegalArgumentException {

        if (rentalId == null || rentalId <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");
        // check weather the rental present in database
          Rental rental=rentalRepository.findById(rentalId).orElseThrow(()-> new RentalIdNotvalid(" invalid RentalId "+rentalId));

        LocalDate currentDate=LocalDate.now();
        LocalDate rentalDate=rental.getRentalDate();
          boolean isValidRental=checkWeatherTheRentalIsValid(rentalDate);
          if(isValidRental) // check weather the rental return date is overdue or not
              throw new RentalIsOverDueException("rental is exceeded over 14 days");
        Book book=rental.getBook();
        book.setBookAvailable(true); // make is available for the other rentals
        bookRepository.save(book);
        rental.setReturnDate(currentDate); // setting current date as a return date
        rentalRepository.save(rental);
        return "book returned succesfully";
    }

    @Override
    public boolean checkWeatherTheRentalIsValid(LocalDate rentalDate) {
        // if the  currentDate minus rentalDate is greater than 14 days
        // then the rental is invalid
        LocalDate currentDate = LocalDate.now();
        LocalDate fourteenDaysAgo = currentDate.minusDays(14);

        return rentalDate.isBefore(fourteenDaysAgo);
    }


}
