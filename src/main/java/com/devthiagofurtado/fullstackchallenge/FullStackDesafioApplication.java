package com.devthiagofurtado.fullstackchallenge;

import com.devthiagofurtado.fullstackchallenge.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class})
@EnableAutoConfiguration
@EnableFeignClients
@EnableCaching
@ComponentScan
public class FullStackDesafioApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullStackDesafioApplication.class, args);
    }

}
