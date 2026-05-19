package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "project id can not be empty")
    private Integer projectId ;
    @NotNull(message = "freelancer id can not be empty")
    private Integer freelancerId ;
    @NotNull(message = "client id can not be empty")
    private Integer clientId;
    @NotNull(message = "agreed Amount can not be empty")
    private int  agreedAmount ;
    @NotEmpty(message = "status can not be empty ")
    @Pattern( regexp = "(?i)^(ACTIVE|COMPLETED|CANCELLED)$")
    @Column(columnDefinition = "varchar(10) not null CHECK(status='ACTIVE' or status='COMPLETED' or status='CANCELLED' )")
    private String status;
    private Date startDate;
    private Date endDate;
}
