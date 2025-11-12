package com.example.guessgame;

import com.example.guessgame.model.Player;
import com.example.guessgame.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuessGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuessGameApplication.class, args);
    }

    // 🧪 This runs automatically at startup to test your DB connection
    @Bean
    public CommandLineRunner startupTest(PlayerRepository playerRepo) {
        return args -> {
            String testName = "startup-test";
            if (playerRepo.findByName(testName) == null) {
                playerRepo.save(new Player(testName));
                System.out.println("✅ Saved startup-test player.");
            } else {
                System.out.println("ℹ️ startup-test player already exists.");
            }

            long count = playerRepo.count();
            System.out.println("👤 Player count in DB: " + count);
        };
    }
}
