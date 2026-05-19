package com.example.capstone2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class FreelancerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "User id can not be empty")
    private Integer userId ;
    @NotNull(message = "hourly Rate can not be empty ")
    @PositiveOrZero
    private  double hourlyRate ;
    @NotNull(message = "experience Years  can not be empty ")
    @PositiveOrZero
    private int experienceYears;
    private String portfolio;
}
