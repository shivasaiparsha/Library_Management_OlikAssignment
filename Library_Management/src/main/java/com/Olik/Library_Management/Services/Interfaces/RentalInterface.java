package com.Olik.Library_Management.Services.Interfaces;

import com.Olik.Library_Management.CustomExeception.BookNotAvailableException;
import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.CustomExeception.RentalIdNotvalid;
import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RentalInterface {
    RentalDto createRental(RentalrequestDto rentalDTO) throws BookNotAvailableException, BookNotFoundException, SQLException;
    List<RentalDto> getAllRentals();

     String bookReturn(Integer rentalId) throws IllegalArgumentException,RentalIdNotvalid, Exception;

     boolean checkWeatherTheRentalIsValid(LocalDate rentalDate);
}
