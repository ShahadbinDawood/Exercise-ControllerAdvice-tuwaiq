package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Contract;
import com.example.capstone2.Model.Payment;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.ContractRepository;
import com.example.capstone2.Repository.PaymentRepository;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }
    public void creatPayment (Integer contractId){
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new ApiException("Contract  not  found");
        }
        if (!contract.getStatus().equalsIgnoreCase("COMPLETED")){
            throw new ApiException("Contract  should be completed before payment");
        }
        if (paymentRepository.findPaymentByContractId(contractId)!=null){
            throw new ApiException("Payment already exists for this contract");
        }
        Payment payment = new Payment();
        payment.setContractId(contractId);
        payment.setClientId(contract.getClientId());
        payment.setFreelancerId(contract.getFreelancerId());
        payment.setStatus("PENDING");
        payment.setAmount((double)contract.getAgreedAmount());
        payment.setPaymentAt(new Date());
        payment.setInvoiceNumber("INV-" + contractId + "-" + System.currentTimeMillis());
        paymentRepository.save(payment);

    }
    public void confirmPayment (Integer paymentId ){
       Payment payment= paymentRepository.findPaymentById(paymentId);
        if (payment==null){
            throw new ApiException("Payment  not  found");
        }
        if (!payment.getStatus().equalsIgnoreCase("PENDING")){
            throw new ApiException("only pending Payment can be confirmed");
        }
        payment.setStatus("COMPLETED");
        paymentRepository.save(payment);

        User freelancer  = userRepository.findUserById(payment.getFreelancerId());
        if (freelancer!=null){
            emailService.sendPaymentConfirmation(freelancer.getEmail() , payment.getAmount(), payment.getInvoiceNumber());
        }
    }

    public void  refundPyment (Integer paymentId ){
        Payment payment= paymentRepository.findPaymentById(paymentId);
        if (payment==null){
            throw new ApiException("Payment  not  found");
        }
        if (!payment.getStatus().equalsIgnoreCase("COMPLETED")){
            throw new ApiException("only complete Payment can be refunded");
        }
        payment.setStatus("REFUNDED");
        paymentRepository.save(payment);
    }
    public List<Payment> getPaymentByClient(Integer clientId){
        User client  = userRepository.findUserById(clientId);
        if (client==null){
            throw new ApiException("User  not  found");
        }
        if (client.getRole().equalsIgnoreCase("FREELANCER")){
            throw new ApiException("User is  not a client ");
        }
        List<Payment> payments = paymentRepository.findPaymentByClientId(clientId);
        if (payments.isEmpty()){
            throw new ApiException("no payment are found ");
        }
        return payments;
    }
    public List<Payment>  getPaymentByFreelancer(Integer freelancerId){
        User freelancer = userRepository.findUserById(freelancerId);
        if (freelancer==null){
            throw new ApiException("User  not  found");
        }
        if (freelancer.getRole().equalsIgnoreCase("CLIENT")){
            throw new ApiException("User is  not a Freelancer ");
        }
        List<Payment> payments = paymentRepository.findPaymentByFreelancerId(freelancerId);
        if (payments.isEmpty()){
            throw new ApiException("no payment are found ");
        }
        return payments;
    }
    public List<Payment> getPaymentByFreelancerAndClient(Integer freelancerId,Integer clientId){
        User freelancer  = userRepository.findUserById(freelancerId);
        if (freelancer==null){
            throw new ApiException("User  not  found");
        }
        if (freelancer.getRole().equalsIgnoreCase("CLIENT")){
            throw new ApiException("User is  not a Freelancer ");
        }
        User client  = userRepository.findUserById(clientId);
        if (client==null){
            throw new ApiException("User  not  found");
        }
        if (client.getRole().equalsIgnoreCase("FREELANCER")){
            throw new ApiException("User is  not a client ");
        }
        List<Payment> payments = paymentRepository.findPaymentByFreelancerIdAndClientId(freelancerId ,clientId);
        if (payments.isEmpty()){
            throw new ApiException("no payment are found ");
        }
        return payments;
    }

}
