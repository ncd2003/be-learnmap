package com.learnmap.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BeLearnmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeLearnmapApplication.class, args);
    }

}
