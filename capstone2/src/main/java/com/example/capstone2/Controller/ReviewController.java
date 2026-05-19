package com.example.capstone2.Controller;

import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.Review;
import com.example.capstone2.Service.AiReviewService;
import com.example.capstone2.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final AiReviewService aiReviewService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllReview() {
        return ResponseEntity.status(200).body(reviewService.getAllReview());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody @Valid Review review) {
        reviewService.addReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody @Valid Review review) {

        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
    @GetMapping("/user-review/{userId}")
    public ResponseEntity<?> getUserReview(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(reviewService.getUserReview(userId));

    }
    @GetMapping("/user-average/{userId}")
    public ResponseEntity<?> averageRating(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(reviewService.averageRating(userId));
    }
    @GetMapping("/evaluate/{userid}")
    public ResponseEntity<?> AIUserEvaluate(@PathVariable Integer userid) {
        return ResponseEntity.status(200).body(aiReviewService.evaluateUser(userid));
    }
}
