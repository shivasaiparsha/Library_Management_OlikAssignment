package com.Olik.Library_Management.ControllerTest.BookControllerTest;

import com.Olik.Library_Management.Controllers.BookController;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes

public class DeleteBookApiTest {
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
    public void  Book_Controller_DeleteById_API_Success_Testing(){

        int bookId=3; // make sure that book should be present in data base
        String url=baseUrl+"/api/book/deleteBookById/"+bookId;
        String message="book deleted successfully";

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?>  request=new HttpEntity<>(headers);

        ResponseEntity<String> response=restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                request,
                String.class
        );

        Assertions.assertEquals(message,response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void  Book_Controller_DeleteById_API_BookNotFoun_Exception_Testing()
    {
        int bookId=3;
        String url=baseUrl+"/api/book/deleteBookById/"+bookId;
        String message="400 : \"book not found with bookId :"+bookId+"\"";

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?>  request=new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    String.class
            );



        }
        catch (Exception e)
        {
            Assertions.assertEquals(message, e.getMessage());
        }
    }
}
