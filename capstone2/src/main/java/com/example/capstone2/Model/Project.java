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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "client id can not be empty")
    private Integer clientId;
    @NotEmpty(message = "title can not be empty ")
    @Size(min = 4)
    private String title;
    @NotEmpty(message = "description can not be empty ")
    @Size(min = 15)
    private String description;
    @NotNull(message = "budget can not be empty")
    @PositiveOrZero
    private int budget;
    @NotEmpty(message = "status can not be empty ")
    @Pattern( regexp = "(?i)^(OPEN|IN_PROGRESS|COMPLETED|CANCELLED)$")
    @Column(columnDefinition = "varchar(15) not null CHECK(status='OPEN' or status='IN_PROGRESS' or status='COMPLETED' or status='CANCELLED' )")
    private String status;
    @Future
    private Date deadline;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date createdAt;
}
