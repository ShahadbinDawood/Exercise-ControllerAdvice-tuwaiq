package com.example.capstone2.Controller;

import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Model.Contract;
import com.example.capstone2.Model.User;
import com.example.capstone2.Service.ContractPdfService;
import com.example.capstone2.Service.ContractService;
import com.example.capstone2.Service.UserService;
import com.example.capstone2.Service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final ContractPdfService contractPdfService;
    private final WhatsAppService   whatsAppService;
    private final UserService userService  ;
    @GetMapping("/all")
    public ResponseEntity<?> getAllContract(){
        return ResponseEntity.status(200).body(contractService.getAllContract());
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Integer id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @GetMapping("/get/freelancer/{freelancerId}")
    public ResponseEntity<?> getByFreelancer(@PathVariable Integer freelancerId) {
        return ResponseEntity.ok(contractService.findContractByFreelancerId(freelancerId));
    }

    @GetMapping("/get/client/{clientId}")
    public ResponseEntity<?> getByClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(contractService.findContractByClientId(clientId));
    }

    @GetMapping("/get/project/{projectId}")
    public ResponseEntity<?> getByProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(contractService.findContractByProjectId(projectId));
    }


    @PutMapping("/complete/{contractId}")
    public ResponseEntity<?> completeContract(@PathVariable Integer contractId) {
        contractService.completeContract(contractId);
        return ResponseEntity.ok(new ApiResponse("Contract completed successfully"));
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadContract(@PathVariable Integer id){
         Contract contract = contractService.getContractById(id);
        byte[] pdf = contractPdfService.generateContractPdf(contract);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "contract_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);

    }
    @PostMapping("/send/{id}")
    public ResponseEntity<?> sendContract(@PathVariable Integer id) {
        contractService.sendContract(id);
        return ResponseEntity.ok(new ApiResponse("Contract sent via Email & WhatsApp ✅"));
    }

}
