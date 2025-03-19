package com.example.demo.controller.dto.request;

import lombok.Data;

@Data
public class ComplaintRequest {
    private String title;
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String description;
    public Long employeeId;
    public Long workspaceId;
}
