package com.ecobank.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String fetchRejectedDocuments(String token, String affiliateCode) throws Exception {

        String url = "https://artxuat.ecobank.com/smeapp-service/document-submission-status";

        // Create an HTTP client
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Create a POST request
            HttpPost httpPost = new HttpPost(url);

            // Set headers
            httpPost.setHeader("Authorization", "Bearer " + token);
            httpPost.setHeader("x-affiliate-code", affiliateCode);
            httpPost.setHeader("x-language-code", "en");
            httpPost.setHeader("Content-Type", "application/json");

            System.out.println(httpPost);

            // Set body as empty JSON "{}"
            StringEntity entity = new StringEntity("{}");
            httpPost.setEntity(entity);

            // Execute the request
            org.apache.http.client.methods.CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // Parse the response JSON into DocumentSubmittedResponse object
            String responseString = EntityUtils.toString(responseEntity);

            System.out.println(responseString);

            return responseString;
        }
    }

}

