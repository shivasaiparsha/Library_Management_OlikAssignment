package com.Olik.Library_Management.Services.Implementation;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Repositories.AuthorRepository;
import com.Olik.Library_Management.Services.Interfaces.AuthorInterface;
import com.Olik.Library_Management.Transformers.AuthorTransoformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthorImplementation implements AuthorInterface {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public String createAuthor(AuthorDto authorDTO) throws IllegalArgumentException, Exception {

        // Check if essential fields are null or empty
        if (authorDTO.getAuthorName() == null || authorDTO.getAuthorName().isEmpty() ||
                authorDTO.getBiography() == null || authorDTO.getBiography().isEmpty()) {
            throw new IllegalArgumentException("Author name and biography must not be null or empty.");
        }

        Author author = AuthorTransoformer.createAuthor(authorDTO);
        authorRepository.save(author);
        return "Author created successfully";
    }

    @Override
    public String deleteAuthorById(Integer id) throws AuthorNotFoundException, Exception {

        if (id == null || id <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");

        if (!authorRepository.existsById(id))
            throw new AuthorNotFoundException("Author not found with id : " + id);
        Author author = authorRepository.findById(id).get();
        authorRepository.delete(author);
        return "Author deleted successfully";
    }

    @Override
    public String updateAuthorById(Integer id, AuthorDto authorDTO) throws AuthorNotFoundException, Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");
        }
        boolean authorPresent = authorRepository.existsById(id);

        if (!authorPresent) // if author not exist
            throw new AuthorNotFoundException("Author not found with id : " + id);
        Author author = authorRepository.findById(id).get();

        if (authorDTO.getAuthorName() != null) {
            author.setAuthorName(authorDTO.getAuthorName());
        }
        if (authorDTO.getBiography() != null)
            author.setBiography(authorDTO.getBiography());
        authorRepository.save(author);
        return "Author updated successfully";
    }

    @Override
    public Author getAuthorById(Integer id) throws AuthorNotFoundException, Exception {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");

        try {
            Author author = authorRepository.findById(id).get();
            if (author == null) // if author not present in db
                throw new AuthorNotFoundException("Author not available with id :" + id);
            return author;
        } catch (NoSuchElementException e) { // sql will through NoSuchElement Exception
            throw new AuthorNotFoundException("Author not available with id :" + id);
        }
    }
}

