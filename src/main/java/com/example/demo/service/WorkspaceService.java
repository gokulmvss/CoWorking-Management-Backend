package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.CreateWorkspaceRequest;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.exceptions.ResourceNotFoundException;

public interface WorkspaceService {

    /**
     * Create a new workspace within a coworking space
     *
     * @param coworkingSpaceId The coworking space ID
     * @param request The workspace details
     * @return The created workspace DTO
     * @throws ResourceNotFoundException if coworking space not found
     */
    WorkspaceDTO createWorkspace(Long coworkingSpaceId, CreateWorkspaceRequest request)
            throws ResourceNotFoundException;

    /**
     * Get all workspaces for a coworking space
     *
     * @param coworkingSpaceId The coworking space ID
     * @return List of workspace DTOs
     * @throws ResourceNotFoundException if coworking space not found
     */
    List<WorkspaceDTO> getAllWorkspacesByCoworkingSpace(Long coworkingSpaceId)
            throws ResourceNotFoundException;

    /**
     * Get all available workspaces for a coworking space
     *
     * @param coworkingSpaceId The coworking space ID
     * @return List of available workspace DTOs
     * @throws ResourceNotFoundException if coworking space not found
     */
    List<WorkspaceDTO> getAvailableWorkspaces(Long coworkingSpaceId)
            throws ResourceNotFoundException;
            
    /**
     * Get all workspaces with at least the specified number of available seats
     *
     * @param coworkingSpaceId The coworking space ID
     * @param minSeats Minimum number of available seats required
     * @return List of workspace DTOs with sufficient available seats
     * @throws ResourceNotFoundException if coworking space not found
     */
    List<WorkspaceDTO> getWorkspacesWithAvailableSeats(Long coworkingSpaceId, Integer minSeats)
            throws ResourceNotFoundException;
            
    /**
     * Get a workspace with all its seats
     *
     * @param workspaceId The workspace ID
     * @return The workspace DTO with seats
     * @throws ResourceNotFoundException if workspace not found
     */
    WorkspaceDTO getWorkspaceWithSeats(Long workspaceId) throws ResourceNotFoundException;
}
