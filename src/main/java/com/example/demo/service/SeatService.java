package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CompanyAllocationRequest;
import com.example.demo.controller.dto.request.CreateBulkSeatsRequest;
import com.example.demo.controller.dto.request.CreateSeatRequest;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.entity.Seat;

public interface SeatService {
    
    /**
     * Create a new seat in a workspace
     * 
     * @param workspaceId The workspace ID
     * @param request The seat details
     * @return The created seat DTO
     * @throws ResourceNotFoundException if workspace not found
     * @throws ConflictException if seat number already exists in workspace
     */
    SeatDTO createSeat(Long workspaceId, CreateSeatRequest request) 
            throws ResourceNotFoundException;
    
    /**
     * Create multiple seats in bulk for a workspace
     * 
     * @param workspaceId The workspace ID
     * @param request The bulk creation details
     * @return List of created seat DTOs
     * @throws ResourceNotFoundException if workspace not found
     */
    List<SeatDTO> createBulkSeats(Long workspaceId, CreateBulkSeatsRequest request)
            throws ResourceNotFoundException;
    
    /**
     * Get all seats for a workspace
     * 
     * @param workspaceId The workspace ID
     * @return List of seat DTOs
     * @throws ResourceNotFoundException if workspace not found
     */
    List<SeatDTO> getSeatsByWorkspace(Long workspaceId) throws ResourceNotFoundException;
    
    /**
     * Get available seats for a workspace
     * 
     * @param workspaceId The workspace ID
     * @return List of available seat DTOs
     * @throws ResourceNotFoundException if workspace not found
     */
    List<SeatDTO> getAvailableSeatsByWorkspace(Long workspaceId) throws ResourceNotFoundException;
    
    /**
     * Get seats allocated to a company
     * 
     * @param companyId The company ID
     * @return List of allocated seat DTOs
     * @throws ResourceNotFoundException if company not found
     */
    List<SeatDTO> getSeatsByCompany(Long companyId) throws ResourceNotFoundException;
    
    /**
     * Allocate seats to a company
     * 
     * @param companyId The company ID
     * @param request The allocation request
     * @return List of allocated seat DTOs
     * @throws ResourceNotFoundException if company or workspace not found
     * @throws IllegalStateException if not enough seats are available
     */
    List<SeatDTO> allocateSeatsToCompany(Long companyId, CompanyAllocationRequest request)
            throws ResourceNotFoundException, IllegalStateException;
    
    /**
     * Release company-allocated seats back to available
     * 
     * @param companyId The company ID
     * @param seatIds List of seat IDs to release
     * @return List of released seat DTOs
     * @throws ResourceNotFoundException if company or seats not found
     * @throws IllegalStateException if seats not allocated to specified company
     */
    List<SeatDTO> releaseCompanyAllocatedSeats(Long companyId, List<Long> seatIds)
            throws ResourceNotFoundException, IllegalStateException;
            
    /**
     * Get a specific seat by ID
     * 
     * @param seatId The seat ID
     * @return The seat DTO
     * @throws ResourceNotFoundException if seat not found
     */
    SeatDTO getSeatById(Long seatId) throws ResourceNotFoundException;
    
    /**
     * Update a seat's status
     * 
     * @param seatId The seat ID
     * @param status The new status
     * @return The updated seat DTO
     * @throws ResourceNotFoundException if seat not found
     */
    SeatDTO updateSeatStatus(Long seatId, Seat.SeatStatus status) throws ResourceNotFoundException;
}