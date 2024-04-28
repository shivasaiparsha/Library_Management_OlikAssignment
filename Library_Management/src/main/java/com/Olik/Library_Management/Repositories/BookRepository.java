package com.Olik.Library_Management.Repositories;

import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book, Integer> {
    Optional <Book> findByIsbn(String isbn);
}
