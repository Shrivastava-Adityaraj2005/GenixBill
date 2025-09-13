package com.myproj.bill_gen;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.myproj.bill_gen.model")
public class BillGenApplication {
    public static void main(String[] args) {
        // Load .env only in local dev
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // Do nothing if .env is missing (e.g., on Render)
                .load();

        // Explicitly set vars for local dev only
        dotenv.entries().forEach(entry -> {
            if (System.getenv(entry.getKey()) == null) {  // Don't overwrite existing env vars
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

        SpringApplication.run(BillGenApplication.class, args);
    }
}
