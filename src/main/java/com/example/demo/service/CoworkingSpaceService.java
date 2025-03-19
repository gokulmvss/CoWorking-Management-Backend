package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.CreateCoworkingSpaceRequest;
import com.example.demo.controller.dto.response.CoworkingSpaceDTO;
import com.example.demo.entity.CoworkingSpace;
import com.example.demo.exceptions.ResourceNotFoundException;

public interface CoworkingSpaceService {
    
    /**
     * Create a new coworking space
     * 
     * @param request The coworking space details
     * @return The created coworking space DTO
     */
    CoworkingSpaceDTO createCoworkingSpace(CreateCoworkingSpaceRequest request);
    
    /**
     * Get all active coworking spaces
     * 
     * @return List of coworking space DTOs
     */
    List<CoworkingSpaceDTO> getAllCoworkingSpaces();
    
    /**
     * Get coworking space by ID
     * 
     * @param id The coworking space ID
     * @return The coworking space DTO
     * @throws ResourceNotFoundException if not found
     */
    CoworkingSpaceDTO getCoworkingSpaceById(Long id) throws ResourceNotFoundException;
    
    /**
     * Get coworking space with all its workspaces
     * 
     * @param id The coworking space ID
     * @return The coworking space DTO with workspaces
     * @throws ResourceNotFoundException if not found
     */
    CoworkingSpaceDTO getCoworkingSpaceWithWorkspaces(Long id) throws ResourceNotFoundException;
    
    /**
     * Get coworking space entity by ID (for internal service use)
     * 
     * @param id The coworking space ID
     * @return The coworking space entity
     * @throws ResourceNotFoundException if not found
     */
    CoworkingSpace getCoworkingSpaceEntityById(Long id) throws ResourceNotFoundException;
    
    public boolean existsByContactEmail(String email);
    public CoworkingSpace saveCoworkingSpace(CoworkingSpace space);
}
