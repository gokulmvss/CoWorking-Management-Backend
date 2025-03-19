package com.example.demo.controller.dto.request;

import com.example.demo.entity.ComplaintStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateComplaintStatusRequest {
    public ComplaintStatus getStatus() {
		return status;
	}

	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}

	public String getResolutionNotes() {
		return resolutionNotes;
	}

	public void setResolutionNotes(String resolutionNotes) {
		this.resolutionNotes = resolutionNotes;
	}

	@NotNull
    private ComplaintStatus status;

    private String resolutionNotes;

    // Getters and Setters
}
