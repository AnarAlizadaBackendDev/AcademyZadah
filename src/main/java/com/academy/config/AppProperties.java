package com.academy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppProperties(
        String brandName,
        String ownerName,
        String baseUrl,
        String storageDir,
        String notifyEmail,
        Admin admin,
        Contact contact) {

    public record Admin(String email, String password, String name) {
    }

    public record Contact(String email, String phone, String linkedin, String github, String telegram) {
    }
}
