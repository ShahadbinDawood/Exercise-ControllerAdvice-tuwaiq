package com.example.capstone2.Controller;

import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.Project;
import com.example.capstone2.Service.AiProposalService;
import com.example.capstone2.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final AiProposalService aiProposalService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllProject() {
        return ResponseEntity.status(200).body(projectService.getAllProject());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody @Valid Project project) {

        projectService.addProject(project);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody @Valid Project project) {

        projectService.updateProject(id, project);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }
    @GetMapping("/open")
    public ResponseEntity<?> getOpenProject(){
        return ResponseEntity.status(200).body(projectService.getOpenProject());
    }
    @GetMapping("/budget-range/{min}/{max}")
    public ResponseEntity<?> budgetRange(@PathVariable int min ,@PathVariable int  max ){
        return ResponseEntity.status(200).body(projectService.budgetRange(min,max));
    }
    @PutMapping("/cancel/{projectId}/{clientId}")
    public ResponseEntity<?> cancelProject (@PathVariable Integer projectId , @PathVariable Integer clientId){
        projectService.cancelProject(projectId, clientId);
        return ResponseEntity.status(200).body(new ApiResponse("project canceled successfully"));

    }
    @GetMapping("/evaluate-proposals/{projectId}")
    public ResponseEntity<?> evaluateProposals(@PathVariable Integer projectId ){
        return ResponseEntity.status(200).body(aiProposalService.evaluateProposals(projectId));
    }
}
