package com.example.lab11.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotEmpty(message = "User Name can not be empty ")
    @Column(columnDefinition = "varchar(15) not null ")
    private String UserName ;
    @NotEmpty(message = "Email can not be empty ")
    @Email
    @Column(columnDefinition = "varchar(35) unique ")
    private String email ;
    @NotEmpty(message = "password can not be empty ")
    @Column(columnDefinition = "varchar(25) not null ")
    private String password ;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date registrationDate ;
}
