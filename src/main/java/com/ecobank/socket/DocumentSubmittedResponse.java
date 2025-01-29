package com.ecobank.socket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentSubmittedResponse {

    private int statusCode;
    private String message;
    @JsonProperty("data")
    private DocumentSubmittedData data;

    // Getters and Setters
    @Data
    public static class DocumentSubmittedData {
        private String hasSetTransactionPin;
        private String hasDoneEllevateProfiling;
        private String hasDoneAddressVerification;
        private List<OtherDocuments> otherDocument;

        // Getters and Setters
    }

    @Data
    public static class OtherDocuments {
        private String documentType;
        private String documentCategory;
        private String uploadedOn;
        private String file;
        private String idNumber;
        private String documentName;
        private String status;
        private String comment;

        // Getters and Setters
    }

    // Getters and Setters for DocumentSubmittedResponse
}

