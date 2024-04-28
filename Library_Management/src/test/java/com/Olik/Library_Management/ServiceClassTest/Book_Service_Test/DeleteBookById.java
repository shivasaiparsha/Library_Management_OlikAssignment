package com.Olik.Library_Management.ServiceClassTest.Book_Service_Test;

import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteBookById {

    @Autowired
    private BookImplementation bookImplementation;

    @Test
    public void DeleteBookById_Success_Message_test() throws Exception{

        String expectedMessage="book deleted successfully";

        Integer bookId=1; // book should be exist in database

            String actualMessage = bookImplementation.deleteBookById(bookId);

        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void DeleteBookById_BookNotFound_Exception() throws Exception{

        Integer bookId=100;
        String expectedmessage="book not found with bookId : "+bookId;

        try{
            String message=bookImplementation.deleteBookById(bookId);
        } catch (BookNotFoundException e) {
            Assertions.assertEquals(expectedmessage, e.getMessage());
        }
    }

    @Test
    public void DeleteBookById_IllegalArgumentException_Test() throws Exception{

        Integer bookId=100;
        String expectedmessage="Invalid ID value. Please provide a valid ID greater than 0.";

        try{
            String message=bookImplementation.deleteBookById(bookId);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedmessage, e.getMessage());
        }
    }

}
