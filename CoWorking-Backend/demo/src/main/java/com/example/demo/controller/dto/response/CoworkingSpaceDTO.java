package com.example.demo.controller.dto.response;

import java.util.List;

public class CoworkingSpaceDTO {
    private Long id;
    private String name;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private String description;
    private Boolean active;
    private List<WorkspaceDTO> workspaces;

    // Default constructor
    public CoworkingSpaceDTO() {
    }

    // Constructor with all fields
    public CoworkingSpaceDTO(Long id, String name, String address, String contactEmail, 
                          String contactPhone, String description, Boolean active, 
                          List<WorkspaceDTO> workspaces) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.description = description;
        this.active = active;
        this.workspaces = workspaces;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<WorkspaceDTO> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<WorkspaceDTO> workspaces) {
        this.workspaces = workspaces;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private String contactEmail;
        private String contactPhone;
        private String description;
        private Boolean active;
        private List<WorkspaceDTO> workspaces;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder contactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder contactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder workspaces(List<WorkspaceDTO> workspaces) {
            this.workspaces = workspaces;
            return this;
        }

        public CoworkingSpaceDTO build() {
            return new CoworkingSpaceDTO(
                id, name, address, contactEmail, contactPhone, 
                description, active, workspaces
            );
        }
    }
}