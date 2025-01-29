package com.ecobank.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.SocketIOServer;

@Component
public class SocketIOStarter implements CommandLineRunner {

    @Autowired
    private SocketIOServer socketIOServer;

    @Override
    public void run(String... args) throws Exception {
        // Start the Socket.IO server
        socketIOServer.start();
        System.out.println("Socket.IO Server started on port 9092.");
    }
}

