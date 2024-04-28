package com.Olik.Library_Management.ControllerTest.BookControllerTest;

import com.Olik.Library_Management.Controllers.BookController;
import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
public class AddBookControllerTest {

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
    public void testAddBook_Success() throws Exception {

        String url=baseUrl+"/api/book/addBook";
        // Arrange
        //  duplicates entriens  are not allowed if you have book with same isnb it will throw unique constraints
        // exception
        AddBookDto addBookDto = new AddBookDto();
        addBookDto.setAuthorId(4);
        addBookDto.setAvailable(true);
        addBookDto.setIsbn("234-3546-78-2123");
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setTitle("Kabir KaPAdh1");
        // Mock the behavior of bookService

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AddBookDto> requestEntity = new HttpEntity<>(addBookDto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Book added Successfully", response.getBody());


    }

    @Test
    public void addBook_AuthorNotFoundException() throws Exception {
        // Arrange
        int authorId = 3;
        String errorMessage = "Author not found";

        AddBookDto addBookDto = new AddBookDto();
        // Set values for the fields (title, available, isbn, etc.)
        addBookDto.setTitle("Sample Book");
        addBookDto.setAvailable(true);
        addBookDto.setIsbn("978-3-16-148410-0");
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setAuthorId(1);


        String url = baseUrl+"/api/book/addBook";

        // Set up headers (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AddBookDto> requestEntity = new HttpEntity<>(addBookDto, headers);



        // Act and Assert
        HttpClientErrorException exception = Assertions.assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(baseUrl, requestEntity, String.class)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }



}
