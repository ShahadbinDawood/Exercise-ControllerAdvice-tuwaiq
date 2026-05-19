package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Project;
import com.example.capstone2.Model.Proposal;
import com.example.capstone2.Repository.ProjectRepository;
import com.example.capstone2.Repository.ProposalRepository;
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
public class AiProposalService {
    private final ProjectRepository projectRepository;
    private final ProposalRepository proposalRepository;

    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    public String evaluateProposals(Integer projectId){
        Project project = projectRepository.findProjectById(projectId);
        if (project==null){
            throw new ApiException("project not found ");
        }
        List<Proposal>  projectProposal = proposalRepository.getProposalsByProjectId(projectId);
        if (projectProposal.isEmpty()){
            throw new ApiException("NO Proposal for this project are found ");
        }
        String prompt = buildPrompt(project,projectProposal);
        return callClaudeApi(prompt);
    }
    private String buildPrompt (Project project,List<Proposal>  projectProposal){
        StringBuilder sb = new StringBuilder();
        sb.append("You are an expert freelance project evaluator.\n\n");
        sb.append("Project Details:\n");
        sb.append("Title :").append(project.getTitle()).append("\n");
        sb.append("Budget :").append(project.getBudget()).append("\n");
        sb.append("Deadline :").append(project.getDeadline()).append("\n");
        sb.append("Description :").append(project.getDescription()).append("\n\n");
        sb.append("The following freelancers submitted proposals:\n\n");

        for (int i = 0; i <projectProposal.size() ; i++) {
            Proposal p = projectProposal.get(i);
            sb.append("FreelancerId :").append(p.getFreelancerId()).append("\n");
            sb.append("Bid Amount :").append(p.getBidAmount()).append("\n");
            sb.append("Cover Letter :").append(p.getCoverLetter()).append("\n");
        }

        sb.append("Please evaluate each proposal and:\n");
        sb.append("1. Give each proposal a score out of 10\n");
        sb.append("2. Explain why in 2-3 sentences\n");
        sb.append("3. Recommend the BEST proposal with clear reasoning\n");
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
