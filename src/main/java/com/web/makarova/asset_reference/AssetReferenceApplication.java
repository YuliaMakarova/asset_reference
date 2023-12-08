package com.web.makarova.asset_reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
public class AssetReferenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetReferenceApplication.class, args);
    }

}
