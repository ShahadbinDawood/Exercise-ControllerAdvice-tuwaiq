package com.example.capstone2.Controller;

import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.FreelancerProfile;
import com.example.capstone2.Service.FreelancerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/freelancer-profile")
@RequiredArgsConstructor
public class FreelancerProfileController {
    private final FreelancerProfileService freelancerProfileService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllFreelancerProfile() {
        return ResponseEntity.status(200).body(freelancerProfileService.getAllFreelancerProfile());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFreelancerProfile(@RequestBody @Valid FreelancerProfile freelancerProfile) {

        freelancerProfileService.addFreelancerProfile(freelancerProfile);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Profile added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFreelancerProfile(@PathVariable Integer id, @RequestBody @Valid FreelancerProfile freelancerProfile) {

        freelancerProfileService.updateFreelancerProfile(id, freelancerProfile);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Profile updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFreelancerProfile(@PathVariable Integer id) {
        freelancerProfileService.deleteFreelancerProfile(id);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Profile deleted successfully"));
    }
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<?> freelancerWithSkill (@PathVariable Integer skillId){
        return ResponseEntity.status(200).body(freelancerProfileService.freelancerWithSkill(skillId));

    }
    @GetMapping("/rate-lower/{hourlyRate}")
    public ResponseEntity<?> profileHourlyRateLower(@PathVariable double hourlyRate){
        return ResponseEntity.status(200).body(freelancerProfileService.profileHourlyRateLower(hourlyRate));

    }
}
