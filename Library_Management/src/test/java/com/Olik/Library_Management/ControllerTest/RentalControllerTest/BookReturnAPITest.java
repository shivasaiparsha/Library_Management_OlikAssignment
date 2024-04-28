package com.Olik.Library_Management.ControllerTest.RentalControllerTest;

import com.Olik.Library_Management.Models.Rental;
import com.Olik.Library_Management.Repositories.RentalRepository;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
public class BookReturnAPITest {

    @LocalServerPort
    private int port;



    private static String baseUrl = "http://localhost";

    @Autowired
    private RentalRepository rentalRepository;
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
    public void testReturnBook_validRentalId_returnsSuccess() throws Exception {
        // Setup a valid rental in the database (assuming rentalRepository is pre-populated with test data)
        Integer validRentalId = 1; // Replace with an existing rental ID
      String url=baseUrl+"/api/rentals/returnBook/"+validRentalId;
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity=new HttpEntity<>(headers);
        // Perform the API call using RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
               httpEntity,
                String.class
                );

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("book returned succesfully", response.getBody());

        // Verify data changes in the database (optional)
        Rental rental = rentalRepository.findById(validRentalId).orElseThrow();
        assertTrue(rental.getBook().isBookAvailable());
        assertNotNull(rental.getReturnDate());
    }

    @Test
    public void testReturnBook_invalidRentalId_returnsBadRequest() throws Exception {
        // Invalid rental ID (doesn't exist in the database)
        // when the user entered wrong rental id
        Integer invalidRentalId = 99;
        String url=baseUrl+"/api/rentals/returnBook/"+invalidRentalId;

        // adding heaader to the http call
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity=new HttpEntity<>(headers);

        try {
            // Perform the API call
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );
        }catch (HttpClientErrorException e) {

            // Assertions
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            Assertions.assertEquals("invalid RentalId "+invalidRentalId, e.getMessage().substring(8, e.getMessage().length()-1)); // Check for specific error message
                 // in the substring method we just removing the status code and queotes to check with expected output
        }
    }




}
