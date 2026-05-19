package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailValidationService {

    @Value("${abstract.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean isValidEmail(String email) {
        String url = "https://emailreputation.abstractapi.com/v1/?api_key=" + apiKey + "&email=" + email;
        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.isBlank()) {
                throw new ApiException("Received empty response from AbstractAPI");
            }

            JsonNode root = objectMapper.readTree(response);

            // Handle API-level errors (invalid key, quota exceeded, etc.)
            if (root.has("error")) {
                String errorMsg = root.get("error").has("message")
                        ? root.get("error").get("message").asText()
                        : root.get("error").asText();
                throw new ApiException("AbstractAPI error: " + errorMsg);
            }

            // New API schema: email_deliverability.status
            JsonNode emailDeliverability = root.get("email_deliverability");
            if (emailDeliverability != null && emailDeliverability.has("status")) {
                return emailDeliverability.get("status").asText().equalsIgnoreCase("deliverable");
            }

            // Fallback: old API schema had a top-level "deliverability" field
            JsonNode deliverability = root.get("deliverability");
            if (deliverability != null) {
                return deliverability.asText().equalsIgnoreCase("DELIVERABLE");
            }

            throw new ApiException("Unexpected response format from AbstractAPI");

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Email validation failed: " + e.getMessage());
        }
    }
}