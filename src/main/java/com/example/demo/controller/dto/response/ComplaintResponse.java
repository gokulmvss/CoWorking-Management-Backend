package com.example.demo.controller.dto.response;

import java.time.LocalDateTime;

import com.example.demo.entity.ComplaintStatus;

import lombok.Data;

@Data
public class ComplaintResponse {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public ComplaintStatus getStatus() {
		return status;
	}
	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getWorkspaceName() {
		return workspaceName;
	}
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}
	public String getResolutionNotes() {
		return resolutionNotes;
	}
	public void setResolutionNotes(String resolutionNotes) {
		this.resolutionNotes = resolutionNotes;
	}
	public LocalDateTime getResolvedAt() {
		return resolvedAt;
	}
	public void setResolvedAt(LocalDateTime resolvedAt) {
		this.resolvedAt = resolvedAt;
	}
	public String getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	private Long id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private String employeeName;
    private String workspaceName;
    private String resolutionNotes;
    private LocalDateTime resolvedAt;
    private String resolvedBy;
    private LocalDateTime createdAt;
}
