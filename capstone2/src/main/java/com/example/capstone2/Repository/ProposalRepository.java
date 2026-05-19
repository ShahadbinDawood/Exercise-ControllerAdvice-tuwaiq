package com.example.capstone2.Repository;

import com.example.capstone2.Model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository  extends JpaRepository<Proposal,Integer> {
    Proposal findProposalById(Integer id);

    List<Proposal> getProposalsByProjectId(Integer projectId);
    Proposal findProposalByProjectIdAndFreelancerId(Integer projectId, Integer freelancerId);
}
