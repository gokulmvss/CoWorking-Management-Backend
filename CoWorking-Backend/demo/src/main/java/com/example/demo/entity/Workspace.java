package com.example.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workspaces")
@EntityListeners(AuditingEntityListener.class)
public class Workspace {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 50)
    private String type; // desk, meeting room, office, etc.

    @NotNull
    @Positive
    private Integer capacity;
    
    // New fields for seat management
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @Size(max = 100)
    private String location; // floor, section, etc.

    @NotNull
    @Positive
    private Double pricePerSeatPerHour; // Changed from pricePerHour

    private Boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coworking_space_id", nullable = false)
    private CoworkingSpace coworkingSpace;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Workspace() {
    }

    // Getters and Setters
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
        return pricePerSeatPerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerSeatPerHour = pricePerHour;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public CoworkingSpace getCoworkingSpace() {
        return coworkingSpace;
    }

    public void setCoworkingSpace(CoworkingSpace coworkingSpace) {
        this.coworkingSpace = coworkingSpace;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workspace workspace = (Workspace) o;
        return Objects.equals(id, workspace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", pricePerHour=" + pricePerSeatPerHour +
                ", available=" + available +
                '}';
    }
    
 // Method to get total seats count
    public int getTotalSeatsCount() {
        return seats.size();
    }
    
    // Method to get available seats count
    public int getAvailableSeatsCount() {
        return (int) seats.stream()
                .filter(seat -> seat.getStatus() == Seat.SeatStatus.AVAILABLE)
                .count();
    }
    
    // Method to get company allocated seats count
    public int getCompanyAllocatedSeatsCount() {
        return (int) seats.stream()
                .filter(seat -> seat.getStatus() == Seat.SeatStatus.COMPANY_ALLOCATED)
                .count();
    }
    
    // Method to get employee booked seats count
    public int getEmployeeBookedSeatsCount() {
        return (int) seats.stream()
                .filter(seat -> seat.getStatus() == Seat.SeatStatus.EMPLOYEE_BOOKED)
                .count();
    }
    
    // Method to add a seat
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setWorkspace(this);
    }
    
    // Method to remove a seat
    public void removeSeat(Seat seat) {
        seats.remove(seat);
        seat.setWorkspace(null);
    }
}