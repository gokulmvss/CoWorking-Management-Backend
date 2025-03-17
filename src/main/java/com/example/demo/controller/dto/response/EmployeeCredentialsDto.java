package com.example.demo.controller.dto.response;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class EmployeeCredentialsDto {
    private String username;
    private String password;
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
	public EmployeeCredentialsDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
    
}
