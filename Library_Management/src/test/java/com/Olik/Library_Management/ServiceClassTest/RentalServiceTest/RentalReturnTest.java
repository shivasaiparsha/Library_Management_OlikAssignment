package com.Olik.Library_Management.ServiceClassTest.RentalServiceTest;

import com.Olik.Library_Management.CustomExeception.BookNotAvailableException;
import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;
import com.Olik.Library_Management.Services.Implementation.RentalServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;

@SpringBootTest
public class RentalReturnTest {

    @Autowired
    private RentalServiceImplementation rentalServiceImplementation;

    @Test
    public void  Rental_return_book_Success_Message_test() throws Exception {

        // create one rental first
        RentalrequestDto rentalRequest = new RentalrequestDto();
        rentalRequest.setRenterName("John Doe");
        rentalRequest.setBookId(1);
        rentalRequest.setReturnDate(LocalDate.of(2024, 5, 15));

        RentalDto rentalDto=rentalServiceImplementation.createRental(rentalRequest);

         String expectedMessage="book returned succesfully";

         String actualMessage=rentalServiceImplementation.bookReturn(1);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
