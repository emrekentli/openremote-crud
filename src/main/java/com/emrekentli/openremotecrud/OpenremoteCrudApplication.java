package com.emrekentli.openremotecrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpenremoteCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenremoteCrudApplication.class, args);
    }

}
