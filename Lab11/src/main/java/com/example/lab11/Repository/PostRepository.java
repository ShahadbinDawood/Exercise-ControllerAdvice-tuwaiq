package com.example.lab11.Repository;

import com.example.lab11.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Post findPostByPostId(Integer postId);
    List<Post> findPostByUserId(Integer userId);
    List<Post> findPostByCategoryId(Integer categoryId);
    Post findPostByTitle(String title);
    List<Post> findPostsByCategoryIdAndUserId(Integer categoryId, Integer userId);
    List<Post> findPostByPublishDateAfter(Date publishDate);

    int countPostByUserId(Integer userId);
}
