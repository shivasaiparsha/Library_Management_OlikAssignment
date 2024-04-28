package com.Olik.Library_Management.ServiceClassTest.RentalServiceTest;

import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.Services.Implementation.RentalServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RentalCreateTest {

    @Autowired
    private RentalServiceImplementation rentalServiceImplementation;

    @Test
    public void rental_Service_Create_rental_forABook_argument_NotPresent_Test() throws Exception{

        String message="One or more required fields are missing in the rental request data.";

          // when anyone the fields are not entered by the user
        RentalrequestDto rentalrequestDto=new RentalrequestDto();
        try{
            rentalServiceImplementation.createRental(rentalrequestDto);
        }
        catch (IllegalArgumentException e){
            Assertions.assertEquals(message, e.getMessage());
        }

    }

    @Test
    public void rental_Service_Create_rental_forABook_Book_NotAvailable_Test() throws Exception{

        String expectesMessage="One or more required fields are missing in the rental request data.";
        

        RentalrequestDto rentalrequestDto=new RentalrequestDto();


        try{
            rentalServiceImplementation.createRental(rentalrequestDto);
        }
        catch (IllegalArgumentException e){
            Assertions.assertEquals(expectesMessage, e.getMessage());
        }

    }


}
