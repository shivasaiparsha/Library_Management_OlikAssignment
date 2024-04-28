package com.Olik.Library_Management.ServiceClassTest.Author_Service_Test;

import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetAuthorByIdTest {

    @Autowired
    private AuthorImplementation authorImplementation;

    @Test
    public void Author_Service_getAuthor_ById_Success_test() throws Exception {

         String authorName="Dyan Chand";
         String biography="Indian famous poet";
         Integer id=1;
         // i'm storing author first
        AuthorDto authorDto=new AuthorDto(authorName, biography);
        authorImplementation.createAuthor(authorDto);

        Author author =authorImplementation.getAuthorById(id);

        Assertions.assertEquals(authorName, author.getAuthorName());
        Assertions.assertEquals(biography, author.getBiography());
    }

    @Test
    public void Author_Service_getAuthor_ById_Exception_test() throws Exception {

        Integer id=-1;
        String expectedMessage="Invalid ID value. Please provide a valid ID greater than 0.";

        try {
            Author author = authorImplementation.getAuthorById(id);
        }
        catch (IllegalArgumentException e){
            // if author id is negative
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }


}
