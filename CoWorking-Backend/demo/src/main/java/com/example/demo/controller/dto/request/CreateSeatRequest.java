package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateSeatRequest {
    
    @NotBlank(message = "Seat number is required")
    @Size(max = 20, message = "Seat number cannot be longer than 20 characters")
    private String seatNumber;
    
    @Size(max = 50, message = "Type cannot be longer than 50 characters")
    private String type;
    
    @Size(max = 255, message = "Features cannot be longer than 255 characters")
    private String features;
    
    // Getters and setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
