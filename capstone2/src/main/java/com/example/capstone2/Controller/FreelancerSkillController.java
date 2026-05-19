package com.example.capstone2.Controller;

import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.FreelancerSkill;
import com.example.capstone2.Service.FreelancerSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/freelancer-skill")
@RequiredArgsConstructor
public class FreelancerSkillController {
    private final FreelancerSkillService freelancerSkillService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllFreelancerSkill() {
        return ResponseEntity.status(200).body(freelancerSkillService.getAllFreelancerSkill());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFreelancerSkill(@RequestBody @Valid FreelancerSkill freelancerSkill) {

        freelancerSkillService.addFreelancerSkill(freelancerSkill);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Skill added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFreelancerSkill(@PathVariable Integer id, @RequestBody @Valid FreelancerSkill freelancerSkill) {

        freelancerSkillService.updateFreelancerSkill(id, freelancerSkill);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Skill updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFreelancerSkill(@PathVariable Integer id) {
        freelancerSkillService.deleteFreelancerSkill(id);
        return ResponseEntity.status(200).body(new ApiResponse("Freelancer Skill deleted successfully"));
    }

}
