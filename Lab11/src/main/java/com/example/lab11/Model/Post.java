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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @NotNull(message = "category Id can not be empty")
    private Integer categoryId;
    @NotEmpty(message = "title can not be empty ")
    @Column(columnDefinition = "varchar(25) not null ")
    private String title;
    @NotEmpty(message = "content can not be empty ")
    private String content;
    @NotNull(message = "user id can not be empty")
    private Integer userId;
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private Date publishDate;

}
