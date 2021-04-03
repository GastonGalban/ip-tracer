package com.gastongalban.iptracer.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gastongalban.iptracer")
public class IpTracerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpTracerApplication.class, args);
    }

}
