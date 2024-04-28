package com.Olik.Library_Management.ServiceClassTest.Author_Service_Test;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorService_Delete_Method {

    @Autowired
    private AuthorImplementation authorImplementation;


    @Test
    public void Author_Service_Delete_Author_Success_test() throws Exception {
        Integer authorId=8; // make sure that auther id should be exist db

        String returnMessage=authorImplementation.deleteAuthorById(authorId);
        String expectedMessage="Author deleted successfully";

        Assertions.assertEquals(expectedMessage, returnMessage);
    }

    @Test
    public void Author_Service_Delete_Author_AuthorNotFound_test() throws Exception {
        Integer authorId=100;
        String expectedMessage="Author not found with id : "+authorId;

           try {
               String returnMessage=authorImplementation.deleteAuthorById(authorId);
           }
           catch (AuthorNotFoundException e){
               Assertions.assertEquals(expectedMessage, e.getMessage());
           }


    }
}
