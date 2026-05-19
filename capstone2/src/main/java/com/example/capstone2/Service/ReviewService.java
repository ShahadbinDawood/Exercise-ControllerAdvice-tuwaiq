package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Review;
import com.example.capstone2.Repository.ContractRepository;
import com.example.capstone2.Repository.ReviewRepository;
import com.example.capstone2.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }
    public void addReview (Review review){
        if (review == null){
            throw new ApiException("Review  not found");
        }
        if (userRepository.findUserById(review.getReviewerId())==null){
            throw new ApiException("Reviewer user not found");
        }
        if (userRepository.findUserById(review.getRevieweeId())==null){
            throw new ApiException("Reviewed user not found");
        }
        if(contractRepository.findContractById(review.getContractId())==null){
            throw new ApiException("Contract not found");
        }
        reviewRepository.save(review);
    }

    public void updateReview (Integer id , Review review){
        Review oldReview = reviewRepository.findReviewById(id);
        if (oldReview == null){
            throw new ApiException("Review  not found");
        }
        if (userRepository.findUserById(review.getReviewerId())==null){
            throw new ApiException("Reviewer user not found");
        }
        if (userRepository.findUserById(review.getRevieweeId())==null){
            throw new ApiException("Reviewed user not found");
        }
        if(contractRepository.findContractById(review.getContractId())==null){
            throw new ApiException("Contract not found");
        }
        oldReview.setContractId(review.getContractId());
        oldReview.setReviewerId(review.getReviewerId());
        oldReview.setRevieweeId(review.getRevieweeId());
        oldReview.setComment(review.getComment());
        oldReview.setRating(review.getRating());
        reviewRepository.save(oldReview);

    }
    public void deleteReview (Integer id ){
        Review review = reviewRepository.findReviewById(id);
        if (review == null){
            throw new ApiException("Review  not found");
        }
        reviewRepository.delete(review);
    }
    public List<Review> getUserReview (Integer userId){
        List<Review> review = reviewRepository.findReviewByRevieweeId(userId);
        if (userRepository.findUserById(userId) == null) {
            throw new ApiException("User not found");
        }
        if (review.isEmpty()){
            throw new ApiException("no Reviews are found");
        }
        return review ;

    }
    public Double averageRating(Integer userId){

        if (userRepository.findUserById(userId) == null) {
            throw new ApiException("User not found");
        }
        List<Review> review = reviewRepository.findReviewByRevieweeId(userId);
        if (review.isEmpty()){
            throw new ApiException("no Reviews are found");
        }
        int count = review.size();
        double sum =0;
        for (Review r :review) sum = r.getRating() + sum;
        return (sum/count);
    }
}
