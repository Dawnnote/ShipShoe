package com.hanghae.shipshoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ShipShoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipShoeApplication.class, args);
    }

}
