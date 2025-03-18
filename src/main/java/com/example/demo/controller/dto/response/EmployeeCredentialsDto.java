package com.example.demo.controller.dto.response;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class EmployeeCredentialsDto {
    private String username;
    private String password;
    private String email;
	public EmployeeCredentialsDto() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public EmployeeCredentialsDto(String username,String email,String password) {
		super();
		this.username = username;
		this.password = password;
		this.email=email;
	}
    
}
