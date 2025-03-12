package com.example.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateWorkspaceRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.service.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    /**
     * Create a new workspace within a coworking space (admin only)
     */
    @PostMapping("/coworking-space/{coworkingSpaceId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<WorkspaceDTO>> createWorkspace(
            @PathVariable Long coworkingSpaceId,
            @Valid @RequestBody CreateWorkspaceRequest request) throws ResourceNotFoundException {
        
        WorkspaceDTO createdWorkspace = workspaceService.createWorkspace(coworkingSpaceId, request);
        
        ApiResponse<WorkspaceDTO> response = new ApiResponse<>(
            true, 
            "Workspace created successfully", 
            createdWorkspace
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all workspaces for a coworking space (accessible by any authenticated user)
     */
    @GetMapping("/coworking-space/{coworkingSpaceId}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<WorkspaceDTO>>> getAllWorkspacesByCoworkingSpace(
            @PathVariable Long coworkingSpaceId) throws ResourceNotFoundException {
        
        List<WorkspaceDTO> workspaces = workspaceService.getAllWorkspacesByCoworkingSpace(coworkingSpaceId);
        
        ApiResponse<List<WorkspaceDTO>> response = new ApiResponse<>(
            true, 
            "Workspaces retrieved successfully", 
            workspaces
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all available workspaces for a coworking space (accessible by any authenticated user)
     */
    @GetMapping("/coworking-space/{coworkingSpaceId}/available")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<WorkspaceDTO>>> getAvailableWorkspaces(
            @PathVariable Long coworkingSpaceId) throws ResourceNotFoundException {
        
        List<WorkspaceDTO> workspaces = workspaceService.getAvailableWorkspaces(coworkingSpaceId);
        
        ApiResponse<List<WorkspaceDTO>> response = new ApiResponse<>(
            true, 
            "Available workspaces retrieved successfully", 
            workspaces
        );
        
        return ResponseEntity.ok(response);
    }
}
