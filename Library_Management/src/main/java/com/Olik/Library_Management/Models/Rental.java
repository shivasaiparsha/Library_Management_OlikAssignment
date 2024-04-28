package com.Olik.Library_Management.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String renterName;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Book book;
}
