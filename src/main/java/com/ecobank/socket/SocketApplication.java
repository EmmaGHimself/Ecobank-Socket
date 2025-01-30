package com.ecobank.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SocketApplication {
    @Autowired
    private SocketIOServer socketIOServer;

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }

    // This runner ensures the Socket.IO server starts
    @Bean
    public CommandLineRunner run() {
        return args -> {
            socketIOServer.start();
            System.out.println("Socket.IO server started on port 8080");
        };
    }
}
