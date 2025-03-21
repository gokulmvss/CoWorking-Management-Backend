package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCoworkingSpaceRequest {
    
    @NotBlank(message = "Coworking space name is required")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;
    
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;
    
    @Email(message = "Must provide a valid email")
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    private String contactEmail;
    
    @Size(max = 20, message = "Phone number cannot be longer than 20 characters")
    private String contactPhone;
    
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

	public CreateCoworkingSpaceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
