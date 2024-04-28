package com.Olik.Library_Management.ServiceClassTest.Book_Service_Test;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.CustomExeception.DuplicateIsbnException;
import com.Olik.Library_Management.CustomExeception.MissingFieldException;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AddBookToDbException {

    @Autowired
    private BookImplementation bookImplementation;

    @Test
    public void Book_Service_Success_Message_test() throws Exception {

        String message="book added successfully";

        AddBookDto addBookDto=new AddBookDto();
        addBookDto.setAuthorId(4); // author id should present in database
        addBookDto.setAvailable(true);
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setIsbn("5639_5754_2942");
        addBookDto.setTitle("Adhyapak");

         String actualMessage=bookImplementation.addBooktoDatabase(addBookDto);
        Assertions.assertEquals(message, actualMessage);
    }

    @Test
    public void Book_Service_Author_AlreadyExist_ById_Test() throws Exception {


        String message="Author not found";

        AddBookDto addBookDto=new AddBookDto();
        addBookDto.setAuthorId(4); // author id should present in database
        addBookDto.setAvailable(true);
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setIsbn("5639_5754");
        addBookDto.setTitle("Adhyapak");

        try {
            String actualMessage = bookImplementation.addBooktoDatabase(addBookDto);
        }
        catch (AuthorNotFoundException e) {
            Assertions.assertEquals(message, e.getMessage());
        }

    }

    @Test
    public void Book_Service_ISB_AlreadyExistInDB_Test() throws Exception{

        // ISBN ID should by exist in db
        String message="Duplicate ISBN detected";

        AddBookDto addBookDto=new AddBookDto();
        addBookDto.setAuthorId(4); // author id should present in database
        addBookDto.setAvailable(true);
        addBookDto.setPublicationDate(LocalDate.now());
        addBookDto.setIsbn("5639_5754");
        addBookDto.setTitle("Adhyapak");

        try {
            String actualMessage = bookImplementation.addBooktoDatabase(addBookDto);
        }
        catch (DuplicateIsbnException e) {
            Assertions.assertEquals(message, e.getMessage());
        }

    }


}
