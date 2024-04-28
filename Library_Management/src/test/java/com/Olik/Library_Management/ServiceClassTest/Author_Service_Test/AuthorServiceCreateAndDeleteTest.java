package com.Olik.Library_Management.ServiceClassTest.Author_Service_Test;

import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorServiceCreateAndDeleteTest {

    @Autowired
    private AuthorImplementation authorImplementation;



    @Test
    public void AuthorServiceClass_Author_Created_Method_Success_Message_Test() throws Exception {
        // create one mock user
        AuthorDto auther=new AuthorDto();
        auther.setAuthorName("Kabir das");
        auther.setBiography("He is an Indian poet");

        // let'sstores the user in the database using service calss
        String returnMessage=authorImplementation.createAuthor(auther);
        String expectedMessage="Author created successfully";
        Assertions.assertEquals(expectedMessage, returnMessage);
    }

    @Test
    public void AuthorServiceClass_Author_Created_Method_Exception_Test() throws Exception {

        AuthorDto auther=new AuthorDto();
        String expectedMessage="Author name and biography must not be null or empty.";
          try{

              authorImplementation.createAuthor(auther);
          }
          catch (IllegalArgumentException e)
          {
              Assertions.assertEquals(expectedMessage, e.getMessage());
          }

          AuthorDto auther2=new AuthorDto();
          auther2.setAuthorName("");
          auther2.setBiography("");

        try{

            authorImplementation.createAuthor(auther);
        }
        catch (IllegalArgumentException e)
        {
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }


    }
    @Test
    public void AuthorServiceClass_Author_Created_Method_EmptyField_Exception_Test() throws Exception {

        AuthorDto auther=new AuthorDto();
        auther.setAuthorName("");
        auther.setBiography("");

        String expectedMessage="Author name and biography must not be null or empty.";

        try{

            authorImplementation.createAuthor(auther);
        }
        catch (IllegalArgumentException e)
        {
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }

    }

}
