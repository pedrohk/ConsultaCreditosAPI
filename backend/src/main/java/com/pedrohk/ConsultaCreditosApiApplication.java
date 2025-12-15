package com.pedrohk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "controller",
        "service",
        "repository",
        "model",
        "messaging"
})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")
public class ConsultaCreditosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultaCreditosApiApplication.class, args);
    }
}
