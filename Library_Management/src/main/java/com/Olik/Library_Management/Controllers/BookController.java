package com.Olik.Library_Management.Controllers;


import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.CustomExeception.MissingFieldException;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.DTOs.RequestDtos.UpdateBook;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Services.Implementation.BookImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

    @Autowired
    private BookImplementation bookService;

    @PostMapping("/addBook") // Endpoint for adding a new book.
    public ResponseEntity<String> addBook(@RequestBody AddBookDto addBookDto) {
        try {
            // Attempt to add the book to the database using the provided AddBookDto.
            bookService.addBooktoDatabase(addBookDto);

            // Log success message.
            log.info("Book added successfully with ISBN: {}", addBookDto.getIsbn());

            // Return success message with HTTP status 200 (OK).
            return new ResponseEntity<>("Book added Successfully", HttpStatus.OK);
        }
     catch (MissingFieldException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AuthorNotFoundException e) {
            // Handle AuthorNotFoundException if the author specified in the AddBookDto is not found.
            // Return error message with HTTP status 400 (BAD REQUEST).
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle any other exceptions. sql exception, data base connection lost exception
            // Log error message.
            log.error("Error adding book: {}", e.getMessage());

            // Return error message with HTTP status 400 (BAD REQUEST).
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks() {
        try {
            // Retrieve all books from the service layer.
            List<Book> books = bookService.getAllBooks();

            // Log success message.
            log.info("Retrieved all books successfully");

            // Return the list of books with HTTP status 200 (OK).
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        catch (Exception e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if an exception occurs.
            log.error("Error retrieving all books: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getBookByBookId/{bookid}")
    public ResponseEntity<?> getBookById(@PathVariable Integer bookid) {
        try {
            // Retrieve the book with the specified ID from the service layer.
            Book book = bookService.getBookById(bookid);

            // Log success message.
            log.info("Retrieved book with ID: {}", bookid);

            // Return the book with HTTP status 200 (OK).
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (BookNotFoundException e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if the book is not found.
            log.error("Book not found with ID: {}", bookid);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if an exception occurs.
            log.error("Error retrieving book with ID: {}", bookid, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UpdateBook/{bookid}")
    public ResponseEntity<?> updateBook(@PathVariable Integer bookid, @RequestBody UpdateBook book) {
        try {
            // Update the book with the specified ID using the provided details.
            String message = bookService.updateBook(bookid, book);


            log.info("Updated book with ID: {}", bookid);

            // Return success message with HTTP status 200 (OK).
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if the book is not found.
            log.error("Book not found with ID: {}", bookid);
            return new ResponseEntity<>("Book Not Found", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if an exception occurs.
            log.error("Error updating book with ID: {}", bookid, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteBookById/{bookid}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookid) {
        try {
            // Delete the book with the specified ID.
            String message = bookService.deleteBookById(bookid);


            log.info("Deleted book with ID: {}", bookid);

            // Return success message with HTTP status 200 (OK).
            return new ResponseEntity<>(message, HttpStatus.OK);
            //MissingFieldException
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (BookNotFoundException e) {
            // Log and return error message with HTTP status 400 (BAD REQUEST) if the book is not found.
            log.error("Book not found with ID: {}", bookid);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log and throw a RuntimeException if an exception occurs.
            log.error("Error deleting book with ID: {}", bookid, e);
            throw new RuntimeException(e);
        }
    }

}
