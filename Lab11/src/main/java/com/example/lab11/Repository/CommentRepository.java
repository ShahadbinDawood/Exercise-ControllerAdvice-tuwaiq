package com.example.lab11.Repository;

import com.example.lab11.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment ,Integer> {
    Comment findCommentByCommentId(Integer commentId);

    List<Comment> findCommentByPostId(Integer postId);
    List<Comment> findCommentByUserId(Integer userId);

}
