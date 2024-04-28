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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
public class GetAllBooksControllerApiTest {

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

// before performing this test make sure that books are present in you database
    @Test
    public void getAllBooks_Success() throws Exception {

        String url=baseUrl+"/api/book/getAllBooks";
         // In my there are
        int expecetedBookListSize=3;
        String names[]={"Sample Book", "Sample Book2", "Kabir KaPAdh"};

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class, requestEntity);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expecetedBookListSize, response.getBody().size());

    }




}
