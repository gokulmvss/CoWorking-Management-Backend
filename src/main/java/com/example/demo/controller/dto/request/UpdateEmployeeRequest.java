package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateEmployeeRequest {
    
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;
    
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;
    
    @Email(message = "Must provide a valid email")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    private String email;
    
    @Size(max = 20, message = "Phone number cannot be longer than 20 characters")
    private String phone;
    
    @Size(max = 100, message = "Job title cannot be longer than 100 characters")
    private String jobTitle;
    
    @Size(max = 100, message = "Department cannot be longer than 100 characters")
    private String department;
    
    private Boolean active;
    
    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}