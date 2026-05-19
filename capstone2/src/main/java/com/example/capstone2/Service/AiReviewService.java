package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Project;
import com.example.capstone2.Model.Proposal;
import com.example.capstone2.Model.Review;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.ReviewRepository;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AiReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    public String evaluateUser(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found ");
        }
        List<Review>  userReview= reviewRepository.findReviewByRevieweeId(userId);
        if (userReview.isEmpty()){
            throw new ApiException("NO Review for this User are found ");
        }
        String prompt = buildPrompt(user,userReview);
        return callClaudeApi(prompt);
    }
    private String buildPrompt (User user, List<Review>  userReview){
        StringBuilder sb = new StringBuilder();
        sb.append("You are an expert User evaluator.\n\n");
        sb.append("User Details:\n");
        sb.append("Name :").append(user.getName()).append("\n");
        sb.append("Role :").append(user.getRole()).append("\n");
        sb.append("Bio :").append(user.getBio()).append("\n\n");
        sb.append("The following Users submitted Review:\n\n");

        for (int i = 0; i <userReview.size() ; i++) {
            Review e = userReview.get(i);
            sb.append("Reviewer Id :").append(e.getReviewerId()).append("\n");
            sb.append("Comment :").append(e.getComment()).append("\n");
            sb.append("Rating :").append(e.getRating()).append("\n");
        }

        sb.append("Please evaluate each User and:\n");
        sb.append("1. Give user a score out of 10\n");
        sb.append("2. Explain why in 2-3 sentences\n");
        sb.append("3. Recommend whether you should work with this user or not, with clear reasoning\n");
        sb.append("4. Format your response in a clear, structured way\n");

        return sb.toString() ;
    }
    private  String callClaudeApi(String prompt){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", anthropicApiKey);
        headers.set("anthropic-version", "2023-06-01");

        Map<String , Object> requestBody = Map.of(
                "model", "claude-sonnet-4-5",
                "max_tokens", 1000,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.anthropic.com/v1/messages",
                    request,
                    Map.class
            );


            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey("content")) {
                List<Map<String, Object>> content = (List<Map<String, Object>>) body.get("content");
                if (!content.isEmpty()) {
                    return (String) content.get(0).get("text");
                }
            }
            throw new ApiException("Failed to get response from AI");

        } catch (Exception e) {
            throw new ApiException("AI evaluation failed: " + e.getMessage());
        }
    }

}