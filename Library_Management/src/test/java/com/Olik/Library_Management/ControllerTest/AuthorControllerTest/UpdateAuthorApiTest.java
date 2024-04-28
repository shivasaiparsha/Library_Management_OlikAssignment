package com.Olik.Library_Management.ControllerTest.AuthorControllerTest;

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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes

public class UpdateAuthorApiTest {
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
    public void updateAuthorById_Success() throws Exception {
        // Mock data
        int authorId = 3;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName("Updated Author");
        authorDto.setBiography("Updated Biography");

        String successMessage = "Author updated successfully";

        // Mock service behavior
        when(authorServiceMock.updateAuthorById(authorId, authorDto)).thenReturn(successMessage);

        // Set up the URL
        String url = baseUrl + "/api/authors/updateAuthor/" + authorId;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the request body and headers
        HttpEntity<AuthorDto> requestEntity = new HttpEntity<>(authorDto, headers);

        // Send the HTTP request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(successMessage, responseEntity.getBody());
    }

    @Test
    public void updateAuthorById_AuthorNotFoundException() throws AuthorNotFoundException,Exception {

        // Arrange
        Integer id = 1;
        String url = baseUrl + "/api/authors/updateAuthor/"+id;
        AuthorDto authorDTO = new AuthorDto(/* Add necessary fields */);
        String errorMessage = "500 : Failed to update author: No value present";
        when(authorServiceMock.updateAuthorById(id, authorDTO)).thenThrow(new AuthorNotFoundException(errorMessage));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthorDto> requestEntity = new HttpEntity<>(authorDTO, headers);

        // Act
        // Assert
        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
        });

        Assertions.assertEquals(errorMessage, exception.getMessage());
    }



}
