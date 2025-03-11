package com.example.demo.controller.dto.response;


public class WorkspaceDTO {
    private Long id;
    private String name;
    private String type;
    private Integer capacity;
    private String location;
    private Double pricePerHour;
    private Boolean available;
    private String coworkingSpaceName;
    private Long coworkingSpaceId;

    // Default constructor
    public WorkspaceDTO() {
    }

    // Constructor with all fields
    public WorkspaceDTO(Long id, String name, String type, Integer capacity, String location,
                     Double pricePerHour, Boolean available, String coworkingSpaceName,
                     Long coworkingSpaceId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.available = available;
        this.coworkingSpaceName = coworkingSpaceName;
        this.coworkingSpaceId = coworkingSpaceId;
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

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
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
        private Double pricePerHour;
        private Boolean available;
        private String coworkingSpaceName;
        private Long coworkingSpaceId;

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

        public Builder pricePerHour(Double pricePerHour) {
            this.pricePerHour = pricePerHour;
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

        public WorkspaceDTO build() {
            return new WorkspaceDTO(
                id, name, type, capacity, location, pricePerHour, 
                available, coworkingSpaceName, coworkingSpaceId
            );
        }
    }
}
