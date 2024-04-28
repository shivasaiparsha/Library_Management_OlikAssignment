package com.Olik.Library_Management.Transformers;


import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.Models.Book;

import java.io.IOException;

public class BookTransformer {

    public static Book getBook(AddBookDto addBookDto) throws IOException {
        return Book.builder()
                .title(addBookDto.getTitle())
                .isbn(addBookDto.getIsbn())
                .publicationDate(addBookDto.getPublicationDate())
                .isBookAvailable(addBookDto.isAvailable())
                .build();
    }
}
