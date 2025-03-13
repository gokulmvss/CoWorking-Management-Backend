package com.example.demo.controller.dto.response;

import java.time.LocalDateTime;

public class TableBookingDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long seatId;
    private String seatNumber;
    private Long workspaceId;
    private String workspaceName;
    private LocalDateTime bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    
    // Default constructor
    public TableBookingDTO() {
    }
    
    // All-args constructor
    public TableBookingDTO(Long id, Long employeeId, String employeeName, Long seatId, String seatNumber,
                       Long workspaceId, String workspaceName, LocalDateTime bookingDate, 
                       LocalDateTime startTime, LocalDateTime endTime, LocalDateTime checkInTime,
                       LocalDateTime checkOutTime, String status, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Long id;
        private Long employeeId;
        private String employeeName;
        private Long seatId;
        private String seatNumber;
        private Long workspaceId;
        private String workspaceName;
        private LocalDateTime bookingDate;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDateTime checkInTime;
        private LocalDateTime checkOutTime;
        private String status;
        private String notes;
        private LocalDateTime createdAt;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder employeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }
        
        public Builder employeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }
        
        public Builder seatId(Long seatId) {
            this.seatId = seatId;
            return this;
        }
        
        public Builder seatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
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
        
        public Builder bookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }
        
        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }
        
        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }
        
        public Builder checkInTime(LocalDateTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }
        
        public Builder checkOutTime(LocalDateTime checkOutTime) {
            this.checkOutTime = checkOutTime;
            return this;
        }
        
        public Builder status(String status) {
            this.status = status;
            return this;
        }
        
        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public TableBookingDTO build() {
            return new TableBookingDTO(
                id, employeeId, employeeName, seatId, seatNumber, workspaceId, workspaceName,
                bookingDate, startTime, endTime, checkInTime, checkOutTime, status, notes, createdAt
            );
        }
    }
}