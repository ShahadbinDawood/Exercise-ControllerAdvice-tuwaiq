package com.example.capstone2.Controller;


import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.Proposal;
import com.example.capstone2.Service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/proposal")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProposal() {
        return ResponseEntity.status(200).body(proposalService.getAllProposal());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProposal(@RequestBody @Valid Proposal proposal) {

        proposalService.addProposal(proposal);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProposal(@PathVariable Integer id, @RequestBody @Valid Proposal proposal) {

        proposalService.updateProposal(id, proposal);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProposal(@PathVariable Integer id) {
        proposalService.deleteProposal(id);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal deleted successfully"));
    }

    @PostMapping("/apply/{freelancerId}/{projectId}")
    public ResponseEntity<?> applyForProject(@PathVariable Integer freelancerId, @PathVariable Integer projectId) {
        proposalService.applyForProject(freelancerId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal  applied successfully"));
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptProposal(@PathVariable Integer id) {
        proposalService.acceptProposal(id);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal   accepted successfully"));
    }

    @PutMapping("/reject/{id}/{userId}")
    public ResponseEntity<?> rejectProposal(@PathVariable Integer id, @PathVariable Integer userId) {
        proposalService.rejectProposal(id, userId);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal  rejected successfully"));
    }

    @PutMapping("/withdraw/{id}/{userId}")
    public ResponseEntity<?> withdrawProposal(@PathVariable Integer id, @PathVariable Integer userId) {
        proposalService.withdrawProposal(id, userId);
        return ResponseEntity.status(200).body(new ApiResponse("Proposal  withdraw successfully"));
    }

    @GetMapping("/proposal-project/{id}")
    public ResponseEntity<?> getProposalsByProject(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(proposalService.projectProposal(id));
    }
}
