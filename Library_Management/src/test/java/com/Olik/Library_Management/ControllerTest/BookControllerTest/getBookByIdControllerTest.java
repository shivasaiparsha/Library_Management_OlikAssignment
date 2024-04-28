package com.Olik.Library_Management.ControllerTest.BookControllerTest;

import com.Olik.Library_Management.Controllers.BookController;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes

public class getBookByIdControllerTest {

    @Mock
    private BookImplementation bookService;

    @InjectMocks
    private BookController bookController;

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
    public void testGetBookById_Success() throws Exception {
        // Assuming a book exists with ID 1 in your database
        int bookId = 1;
        String url = baseUrl+"/api/book/getBookByBookId/"+bookId;

        // Set the port dynamically based on the random port used by Spring Boot
        HttpHeaders headers=new HttpHeaders();
         headers.set("Content-Type", "application/json");
        HttpEntity<?> httpEntity=new HttpEntity<>(headers);
        // Send the GET request
        ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class);

        // Assert the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertNotNull(response.getBody());
        // You can further assert the book details retrieved based on your Book class
    }

    @Test
    public void testGetBookById_NotFound() throws Exception {
        // Assuming a book doesn't exist with ID 100
        int bookId = 100;
        String url = baseUrl+"/api/book/getBookByBookId/"+bookId;
        String message="400 : \"No value present\"";

        // Set the port dynamically based on the random port used by Spring Boot
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> httpEntity=new HttpEntity<>(headers);

          try {
              // Send the GET request
              ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
          }
          catch (Exception e)
          {   // no value present exception
               Assertions.assertEquals(message, e.getMessage());
          }
    }
}
