package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateComplaintRequest {
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

	public Long getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}

	@NotBlank
    private String title;

    @NotBlank
    private String description;

    private Long workspaceId;  // Workspace is optional

    // Getters and Setters
}

