package com.ecobank.socket;

public class FetchDocumentsRequest {
    private String token;
    private String affiliateCode;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }
}

