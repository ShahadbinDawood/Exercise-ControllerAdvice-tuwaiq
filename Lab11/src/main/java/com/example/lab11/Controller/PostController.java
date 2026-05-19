package com.example.lab11.Controller;


import com.example.lab11.Api.ApiResponse;
import com.example.lab11.Model.Post;
import com.example.lab11.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPost() {
        return ResponseEntity.status(200).body(postService.getAllPost());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody @Valid Post post) {

        postService.addPost(post);
        return ResponseEntity.status(200).body(new ApiResponse("Post added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @RequestBody @Valid Post post) {

        postService.updatePost(id, post);
        return ResponseEntity.status(200).body(new ApiResponse("Post updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.status(200).body(new ApiResponse("Post deleted successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(postService.findPostByUserId(userId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getPostsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.status(200).body(postService.findPostByCategoryId(categoryId));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getPostByTitle(@PathVariable String title) {
        return ResponseEntity.status(200).body(postService.findPostByTitle(title));
    }

    @GetMapping("/filter/{categoryId}/{userId}")
    public ResponseEntity<?> getPostsByCategoryAndUser(@PathVariable Integer categoryId, @PathVariable Integer userId) {
        return ResponseEntity.status(200).body(postService.findPostByCategoryIdAndUserId(categoryId, userId));
    }

    @GetMapping("/after/{date}")
    public ResponseEntity<?> getPostsAfterDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.status(200).body(postService.findPostAfterDate(date));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<?> countPostsByUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(postService.countPostByUserId(userId));
    }

}
