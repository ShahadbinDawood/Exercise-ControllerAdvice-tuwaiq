package com.example.lab11.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @NotEmpty(message = "Name can not be empty ")
    @Pattern( regexp = "(?i)^(FOOD|TRAVEL|HEALTH|FASHION)$")
    @Column(columnDefinition = "varchar(15) not null  check(name ='FOOD' or name ='TRAVEL' or name ='HEALTH' or name ='FASHION'   )")
    private String name ;
}
