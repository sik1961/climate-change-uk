package com.sik.climatechangeuk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Slf4j
@ConfigurationPropertiesScan("com.sik.climatechangeuk")
@EnableScheduling
public class ClimateChangeUk {
    public static void main(String[] args) {
        SpringApplication.run(ClimateChangeUk.class, args);
    }
}
