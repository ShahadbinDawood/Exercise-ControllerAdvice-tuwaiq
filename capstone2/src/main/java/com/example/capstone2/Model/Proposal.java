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
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @NotNull(message = "project id can not be empty")
    private Integer projectId ;
    @NotNull(message = "freelancer id can not be empty")
    private Integer freelancerId ;
    @Size(min = 50)
    private String coverLetter;
    @NotNull(message = "bid Amount id can not be empty")
    private Integer bidAmount ;
    @NotEmpty(message = "status can not be empty ")
    @Pattern( regexp = "(?i)^(PENDING|ACCEPTED|REJECTED)$")
    @Column(columnDefinition = "varchar(10) not null CHECK(status='PENDING' or status='ACCEPTED' or status='REJECTED' )")
    private String status;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date submittedAt;
}
