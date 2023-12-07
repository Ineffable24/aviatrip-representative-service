package org.aviatrip.representativeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RepresentativeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepresentativeServiceApplication.class, args);
    }
}