package com.hyperdata.nifi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hyperdata.nifi.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class NifiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NifiApplication.class, args);
    }

}
