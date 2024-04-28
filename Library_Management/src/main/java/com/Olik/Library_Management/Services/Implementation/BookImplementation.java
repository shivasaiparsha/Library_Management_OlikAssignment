package com.Olik.Library_Management.Services.Implementation;

import com.Olik.Library_Management.CustomExeception.*;
import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.DTOs.RequestDtos.UpdateBook;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Models.Book;
import com.Olik.Library_Management.Repositories.AuthorRepository;
import com.Olik.Library_Management.Repositories.BookRepository;
import com.Olik.Library_Management.Services.Interfaces.BookInterface;
import com.Olik.Library_Management.Transformers.BookTransformer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class BookImplementation  implements BookInterface {

    @Autowired
    private AuthorRepository authorRepositary;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBooktoDatabase(AddBookDto addBookDto) throws MissingFieldException, DuplicateIsbnException, AuthorNotFoundException, Exception
    {
        // Check for missing fields in AddBookDto
        if (addBookDto.getTitle() == null || addBookDto.getTitle().isEmpty() ||
                addBookDto.getIsbn() == null || addBookDto.getIsbn().isEmpty() ||
                addBookDto.getPublicationDate() == null ||
                addBookDto.getAuthorId() == null) {
            throw new MissingFieldException("One or more required fields are missing in the book data.");
        }
             // Sql will through Unique constraint exception
            if (bookRepository.findByIsbn(addBookDto.getIsbn()) != null) { // if ISBN already associate with another book
                throw new DuplicateIsbnException("Duplicate ISBN detected");
            }
            // sql will through NOSuchElementException if any id field is null
            if(!bookRepository.existsById(addBookDto.getAuthorId()))
                throw new AuthorNotFoundException("Author not found");
            Optional<Author> optionalAuthor = authorRepositary.findById(addBookDto.getAuthorId());

            Author author = optionalAuthor.get();

            Book book = BookTransformer.getBook(addBookDto); // build the boo object using transformer
            book.setAuthor(author);


            bookRepository.save(book);
            return "book added successfully";

    }

    @Override
    public List<Book> getAllBooks() throws EmptyListException, Exception{

          List<Book> bookList=bookRepository.findAll();
          if(bookList.size()==0) throw new EmptyListException("Empty");
          return bookList;
    }
    @Override
    public Book getBookById(Integer id) throws IllegalArgumentException, BookNotFoundException, Exception {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");
        if(!bookRepository.existsById(id))          // check if book with id  present or not
              throw new BookNotFoundException("Book not found with BookId : "+id);
        Book book=bookRepository.findById(id).get();
        return  book;
    }

    @Override
    public String  deleteBookById(Integer id) throws IllegalArgumentException, BookNotFoundException,Exception {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");
        if(bookRepository.findById(id).isPresent()==false) // check book id exist or not
             throw new BookNotFoundException("book not found with bookId :"+id);
          bookRepository.deleteById(id);
          return "book deleted successfully";
    }

    @Override
    public String updateBook(Integer bookid, UpdateBook updatebook) throws IllegalArgumentException, BookNotFoundException,Exception {
        if (bookid == null || bookid <= 0)
            throw new IllegalArgumentException("Invalid ID value. Please provide a valid ID greater than 0.");
        try {
          if(!bookRepository.existsById(bookid))
              throw new NoSuchElementException("Book Not Found");


        Book book1=bookRepository.findById(bookid).get(); // find by book by the book id

        // updating the book attributes if any of the field not null
         if(updatebook.getTitle()!=null)
             book1.setTitle(updatebook.getTitle());

           if(updatebook.getIsbn()!=null)
              book1.setIsbn(updatebook.getIsbn());

           if(updatebook.getPublicationDate()!=null)
              book1.setPublicationDate(updatebook.getPublicationDate());
           if(book1.isBookAvailable()==false&& updatebook.isBookAvailable()==true)
               throw new IllegalArgumentException("book is not available");
          // set book available to the rent
           if(book1.isBookAvailable()==false)
               book1.setBookAvailable(false);
           else book1.setBookAvailable(true);

          bookRepository.save(book1);
      }
      catch (Exception e){
          throw new BookNotFoundException("Invalid Request "+e.getMessage());
      }

        return "book Updated successfully";
    }
}
