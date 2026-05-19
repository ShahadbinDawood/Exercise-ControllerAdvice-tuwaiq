package com.example.capstone2.Controller;


import com.example.capstone2.Api.ApiResponse;
import com.example.capstone2.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private  final PaymentService paymentService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllPayment(){
        return ResponseEntity.status(200).body(paymentService.getAllPayment());
    }
    @PostMapping("/creat/{contractId}")
    public ResponseEntity<?> creatPayment(@PathVariable Integer contractId){
        paymentService.creatPayment(contractId);
        return ResponseEntity.status(200).body(new ApiResponse("payment created successfully") );
    }
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmPayment(@PathVariable Integer id){
        paymentService.confirmPayment(id);
        return ResponseEntity.status(200).body(new ApiResponse("payment confirm successfully") );
    }
    @PutMapping("/refund/{id}")
    public ResponseEntity<?> refundPayment(@PathVariable Integer id){
        paymentService.refundPyment(id);
        return ResponseEntity.status(200).body(new ApiResponse("payment refunded successfully") );
    }
    @GetMapping("/payment-client/{clientId}")
    public ResponseEntity<?> getPaymentByClient(@PathVariable Integer clientId){
        return ResponseEntity.status(200).body(paymentService.getPaymentByClient(clientId));
    }
    @GetMapping("/payment-freelancer/{freelancerId}")
    public ResponseEntity<?> getPaymentByFreelancer(@PathVariable Integer freelancerId){
        return ResponseEntity.status(200).body(paymentService.getPaymentByFreelancer(freelancerId));
    }
    @GetMapping("/payment-freelancer-client/{freelancerId}/{clientId}")
    public ResponseEntity<?> getPaymentByFreelancerAndClient(@PathVariable Integer freelancerId , @PathVariable Integer clientId){
        return ResponseEntity.status(200).body(paymentService.getPaymentByFreelancerAndClient(freelancerId, clientId));
    }



}
