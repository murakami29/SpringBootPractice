package com.example.demo.service;

public interface PasswordService {
    String encodePassword(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
