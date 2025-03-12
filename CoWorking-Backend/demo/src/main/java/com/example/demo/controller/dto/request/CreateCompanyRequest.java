package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCompanyRequest {
    
    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;
    
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;
    
    @Email(message = "Must provide a valid email")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    private String email;
    
    @Size(max = 20, message = "Phone number cannot be longer than 20 characters")
    private String phone;
    
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;
    
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
