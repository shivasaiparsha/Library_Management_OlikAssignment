package com.Olik.Library_Management.ControllerTest.RentalControllerTest;

import com.Olik.Library_Management.CustomExeception.BookNotAvailableException;
import com.Olik.Library_Management.DTOs.RequestDtos.RentalrequestDto;
import com.Olik.Library_Management.DTOs.ResponseDtos.RentalDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
public class RentalAPI_Controller_CreateRent_Test {

    @LocalServerPort
    private int port;



    private static String baseUrl = "http://localhost";

    @Autowired
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void createUri(){
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port));
    }

    @Test
  public void Rental_controller_createRentForABook_API_Success_Testing(){

        int bookId=4;
      RentalrequestDto rental = new RentalrequestDto();
          rental.setRenterName("shivasai");
          rental.setReturnDate(LocalDate.now());
          rental.setBookId(5);
      String url=baseUrl+"/api/rentals/createRent" ;

      String successMessage="book Updated successfully";
      HttpHeaders headers=new HttpHeaders();
      headers.set("Content-Type", "application/json");

      HttpEntity<RentalrequestDto> requestHttpEntity=new HttpEntity<>(rental,headers);

      ResponseEntity<RentalDto> responseEntity=restTemplate.exchange(
              url,
              HttpMethod.POST,
              requestHttpEntity,
              RentalDto.class
      );

      Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      Assertions.assertEquals(rental.getRenterName(), responseEntity.getBody().getRenterName());
      Assertions.assertEquals(rental.getReturnDate(), responseEntity.getBody().getReturnDate());

    }

    @Test
    public void Rental_controller_createRentForABook_API_HttpClientErrorException_Testing()
    {
        int bookId=15;
        RentalrequestDto rental = new RentalrequestDto();
        rental.setRenterName("shivasai");
        rental.setReturnDate(LocalDate.now());
        rental.setBookId(15);
        String expectedMessage="\"Book not found with id: "+bookId+"\"";
        String url=baseUrl+"/api/rentals/createRent" ;

        String successMessage="book Updated successfully";
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<RentalrequestDto> requestHttpEntity=new HttpEntity<>(rental,headers);

         try {
             ResponseEntity<RentalDto> responseEntity = restTemplate.exchange(
                     url,
                     HttpMethod.POST,
                     requestHttpEntity,
                     RentalDto.class
             );
         }catch (HttpClientErrorException e)
         {
             // client error exceptoion occur when the request data not presnt in database
             Assertions.assertEquals(expectedMessage, e.getMessage().substring(6));
         }
    }

    @Test
    public void Rental_controller_createRentForABook_API_BookNotAvailableForRentStatus_Testing() throws BookNotAvailableException
    {
        int bookId=5;
        RentalrequestDto rental = new RentalrequestDto();
        rental.setRenterName("shivasai");
        rental.setReturnDate(LocalDate.now());
        rental.setBookId(5);
        String expectedMessage="\"Book with id " +bookId+ " is not available for rental\"";
        String url=baseUrl+"/api/rentals/createRent" ;

        String successMessage="book Updated successfully";
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<RentalrequestDto> requestHttpEntity=new HttpEntity<>(rental,headers);

        try {
            ResponseEntity<RentalDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestHttpEntity,
                    RentalDto.class
            );
        }
        catch (HttpClientErrorException e) {

            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            Assertions.assertEquals(expectedMessage, e.getMessage().substring(6));
        }

    }



}
