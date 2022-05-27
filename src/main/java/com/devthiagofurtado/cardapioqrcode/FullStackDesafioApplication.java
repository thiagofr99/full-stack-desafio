package com.devthiagofurtado.cardapioqrcode;

import com.devthiagofurtado.cardapioqrcode.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class})
@EnableAutoConfiguration
@ComponentScan
public class FullStackDesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullStackDesafioApplication.class, args);
	}

}
