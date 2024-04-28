package com.Olik.Library_Management.ServiceClassTest.Author_Service_Test;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateAuthorServiceTest {

    @Autowired
    private AuthorImplementation authorImplementation;

    @Test
    public void Update_Author_IdNotNull_Exception_Test() throws Exception {
        AuthorDto authorDto=new AuthorDto();
        Integer authorId=-1;

        String expectedMessage="Invalid ID value. Please provide a valid ID greater than 0.";

         try {
             String actualMessage = authorImplementation.updateAuthorById(authorId, authorDto);
         }
         catch (IllegalArgumentException e){

             Assertions.assertEquals(expectedMessage, e.getMessage());
         }
    }

    @Test
    public void Update_Author__AuthorNotFound_Test() throws Exception {
        AuthorDto authorDto=new AuthorDto();
        Integer authorId=100;

        String expectedMessage="Author not found with id : "+authorId;

        try {
            String actualMessage = authorImplementation.updateAuthorById(authorId, authorDto);
        }
        catch (AuthorNotFoundException e){

            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public   void Update_Author_Sucess_Message_Test() throws Exception{

        AuthorDto authorDto=new AuthorDto();
        Integer authorId=10;

        String expectedMessage="Author updated successfully";;


            String actualMessage = authorImplementation.updateAuthorById(authorId, authorDto);

           Assertions.assertEquals(expectedMessage, actualMessage);
    }



}
