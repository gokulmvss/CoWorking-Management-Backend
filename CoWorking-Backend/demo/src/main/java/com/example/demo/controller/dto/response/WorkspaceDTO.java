package com.example.demo.controller.dto.response;

import java.util.List;

public class WorkspaceDTO {
    private Long id;
    private String name;
    private String type;
    private Integer capacity;
    private String location;
    private Double pricePerSeatPerHour; // Changed from pricePerHour
    private Boolean available;
    private String coworkingSpaceName;
    private Long coworkingSpaceId;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer companyAllocatedSeats;
    private Integer employeeBookedSeats;
    private List<SeatDTO> seats;

    // Default constructor
    public WorkspaceDTO() {
    }

    // Constructor with all fields
    public WorkspaceDTO(Long id, String name, String type, Integer capacity, String location,
                     Double pricePerSeatPerHour, Boolean available, String coworkingSpaceName,
                     Long coworkingSpaceId, Integer totalSeats, Integer availableSeats, 
                     Integer companyAllocatedSeats, Integer employeeBookedSeats, List<SeatDTO> seats) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.pricePerSeatPerHour = pricePerSeatPerHour;
        this.available = available;
        this.coworkingSpaceName = coworkingSpaceName;
        this.coworkingSpaceId = coworkingSpaceId;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.companyAllocatedSeats = companyAllocatedSeats;
        this.employeeBookedSeats = employeeBookedSeats;
        this.seats = seats;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPricePerSeatPerHour() {
        return pricePerSeatPerHour;
    }

    public void setPricePerSeatPerHour(Double pricePerSeatPerHour) {
        this.pricePerSeatPerHour = pricePerSeatPerHour;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getCoworkingSpaceName() {
        return coworkingSpaceName;
    }

    public void setCoworkingSpaceName(String coworkingSpaceName) {
        this.coworkingSpaceName = coworkingSpaceName;
    }

    public Long getCoworkingSpaceId() {
        return coworkingSpaceId;
    }

    public void setCoworkingSpaceId(Long coworkingSpaceId) {
        this.coworkingSpaceId = coworkingSpaceId;
    }
    
    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getCompanyAllocatedSeats() {
        return companyAllocatedSeats;
    }

    public void setCompanyAllocatedSeats(Integer companyAllocatedSeats) {
        this.companyAllocatedSeats = companyAllocatedSeats;
    }

    public Integer getEmployeeBookedSeats() {
        return employeeBookedSeats;
    }

    public void setEmployeeBookedSeats(Integer employeeBookedSeats) {
        this.employeeBookedSeats = employeeBookedSeats;
    }
    
    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String type;
        private Integer capacity;
        private String location;
        private Double pricePerSeatPerHour;
        private Boolean available;
        private String coworkingSpaceName;
        private Long coworkingSpaceId;
        private Integer totalSeats;
        private Integer availableSeats;
        private Integer companyAllocatedSeats;
        private Integer employeeBookedSeats;
        private List<SeatDTO> seats;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder capacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder pricePerSeatPerHour(Double pricePerSeatPerHour) {
            this.pricePerSeatPerHour = pricePerSeatPerHour;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public Builder coworkingSpaceName(String coworkingSpaceName) {
            this.coworkingSpaceName = coworkingSpaceName;
            return this;
        }

        public Builder coworkingSpaceId(Long coworkingSpaceId) {
            this.coworkingSpaceId = coworkingSpaceId;
            return this;
        }
        
        public Builder totalSeats(Integer totalSeats) {
            this.totalSeats = totalSeats;
            return this;
        }
        
        public Builder availableSeats(Integer availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }
        
        public Builder companyAllocatedSeats(Integer companyAllocatedSeats) {
            this.companyAllocatedSeats = companyAllocatedSeats;
            return this;
        }
        
        public Builder employeeBookedSeats(Integer employeeBookedSeats) {
            this.employeeBookedSeats = employeeBookedSeats;
            return this;
        }
        
        public Builder seats(List<SeatDTO> seats) {
            this.seats = seats;
            return this;
        }

        public WorkspaceDTO build() {
            return new WorkspaceDTO(
                id, name, type, capacity, location, pricePerSeatPerHour, 
                available, coworkingSpaceName, coworkingSpaceId,
                totalSeats, availableSeats, companyAllocatedSeats, employeeBookedSeats, seats
            );
        }
    }
}