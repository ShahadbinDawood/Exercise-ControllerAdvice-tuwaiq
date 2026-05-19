package com.example.capstone2.Repository;

import com.example.capstone2.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment ,Integer> {
    Payment findPaymentById(Integer id);
    Payment findPaymentByContractId(Integer contractId);
    List<Payment> findPaymentByClientId(Integer clientId);
    List<Payment> findPaymentByFreelancerId(Integer freelancerId);
    List<Payment> findPaymentByFreelancerIdAndClientId(Integer freelancerId, Integer clientId);
}
