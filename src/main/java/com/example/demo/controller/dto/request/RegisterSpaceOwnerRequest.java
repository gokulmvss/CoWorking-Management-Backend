package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterSpaceOwnerRequest {
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
 // Coworking space details
    @NotBlank(message = "Space name is required")
    @Size(max = 100)
    private String spaceName;
    
    @NotBlank(message = "Space address is required")
    @Size(max = 255)
    private String spaceAddress;
    
    @Size(max = 50)
    @Email(message = "Invalid space email format")
    private String spaceContactEmail;
    
    @Size(max = 20)
    private String spaceContactPhone;
    
    @Size(max = 500)
    private String spaceDescription;

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceAddress() {
		return spaceAddress;
	}

	public void setSpaceAddress(String spaceAddress) {
		this.spaceAddress = spaceAddress;
	}

	public String getSpaceContactEmail() {
		return spaceContactEmail;
	}

	public void setSpaceContactEmail(String spaceContactEmail) {
		this.spaceContactEmail = spaceContactEmail;
	}

	public String getSpaceContactPhone() {
		return spaceContactPhone;
	}

	public void setSpaceContactPhone(String spaceContactPhone) {
		this.spaceContactPhone = spaceContactPhone;
	}

	public String getSpaceDescription() {
		return spaceDescription;
	}

	public void setSpaceDescription(String spaceDescription) {
		this.spaceDescription = spaceDescription;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	
}
