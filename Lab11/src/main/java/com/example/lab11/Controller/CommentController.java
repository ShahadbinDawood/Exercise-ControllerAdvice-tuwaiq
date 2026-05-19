package com.example.lab11.Controller;


import com.example.lab11.Api.ApiResponse;
import com.example.lab11.Model.Comment;
import com.example.lab11.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllComment() {
        return ResponseEntity.status(200).body(commentService.getAllComment());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody @Valid Comment comment) {

        commentService.addComment(comment);
        return ResponseEntity.status(200).body(new ApiResponse("Comment added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody @Valid Comment comment) {

        commentService.updateComment(id, comment);
        return ResponseEntity.status(200).body(new ApiResponse("Comment updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(200).body(new ApiResponse("Comment deleted successfully"));
    }
    @GetMapping("/post_comment/{id}")
    public ResponseEntity<?> findCommentByPostId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(commentService.findCommentByPostId(id));
    }
    @GetMapping("/user_comment/{id}")
    public ResponseEntity<?> findCommentByUserId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(commentService.findCommentByUserId(id));
    }


}
