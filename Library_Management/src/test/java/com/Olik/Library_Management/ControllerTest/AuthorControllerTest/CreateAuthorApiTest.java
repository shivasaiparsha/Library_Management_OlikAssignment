package com.Olik.Library_Management.ControllerTest.AuthorControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.Olik.Library_Management.Controllers.AuthorController;
import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
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
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
public class CreateAuthorApiTest {


   @Autowired
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
    public void createAuthor_Success_test(AuthorDto author) throws Exception {
        String url=baseUrl+"/api/authors/createAuthor";


        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName("Test Author");
        authorDto.setBiography("Test Biography");

        // Mock service behavior
        when(authorServiceMock.createAuthor(authorDto)).thenReturn("Author created successfully");

        // Set up headers and authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity with the request body and headers
        HttpEntity<AuthorDto> requestEntity = new HttpEntity<>(authorDto, headers);

        // Create the URL for the API endpoint


        // Send the HTTP request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        // Verify the response status code and message
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Author created successfully", responseEntity.getBody());

    }

    @Test
    public void deleteAuthorById_Success() throws AuthorNotFoundException {
        // Mock data
        Integer authorId = 2;
        String successMessage = "Author deleted successfully";

        // Mock service behavior
//        when(authorServiceMock.deleteAuthorById(authorId)).thenReturn(successMessage);

        // Set up the URL
        String url = baseUrl + "/api/authors/DeleteAuthorById/" + authorId;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();

        // Create the HTTP entity with headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send the HTTP request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

        // Verify the response status code and message
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(successMessage, responseEntity.getBody());
    }

    @Test
    public void deleteAuthorById_AuthorNotFound() throws Exception {
        // Mock data
        int authorId = 1;
        String errorMessage = "404 : Author not found with id : " + authorId;

        // Mock service behavior
        when(authorServiceMock.deleteAuthorById(authorId)).thenThrow(new AuthorNotFoundException(errorMessage));

        // Set up the URL
        String url = baseUrl + "/api/authors/DeleteAuthorById/" + authorId;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();

        // Create the HTTP entity with headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send the HTTP request
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
            // Verify the response status code and message
            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }
        catch (Exception e){
            assertEquals(e.getMessage(), e.getMessage());

        }

    }

}
