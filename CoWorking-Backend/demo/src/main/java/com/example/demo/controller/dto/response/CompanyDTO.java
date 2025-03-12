package com.example.demo.controller.dto.response;

import java.util.List;

public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String description;
    private Boolean active;
    private Integer allocatedSeatsCount;
    private List<SeatDTO> allocatedSeats;
    
    // Default constructor
    public CompanyDTO() {
    }
    
    // Constructor with all fields
    public CompanyDTO(Long id, String name, String address, String email, String phone,
                   String description, Boolean active, Integer allocatedSeatsCount, 
                   List<SeatDTO> allocatedSeats) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.active = active;
        this.allocatedSeatsCount = allocatedSeatsCount;
        this.allocatedSeats = allocatedSeats;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getAllocatedSeatsCount() {
        return allocatedSeatsCount;
    }

    public void setAllocatedSeatsCount(Integer allocatedSeatsCount) {
        this.allocatedSeatsCount = allocatedSeatsCount;
    }

    public List<SeatDTO> getAllocatedSeats() {
        return allocatedSeats;
    }

    public void setAllocatedSeats(List<SeatDTO> allocatedSeats) {
        this.allocatedSeats = allocatedSeats;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private String email;
        private String phone;
        private String description;
        private Boolean active;
        private Integer allocatedSeatsCount;
        private List<SeatDTO> allocatedSeats;
        
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
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder phone(String phone) {
            this.phone = phone;
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
        
        public Builder allocatedSeatsCount(Integer allocatedSeatsCount) {
            this.allocatedSeatsCount = allocatedSeatsCount;
            return this;
        }
        
        public Builder allocatedSeats(List<SeatDTO> allocatedSeats) {
            this.allocatedSeats = allocatedSeats;
            return this;
        }
        
        public CompanyDTO build() {
            return new CompanyDTO(
                id, name, address, email, phone, description,
                active, allocatedSeatsCount, allocatedSeats
            );
        }
    }
}