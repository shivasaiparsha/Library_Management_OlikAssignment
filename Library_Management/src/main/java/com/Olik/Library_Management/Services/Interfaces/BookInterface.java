package com.Olik.Library_Management.Services.Interfaces;

import com.Olik.Library_Management.CustomExeception.AuthorNotFoundException;
import com.Olik.Library_Management.CustomExeception.BookNotFoundException;
import com.Olik.Library_Management.CustomExeception.DuplicateIsbnException;
import com.Olik.Library_Management.CustomExeception.EmptyListException;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.DTOs.RequestDtos.UpdateBook;
import com.Olik.Library_Management.Models.Book;

import java.util.List;

public interface BookInterface {
    public String addBooktoDatabase(AddBookDto addBookDto) throws DuplicateIsbnException, AuthorNotFoundException, Exception;
    public List<Book> getAllBooks() throws EmptyListException, Exception;
    public Book getBookById(Integer id) throws BookNotFoundException, Exception, IllegalArgumentException;
    public String  deleteBookById(Integer id) throws BookNotFoundException,Exception, IllegalArgumentException;
    public String updateBook(Integer bookid, UpdateBook updatebook) throws BookNotFoundException,Exception, IllegalArgumentException;

}
