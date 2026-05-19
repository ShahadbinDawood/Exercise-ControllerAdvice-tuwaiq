package com.example.capstone2.Service;


import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Contract;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.ContractRepository;
import com.example.capstone2.Repository.ProjectRepository;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ContractPdfService contractPdfService;
    private final WhatsAppService whatsAppService;
    private final EmailService emailService;
    public List<Contract>  getAllContract (){
        return contractRepository.findAll();
    }

    public  Contract getContractById(Integer id){
        Contract contract=contractRepository.findContractById(id);
        if (contract==null){
            throw new ApiException("Contract  not  found");
        }
        return contract;
    }
    public List<Contract> findContractByFreelancerId(Integer FreelancerId){
        List<Contract> contracts=contractRepository.findContractByFreelancerId(FreelancerId);
        if (contracts==null){
            throw new ApiException("Contract  not  found");
        }
        return contracts;
    }
    public List<Contract> findContractByClientId(Integer ClientId){
        List<Contract> contracts=contractRepository.findContractByClientId(ClientId);
        if (contracts==null){
            throw new ApiException("Contract  not  found");
        }
        return contracts;
    }
    public  Contract findContractByProjectId(Integer ProjectId){
        Contract contract=contractRepository.findContractByProjectId(ProjectId);
        if (contract==null){
            throw new ApiException("Contract  not  found");
        }
        return contract;
    }
    public void completeContract(Integer contractId){
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) {
            throw new ApiException("Contract not found");
        }
        if (!contract.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new ApiException("Only ACTIVE contracts can be completed");
        }

        contract.setStatus("COMPLETED");
        contract.setEndDate(new Date());
        contractRepository.save(contract);

        var project = projectRepository.findProjectById(contract.getProjectId());
        if (project != null) {
            project.setStatus("COMPLETED");
            projectRepository.save(project);
        }

    }
    public void sendContract(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) throw new ApiException("Contract not found");

        User client = userRepository.findUserById(contract.getClientId());
        if (client == null) throw new ApiException("Client not found");

        // Generate PDF
        byte[] pdf = contractPdfService.generateContractPdf(contract);

        // Send Email
        emailService.sendContractByEmail(client.getEmail(), pdf, contractId);

        // Send WhatsApp
        whatsAppService.sendContractByWhatsApp(client.getPhoneNumber(), contractId);
    }
}
