package com.example.capstone2.Repository;

import com.example.capstone2.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
    Contract findContractById(Integer id);
    List<Contract> findContractByFreelancerId(Integer freelancerId);
    List<Contract> findContractByClientId(Integer clientId);
    Contract findContractByProjectId(Integer projectId);
}
