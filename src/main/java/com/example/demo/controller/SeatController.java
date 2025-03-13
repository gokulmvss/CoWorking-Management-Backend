package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateBulkSeatsRequest;
import com.example.demo.controller.dto.request.CreateSeatRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.service.SeatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "http://localhost:4200/")
public class SeatController {
    
    private final SeatService seatService;
    
    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }
    
    /**
     * Create a new seat in a workspace (admin only)
     */
    @PostMapping("/workspace/{workspaceId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SeatDTO>> createSeat(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateSeatRequest request) {
        
        try {
            SeatDTO createdSeat = seatService.createSeat(workspaceId, request);
            
            ApiResponse<SeatDTO> response = new ApiResponse<>(
                true, 
                "Seat created successfully", 
                createdSeat
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ApiResponse<SeatDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse<SeatDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
    
    /**
     * Create multiple seats in bulk for a workspace (admin only)
     */
    @PostMapping("/workspace/{workspaceId}/bulk")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> createBulkSeats(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateBulkSeatsRequest request) {
        
        try {
            List<SeatDTO> createdSeats = seatService.createBulkSeats(workspaceId, request);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                String.format("%d seats created successfully", createdSeats.size()), 
                createdSeats
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Get all seats for a workspace
     */
    @GetMapping("/workspace/{workspaceId}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> getSeatsByWorkspace(
            @PathVariable Long workspaceId) {
        
        try {
            List<SeatDTO> seats = seatService.getSeatsByWorkspace(workspaceId);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                "Seats retrieved successfully", 
                seats
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Get available seats for a workspace
     */
    @GetMapping("/workspace/{workspaceId}/available")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> getAvailableSeatsByWorkspace(
            @PathVariable Long workspaceId) {
        
        try {
            List<SeatDTO> seats = seatService.getAvailableSeatsByWorkspace(workspaceId);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                "Available seats retrieved successfully", 
                seats
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Get seats allocated to a company
     */
    @GetMapping("/company/{companyId}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> getSeatsByCompany(
            @PathVariable Long companyId) {
        
        try {
            List<SeatDTO> seats = seatService.getSeatsByCompany(companyId);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                "Company allocated seats retrieved successfully", 
                seats
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}