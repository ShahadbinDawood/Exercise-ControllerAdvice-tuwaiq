package com.example.capstone2.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "contract id can not be empty")
    private Integer contractId ;
    @NotNull(message = "freelancer id can not be empty")
    private Integer freelancerId ;
    @NotNull(message = "client id can not be empty")
    private Integer clientId;
    @NotNull(message = "amount  can not be empty")
    @PositiveOrZero
    private Double amount ;
    @NotEmpty(message = "status can not be empty ")
    @Pattern( regexp = "(?i)^(PENDING|COMPLETED|FAILED|REFUNDED)$")
    @Column(columnDefinition = "varchar(10) not null CHECK(status='PENDING' or status='COMPLETED' or status='FAILED' or status='REFUNDED')")
    private String status;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date paymentAt;
    //?
    private String invoiceNumber ;
}
