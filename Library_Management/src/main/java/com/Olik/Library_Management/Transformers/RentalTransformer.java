package com.Olik.Library_Management.Transformers;

import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Models.Rental;

import java.io.IOException;

public class RentalTransformer {

    public static RentalDto createRental(Rental rental) {
        return RentalDto.builder()
                .rentalId(rental.getId())
                .renterName(rental.getRenterName())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .bookTitle(rental.getBook().getTitle())
                .build();
    }
}
