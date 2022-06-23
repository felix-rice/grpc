package com.felix.grpc.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.felix.grpc", "com.felix.grpc.sdk.ecommerce"})
public class GrpcRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcRunnerApplication.class, args);
    }

}
