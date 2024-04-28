package com.Olik.Library_Management.DTOs.RequestDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalrequestDto {

    private String renterName;
    private Integer bookId;
    private LocalDate returnDate;
}
