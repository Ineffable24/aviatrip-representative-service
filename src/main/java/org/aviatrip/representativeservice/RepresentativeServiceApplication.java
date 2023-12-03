package org.aviatrip.representativeservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RepresentativeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepresentativeServiceApplication.class, args);

    }
}