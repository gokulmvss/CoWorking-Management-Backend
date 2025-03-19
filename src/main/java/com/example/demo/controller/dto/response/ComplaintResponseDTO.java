package com.example.demo.controller.dto.response;

import java.time.LocalDateTime;

import com.example.demo.entity.ComplaintStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ComplaintResponseDTO {
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ComplaintStatus status = ComplaintStatus.OPEN;
    private EmployeeBasicInfo submittedBy;
    private WorkspaceBasicInfo workspace;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Inner classes for basic details
    public static class EmployeeBasicInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        public void setId(Long id) {
            this.id = id;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
        
    }

    public static class WorkspaceBasicInfo {
        private Long id;
        private String name;
        private String location;
        private String type;
        
        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setType(String type) {
            this.type = type;
        }
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getType() {
            return type;
        }
    }

	public ComplaintResponseDTO() {
		super();
	}

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

	public EmployeeBasicInfo getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(EmployeeBasicInfo submittedBy) {
		this.submittedBy = submittedBy;
	}

	public WorkspaceBasicInfo getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WorkspaceBasicInfo workspace) {
		this.workspace = workspace;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
}

