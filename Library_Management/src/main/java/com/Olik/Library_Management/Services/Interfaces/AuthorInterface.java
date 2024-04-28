package com.Olik.Library_Management.Services.Interfaces;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;

public interface AuthorInterface {
    String createAuthor(AuthorDto authorDTO) throws Exception, IllegalArgumentException;
    String deleteAuthorById(Integer id) throws AuthorNotFoundException, Exception;
    String updateAuthorById(Integer id, AuthorDto authorDTO) throws AuthorNotFoundException, Exception;
    Author getAuthorById(Integer id) throws AuthorNotFoundException, Exception;
}
