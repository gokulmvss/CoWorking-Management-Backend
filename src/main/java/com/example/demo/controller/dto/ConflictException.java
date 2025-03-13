package com.example.demo.controller.dto;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}