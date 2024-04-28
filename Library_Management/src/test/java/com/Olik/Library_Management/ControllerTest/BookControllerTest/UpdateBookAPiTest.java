package com.Olik.Library_Management.ControllerTest.BookControllerTest;

import com.Olik.Library_Management.Controllers.BookController;
import com.Olik.Library_Management.DTOs.RequestDtos.UpdateBook;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
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

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes

public class UpdateBookAPiTest {

    @Mock
    private BookImplementation bookService;

    @InjectMocks
    private BookController bookController;

    @LocalServerPort
    private int port;



    private static String baseUrl = "http://localhost";

    @Autowired
    private AuthorImplementation authorService;

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
 public void BookControllerTest_UpdateBookApi_success_Status_test(){


        int bookId=1; //  book already presnt in db we are changing the details
         String url= baseUrl+"/api/book/UpdateBook/"+bookId;
         UpdateBook book=new UpdateBook();
         book.setTitle("The Lord of the Rings");
         book.setBookAvailable(true);
         book.setIsbn("978-0261102694");
         book.setPublicationDate(LocalDate.now());
        String successMessage="book Updated successfully";
         HttpHeaders headers=new HttpHeaders();
         headers.set("Content-Type", "application/json");

         HttpEntity<UpdateBook> requestHttpEntity=new HttpEntity<>(book,headers);

         ResponseEntity<String> responseEntity=restTemplate.exchange(
                 url,
                 HttpMethod.PUT,
                 requestHttpEntity,
                 String.class
         );

         Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
         Assertions.assertEquals(successMessage, responseEntity.getBody());
     }

    @Test
    public void BookControllerTest_UpdateBookApi_BookNotFount_Status(){


        int bookId=1; //  book already presnt in db we are changing the details
        String url= baseUrl+"/api/book/UpdateBook/"+bookId;
        UpdateBook book=new UpdateBook();
        book.setTitle("The Lord of the Rings");
        book.setBookAvailable(true);
        book.setIsbn("978-0261102694");
        book.setPublicationDate(LocalDate.now());
        String successMessage="Book Not Found";
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<UpdateBook> requestHttpEntity=new HttpEntity<>(book,headers);

                try {
                    ResponseEntity<String> responseEntity = restTemplate.exchange(
                            url,
                            HttpMethod.PUT,
                            requestHttpEntity,
                            String.class
                    );
                }
            catch(Exception e)
            {
                Assertions.assertEquals(successMessage, e.getMessage());
            }
    }

}
