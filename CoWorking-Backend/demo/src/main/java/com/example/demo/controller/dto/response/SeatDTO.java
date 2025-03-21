package com.example.demo.controller.dto.response;


public class SeatDTO {
    private Long id;
    private String seatNumber;
    private String status;
    private String type;
    private String features;
    private Long workspaceId;
    private String workspaceName;
    private Long companyId;
    private String companyName;
    
    // Default constructor
    public SeatDTO() {
    }
    
    // Constructor with all fields
    public SeatDTO(Long id, String seatNumber, String status, String type, String features,
                Long workspaceId, String workspaceName, Long companyId, String companyName) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.status = status;
        this.type = type;
        this.features = features;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.companyId = companyId;
        this.companyName = companyName;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Long id;
        private String seatNumber;
        private String status;
        private String type;
        private String features;
        private Long workspaceId;
        private String workspaceName;
        private Long companyId;
        private String companyName;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder seatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }
        
        public Builder status(String status) {
            this.status = status;
            return this;
        }
        
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        public Builder features(String features) {
            this.features = features;
            return this;
        }
        
        public Builder workspaceId(Long workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }
        
        public Builder workspaceName(String workspaceName) {
            this.workspaceName = workspaceName;
            return this;
        }
        
        public Builder companyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }
        
        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        
        public SeatDTO build() {
            return new SeatDTO(
                id, seatNumber, status, type, features,
                workspaceId, workspaceName, companyId, companyName
            );
        }
    }
}
