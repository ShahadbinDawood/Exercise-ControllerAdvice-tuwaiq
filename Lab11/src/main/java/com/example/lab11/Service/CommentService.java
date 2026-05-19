package com.example.lab11.Service;

import com.example.lab11.Api.ApiException;
import com.example.lab11.Model.Comment;
import com.example.lab11.Repository.CommentRepository;
import com.example.lab11.Repository.PostRepository;
import com.example.lab11.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository   userRepository;
    private final PostRepository postRepository;
    public List<Comment> getAllComment(){
        return commentRepository.findAll();
    }
    public void addComment(Comment comment){
        if (comment == null) {
            throw new ApiException("comment not found");
        }
        if (userRepository.findUserByUserId(comment.getUserId()) == null) {
            throw new ApiException("user not found");
        }
        if (postRepository.findPostByPostId(comment.getPostId()) == null) {
            throw new ApiException("post not found");
        }
        commentRepository.save(comment);
    }
    public void updateComment(Integer id , Comment comment){
        Comment oldComment = commentRepository.findCommentByCommentId(id);
        if (oldComment == null) {
            throw new ApiException("comment not found");
        }
        if (userRepository.findUserByUserId(comment.getUserId()) == null) {
            throw new ApiException("user not found");
        }
        if (postRepository.findPostByPostId(comment.getPostId()) == null) {
            throw new ApiException("post not found");
        }
        oldComment.setUserId(comment.getUserId());
        oldComment.setPostId(comment.getPostId());
        oldComment.setContent(comment.getContent());
        oldComment.setCommentDate(comment.getCommentDate());
        commentRepository.save(oldComment);
    }
    public void deleteComment(Integer id ){
        Comment comment = commentRepository.findCommentByCommentId(id);
        if (comment == null) {
            throw new ApiException("comment not found");
        }
        commentRepository.delete(comment);
    }
    public List<Comment> findCommentByPostId(Integer id){
        List<Comment> comment = commentRepository.findCommentByPostId(id);
        if (comment.isEmpty()) {
            throw new ApiException("comment not found");
        }
        return comment;
    }
    public List<Comment> findCommentByUserId(Integer id){
        if (userRepository.findUserByUserId(id) == null) {
            throw new ApiException("User not found");
        }
        List<Comment> comment = commentRepository.findCommentByUserId(id);
        if (comment.isEmpty()) {
            throw new ApiException("comment not found");
        }
        return comment;
    }

}
