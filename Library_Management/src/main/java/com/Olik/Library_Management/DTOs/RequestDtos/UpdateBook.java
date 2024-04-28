package com.Olik.Library_Management.DTOs.RequestDtos;

import jakarta.persistence.Column;
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
public class UpdateBook {

    private String title;
    private boolean isBookAvailable;
    private  String isbn;
    private LocalDate publicationDate;
}
