package com.example.capstone2.Service;


import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Contract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ContractPdfService {

    public byte[] generateContractPdf(Contract contract) {
        String html = buildHtml(contract);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ApiException("Failed to generate contract PDF");
        }
    }

    private String buildHtml(Contract contract) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        padding: 40px;
                        color: #333;
                    }
                    .header {
                        text-align: center;
                        border-bottom: 2px solid #333;
                        padding-bottom: 20px;
                        margin-bottom: 30px;
                    }
                    .title {
                        font-size: 28px;
                        font-weight: bold;
                        color: #1a1a2e;
                    }
                    .section {
                        margin-bottom: 20px;
                        padding: 15px;
                        background-color: #f9f9f9;
                        border-radius: 8px;
                    }
                    .label {
                        font-weight: bold;
                        color: #555;
                    }
                    .footer {
                        margin-top: 50px;
                        text-align: center;
                        font-size: 12px;
                        color: #999;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <div class="title">Freelance Contract</div>
                    <p>Contract #%d</p>
                </div>

                <div class="section">
                    <p><span class="label">Project ID:</span> %d</p>
                    <p><span class="label">Client ID:</span> %d</p>
                    <p><span class="label">Freelancer ID:</span> %d</p>
                </div>

                <div class="section">
                    <p><span class="label">Agreed Amount:</span> $%d</p>
                    <p><span class="label">Status:</span> %s</p>
                    <p><span class="label">Start Date:</span> %s</p>
                </div>

                <div class="footer">
                    <p>Generated automatically by Freelance Marketplace</p>
                    <p>This contract is legally binding upon acceptance.</p>
                </div>
            </body>
            </html>
            """.formatted(
                contract.getId(),
                contract.getProjectId(),
                contract.getClientId(),
                contract.getFreelancerId(),
                contract.getAgreedAmount(),
                contract.getStatus(),
                contract.getStartDate()
        );
    }
}