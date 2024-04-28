package com.Olik.Library_Management.ServiceClassTest.Book_Service_Test;

import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class GetBookByIdTest {

    @Autowired
    private BookImplementation bookImplementation;

    @Test
    public void ReadBook_ByBookId_Success_Message_Test() throws Exception{

         Integer bookId=1;
        // MOck one book object in databse
        AddBookDto addBookDto=new AddBookDto();
        addBookDto.setAuthorId(4); // author id should present in database
        addBookDto.setAvailable(true);
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setIsbn("5639_5754");
        addBookDto.setTitle("Sample_Name");

         String message=bookImplementation.addBooktoDatabase(addBookDto);

         Book book=bookImplementation.getBookById(bookId);

        Assertions.assertEquals(addBookDto.getIsbn(), book.getIsbn());// check isbn
        Assertions.assertEquals(addBookDto.getTitle(), book.getTitle());// check title
        Assertions.assertEquals(addBookDto.getPublicationDate(), book.getPublicationDate()); //check PublicationDate

    }

    @Test
    public void GetBookByBookId_BookNotFoundException_Test() throws Exception{

        Integer bookId=100; // it should not present in your database

        String expectedMessage="Book not found with BookId : "+bookId;

        try{
            Book book=bookImplementation.getBookById(bookId);
        }
        catch (BookNotFoundException e){

            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void GetBookByBookId_IllegalArgumentsException_Test() throws Exception{

        Integer bookId=-1; // Id should not be negative

        String expectedMessage="Invalid ID value. Please provide a valid ID greater than 0.";

        try{
            Book book=bookImplementation.getBookById(bookId);
        }
        catch ( IllegalArgumentException e){

            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }
}
