package com.Olik.Library_Management.ControllerTest.AuthorControllerTest;

import com.Olik.Library_Management.Controllers.AuthorController;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes

public class GetAuthorApiTest {
    @Mock
    private AuthorImplementation authorServiceMock;
    @InjectMocks
    private AuthorController authorController;

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
    public void GetAuthorById_API_Sucess_test() {
        // Prepare request URL with path variable

        int id = 1;
        String getAuthorurl = baseUrl + "/api/authors/getAuthor/" + id;

        AuthorDto author=new AuthorDto();
        author.setAuthorName("shivasai");
        author.setBiography("shivasai is good poet");



         // first set authors
        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build request entity
        // Create the HTTP entity with the request body and headers

        // Send the HTTP request
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity <Author> responseEntity = restTemplate.exchange(getAuthorurl, HttpMethod.GET, requestEntity, Author.class, id);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Parse response body if needed
        Author author1 = responseEntity.getBody();
        Assertions.assertEquals(author.getAuthorName(), author1.getAuthorName());
        Assertions.assertEquals(author.getBiography(), author1.getBiography());
    }

    @Test
    public void GetAuthorById_API_AuthorNotFoundException_test() {
        // Prepare request URL with path variable

        int id = 100;
        String getAuthorurl = baseUrl + "/api/authors/getAuthor/" + id;

        AuthorDto author=new AuthorDto();
        author.setAuthorName("shivasai");
        author.setBiography("shivasai is good poet");



        // first set authors
        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build request entity
        // Create the HTTP entity with the request body and headers

        // Send the HTTP request
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
      try {
          ResponseEntity<Author> responseEntity = restTemplate.exchange(getAuthorurl, HttpMethod.GET, requestEntity, Author.class, id);
          Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      }
       catch (HttpClientErrorException e) {

           Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
           Assertions.assertEquals("Author not available with id :"+id, e.getMessage().substring(7,e.getMessage().length()-1));
       }
    }


}
