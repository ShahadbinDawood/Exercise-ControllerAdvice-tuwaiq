package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendContractByEmail(String toEmail, byte[] pdfBytes, Integer contractId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your Contract #" + contractId + " ✅");
            helper.setText("Please find your contract attached.");
            helper.addAttachment(
                    "contract_" + contractId + ".pdf",
                    new ByteArrayResource(pdfBytes),
                    "application/pdf"
            );
            mailSender.send(message);
        } catch (Exception e) {
            throw new ApiException("Failed to send email: " + e.getMessage());
        }
    }

    public  void sendPaymentConfirmation(String toEmail , Double amount , String invoiceNumber){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Payment Received #" + invoiceNumber + " ✅");
            helper.setText("""
            Hello,
            
            Your payment has been confirmed!
            
            Invoice Number: %s
            Amount: $%.2f
            
            Thank you for using Freelance Marketplace.
            """.formatted(invoiceNumber, amount));
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new ApiException("Failed to Payment Confirmation email ");
        }
    }
}

