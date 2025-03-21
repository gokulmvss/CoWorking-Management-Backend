package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateWorkspaceRequest {
    
    @NotBlank(message = "Workspace name is required")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;
    
    @Size(max = 50, message = "Type cannot be longer than 50 characters")
    private String type;
    
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;
    
    @Size(max = 100, message = "Location cannot be longer than 100 characters")
    private String location;
    
    @NotNull(message = "Price per hour per seat is required")
    @Positive(message = "Price per hour per seat must be a positive number")
    private Double pricePerSeatPerHour;
    
    private Boolean available = true;

	public CreateWorkspaceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

	public Double getPricePerSeatPerHour() {
		return pricePerSeatPerHour;
	}

	public void setPricePerSeatPerHour(Double pricePerSeatPerHour) {
		this.pricePerSeatPerHour = pricePerSeatPerHour;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
    
    
}