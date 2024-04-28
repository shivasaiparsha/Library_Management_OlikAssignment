package com.Olik.Library_Management.DTOs.RequestDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddBookDto {

    private String title;
    private boolean isAvailable;
    private String isbn;
    private LocalDate publicationDate;
    private Integer authorId;
}
