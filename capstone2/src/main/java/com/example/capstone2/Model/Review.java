package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "contract id can not be empty")
    private Integer contractId ;
    @NotNull(message = "reviewer id can not be empty")
    private Integer reviewerId ;
    @NotNull(message = "reviewee id can not be empty")
    private Integer revieweeId;
    @NotEmpty(message = "comment can not be empty ")
    @Size(min = 15)
    private String comment;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date createdAt;
    @NotNull(message = "rating can not be empty")
    @Min(1) @Max(5)
    private Integer rating;
}
