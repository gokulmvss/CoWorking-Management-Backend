package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateBulkSeatsRequest {
    
    @NotNull(message = "Starting seat number is required")
    private Integer startingNumber;
    
    @NotNull(message = "Number of seats is required")
    @Positive(message = "Number of seats must be positive")
    private Integer numberOfSeats;
    
    @Size(max = 50, message = "Type cannot be longer than 50 characters")
    private String type;
    
    @Size(max = 255, message = "Features cannot be longer than 255 characters")
    private String features;
    
    @NotBlank(message = "Prefix is required")
    @Size(max = 10, message = "Prefix cannot be longer than 10 characters")
    private String prefix = "S";
    
    // Getters and setters
    public Integer getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(Integer startingNumber) {
        this.startingNumber = startingNumber;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}