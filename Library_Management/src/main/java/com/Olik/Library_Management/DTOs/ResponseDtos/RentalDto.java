package com.Olik.Library_Management.DTOs.ResponseDtos;

import com.Olik.Library_Management.Models.Book;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalDto {

    private Integer rentalId;
    private String renterName;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private String bookTitle;
}
