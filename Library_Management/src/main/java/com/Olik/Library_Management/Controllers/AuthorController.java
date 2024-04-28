package com.Olik.Library_Management.Controllers;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Services.Implementation.AuthorImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/authors")
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorImplementation authorService;



    // Endpoint to create an author
    @PostMapping("/createAuthor") // Endpoint for creating a new author.
    public ResponseEntity<String> createAuthor(@RequestBody AuthorDto authorDTO) {
        try {
            // Attempt to create the author using the provided AuthorDto.
            String message = authorService.createAuthor(authorDTO);

            // Log success message.
            log.info("Author created successfully");

            // Return success message.
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {   // Handle IllegalArgumentException if any field is empty.

            log.error("Failed to create author", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create author: " + e.getMessage());
        } catch (Exception e) { // Handle any other exceptions.
            // Log and return internal server error message.
            log.error("Failed to create author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create author: " + e.getMessage());
        }
    }



    @DeleteMapping("/DeleteAuthorById/{id}") // Endpoint for deleting an author by ID.
    public ResponseEntity<String> deleteAuthorById(@PathVariable("id") Integer id) {
        try {
            // Attempt to delete the author with the provided ID.
            String message = authorService.deleteAuthorById(id);

            // Log success message.
            log.info("Author with ID {} deleted successfully", id);

            // Return success message.
            return ResponseEntity.ok(message);
        } catch (AuthorNotFoundException e) { // Handle AuthorNotFoundException if author does not exist.
            // Log and return not found message.
            log.error("Author with ID {} not found", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found with id : " + id);
        } catch (Exception e) { // Handle any other exceptions.
            // Log and return internal server error message.
            log.error("Failed to delete author with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete author: " + e.getMessage());
        }
    }



    @PutMapping("/updateAuthor/{id}") // Endpoint for updating an author by ID.
    public ResponseEntity<String> updateAuthorById(@PathVariable("id") Integer id, @RequestBody AuthorDto authorDTO) throws AuthorNotFoundException, Exception {
        try {
            // Attempt to update the author with the provided ID using the new AuthorDto.
            String message = authorService.updateAuthorById(id, authorDTO);

            // Log success message.
            log.info("Author with ID {} updated successfully", id);


            return ResponseEntity.ok(message);
        } catch (AuthorNotFoundException e) { // Handle AuthorNotFoundException if author does not exist.

            log.error("Author with ID {} not found", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) { // Handle any other exceptions.

            log.error("Failed to update author with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update author: " + e.getMessage());
        }
    }


    // Endpoint to get an author by ID
    @GetMapping("/getAuthor/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Integer id) {
        try {
            Author author = authorService.getAuthorById(id); // here we are made a call to the service class
            log.info("Retrieved author with ID {}", id);
            return ResponseEntity.ok(author);
        }
        catch (AuthorNotFoundException e) { // if author with requested id not exist log the error
            log.error("Author with ID {} not found", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) { // other exception like sql exceptions
            log.error("Failed to get author with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

