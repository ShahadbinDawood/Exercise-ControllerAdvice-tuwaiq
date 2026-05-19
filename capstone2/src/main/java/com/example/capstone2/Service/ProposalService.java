package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Contract;
import com.example.capstone2.Model.Project;
import com.example.capstone2.Model.Proposal;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private  final ContractRepository contractRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    public List<Proposal> getAllProposal(){
        return proposalRepository.findAll();
    }
    public void addProposal (Proposal proposal){
        if (proposal == null){
            throw new ApiException("proposal  not found");
        }
        if (userRepository.findUserById(proposal.getFreelancerId())==null){
            throw new ApiException("user  not found");
        }
        if (projectRepository.findProjectById(proposal.getProjectId())==null){
            throw new ApiException("project  not found");
        }
        proposalRepository.save(proposal);
    }
    public void updateProposal (Integer id ,Proposal proposal ){
        Proposal oldProposal = proposalRepository.findProposalById(id);
        if (oldProposal == null){
            throw new ApiException("proposal  not found");
        }
        if (userRepository.findUserById(proposal.getFreelancerId())==null){
            throw new ApiException("user  not found");
        }
        if (projectRepository.findProjectById(proposal.getProjectId())==null){
            throw new ApiException("project  not found");
        }
        oldProposal.setFreelancerId(proposal.getFreelancerId());
        oldProposal.setProjectId(proposal.getProjectId());
        oldProposal.setStatus(proposal.getStatus());
        oldProposal.setBidAmount(proposal.getBidAmount());
        oldProposal.setCoverLetter(proposal.getCoverLetter());
        proposalRepository.save(oldProposal);
    }

    public void deleteProposal(Integer id){
        Proposal proposal = proposalRepository.findProposalById(id);
        if (proposal == null){
            throw new ApiException("proposal  not found");
        }
        proposalRepository.delete(proposal);
    }
    public void applyForProject(Integer freelancerId , Integer projectId ){
        User freelancer = userRepository.findUserById(freelancerId);
        if (freelancer == null){
            throw new ApiException("User  not found");
        }
        Project project = projectRepository.findProjectById(projectId);
        if (project == null){
            throw new ApiException("project  not found");
        }
        if (!freelancer.getRole().equalsIgnoreCase("FREELANCER")){
            throw new ApiException("only freelancer can apply  ");
        }
        if (!project.getStatus().equalsIgnoreCase("OPEN")){
            throw new ApiException("the project not open for proposal  ");
        }
        if (proposalRepository.findProposalByProjectIdAndFreelancerId(projectId, freelancerId)!=null){
            throw new ApiException("Already applied  proposal for the project   ");
        }

        Proposal proposal = new Proposal();
        proposal.setStatus("PENDING");
        proposal.setProjectId(projectId);
        proposal.setFreelancerId(freelancerId);
        proposal.setSubmittedAt(new Date());
        proposal.setBidAmount((int) (freelancerProfileRepository.findFreelancerProfilesByUserId(freelancerId).getHourlyRate()*100));
        proposalRepository.save(proposal);

    }
    //first endpoint
    public void acceptProposal(Integer id ){
        Proposal proposal = proposalRepository.findProposalById(id);
        if (proposal == null){
            throw new ApiException("proposal  not found");
        }
        if (!proposal.getStatus().equalsIgnoreCase("PENDING")){
            throw new ApiException("Proposal is not pending");
        }
        proposal.setStatus("ACCEPTED");
        proposalRepository.save(proposal);

        Project project=projectRepository.findProjectById(proposal.getProjectId());
        project.setStatus("IN_PROGRESS");
        projectRepository.save(project);
        Contract contract = new Contract();
        contract.setProjectId(proposal.getProjectId());
        contract.setStatus("ACTIVE");
        contract.setAgreedAmount(proposal.getBidAmount());
        contract.setClientId(project.getClientId());
        contract.setFreelancerId(proposal.getFreelancerId());
        contract.setStartDate(new Date());
        contractRepository.save(contract);

        List<Proposal> otherProposal = proposalRepository.getProposalsByProjectId(proposal.getProjectId());
        for (Proposal p :otherProposal){
            if (!p.getId().equals(proposal.getId())){
                p.setStatus("REJECTED");
                proposalRepository.save(p);

            }
        }
    }
    public  void rejectProposal (Integer id ,Integer userId){
        Proposal proposal = proposalRepository.findProposalById(id);
        if (proposal == null){
            throw new ApiException("proposal  not found");
        }
        if (!projectRepository.findProjectById(proposal.getProjectId()).getClientId().equals(userId)){
            throw new ApiException("you are not  authorized  ");
        }
        proposal.setStatus("REJECTED");
        proposalRepository.save(proposal);

    }
    public void withdrawProposal(Integer id ,Integer userId){ //i think it replace delet ??
        Proposal proposal = proposalRepository.findProposalById(id);
        if (proposal == null){
            throw new ApiException("proposal  not found");
        }
        if (!proposal.getFreelancerId().equals(userId)){
            throw new ApiException("you are not  authorized  ");
        }
        if (proposal.getStatus().equalsIgnoreCase("PENDING")) {
            proposalRepository.delete(proposal);
            return;
        }
        throw new ApiException("the Proposal have been "+proposal.getStatus()+" already");
    }
    public  List<Proposal> projectProposal (Integer projectId){
        Project project = projectRepository.findProjectById(projectId);
        if (project == null){
            throw new ApiException("project  not found");
        }
        List<Proposal> Proposals = proposalRepository.getProposalsByProjectId(projectId);
        if (Proposals.isEmpty()){
            throw new ApiException("proposal  not found");
        }
        return Proposals;
    }
}
