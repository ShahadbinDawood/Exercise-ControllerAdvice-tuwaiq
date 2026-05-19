package com.example.lab11.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @NotNull(message = "user id can not be empty")
    private Integer userId;
    @NotNull(message = "post id can not be empty")
    private Integer postId;
    @NotEmpty(message = "content can not be empty ")
    private String content;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date commentDate ;

}
