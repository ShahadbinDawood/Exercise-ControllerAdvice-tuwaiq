package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotEmpty(message = "name can not be empty ")
    @Size(min = 4)
    @Pattern(regexp = "^[A-Za-z]+$")
    @Column(columnDefinition = "varchar(15) not null CONSTRAINT name CHECK (name NOT LIKE '%[^A-Za-z]%')")
    private String name ;
    @NotEmpty(message = "email can not be empty ")
    @Email
    @Column(columnDefinition = "varchar(45) not null unique")
    private String email ;
    @NotEmpty(message = "phoneNumber can not be empty ")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber ;
    @NotEmpty(message = "password can not be empty ")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private  String password ;
    @NotEmpty(message = "role can not be empty ")
    @Pattern( regexp = "(?i)^(CLIENT|FREELANCER)$")
    @Column(columnDefinition = "varchar(10) not null CHECK(role='CLIENT' or role='FREELANCER')")
    private String role;
    @Size(min = 15)
    private String bio;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date registrationDate;

}
