package com.Olik.Library_Management.Transformers;

import com.Olik.Library_Management.DTOs.RequestDtos.AddBookDto;
import com.Olik.Library_Management.DTOs.RequestDtos.AuthorDto;
import com.Olik.Library_Management.Models.Author;
import com.Olik.Library_Management.Models.Book;

import java.io.IOException;

public class AuthorTransoformer {

    public static Author createAuthor(AuthorDto authorDto) throws IOException {
        return Author.builder()
                .authorName(authorDto.getAuthorName())
                .biography(authorDto.getBiography())
                .build();
    }
}
