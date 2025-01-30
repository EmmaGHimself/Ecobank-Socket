package com.ecobank.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SocketIOConfig {


    @Autowired
    private DocumentService documentService;

    @Bean
    public SocketIOServer socketIOServer() {
        // Configure the Socket.IO server
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("shark-app-l2tqa.ondigitalocean.app");
        config.setPort(9092);

        config.setOrigin("https://shark-app-l2tqa.ondigitalocean.app");


        // Create the SocketIO server
        SocketIOServer server = new SocketIOServer(config);

        // Add an event listener for 'fetchRejectedDocuments'
        server.addEventListener("fetchRejectedDocuments", FetchDocumentsRequest.class, (client, data, ackRequest) -> {
            System.out.println("Received request to fetch rejected documents for user: "
                    + data.getToken() + ", affiliate: " + data.getAffiliateCode());

            // Fetch the rejected documents based on userID and affiliateCode
            String token = data.getToken();
            String affiliateCode = data.getAffiliateCode();

            try {
                // Call the service method to fetch the documents
                String result = documentService.fetchRejectedDocuments(token, affiliateCode);

                String resultResponse = processDocumentResponse(result);

                // Send the response back to the client (assuming client has a sendEvent method)
                client.sendEvent("rejectedDocumentsResponse", resultResponse);

            } catch (Exception e) {
                // Handle any exceptions (you might want to log them or send a failure response)
                e.printStackTrace();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("statusCode", 400);
                jsonResponse.put("message", "Error fetching documents.");

                // Optionally, send an error message
                client.sendEvent("rejectedDocumentsResponse", jsonResponse);
            }
        });

        return server;
    }

    public String processDocumentResponse(String result) {
        JSONObject jsonResponse = new JSONObject(result);

        // Check if statusCode is 200
        if (jsonResponse.getInt("statusCode") == 200) {
            String message = jsonResponse.getString("message");

            // Get the "otherDocument" array
            JSONArray otherDocuments = jsonResponse.getJSONObject("data").getJSONArray("otherDocument");

            // Filter the documents with status "APPROVED"
            List<JSONObject> rejectedDocuments = new ArrayList<>();
            for (int i = 0; i < otherDocuments.length(); i++) {
                JSONObject document = otherDocuments.getJSONObject(i);
                if ("REJECTED".equals(document.getString("status"))) {
                    rejectedDocuments.add(document);
                }
            }

            // Construct the response with only approved documents
            JSONObject response = new JSONObject();
            response.put("statusCode", 200);
            response.put("message", message);
            response.put("rejectedDocuments", rejectedDocuments);

            return response.toString();
        } else {
            // If statusCode is not 200, return an error response
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("statusCode", jsonResponse.getInt("statusCode"));
            errorResponse.put("message", jsonResponse.getString("message"));
            return errorResponse.toString();
        }
    }

}

