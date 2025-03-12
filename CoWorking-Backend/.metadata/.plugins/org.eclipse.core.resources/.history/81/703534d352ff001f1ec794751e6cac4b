package com.example.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateCoworkingSpaceRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.CoworkingSpaceDTO;
import com.example.demo.service.CoworkingSpaceService;

import java.util.List;

@RestController
@RequestMapping("/api/coworking-spaces")
@CrossOrigin(origins = "http://localhost:4200/")
public class CoworkingSpaceController {

    private final CoworkingSpaceService coworkingSpaceService;

    @Autowired
    public CoworkingSpaceController(CoworkingSpaceService coworkingSpaceService) {
        this.coworkingSpaceService = coworkingSpaceService;
    }

    /**
     * Create a new coworking space (admin only)
     */
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<CoworkingSpaceDTO>> createCoworkingSpace(
            @Valid @RequestBody CreateCoworkingSpaceRequest request) {
        
        CoworkingSpaceDTO createdSpace = coworkingSpaceService.createCoworkingSpace(request);
        
        ApiResponse<CoworkingSpaceDTO> response = new ApiResponse<>(
            true, 
            "Coworking space created successfully", 
            createdSpace
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all coworking spaces (accessible to all authenticated users)
     */
    @GetMapping
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CoworkingSpaceDTO>>> getAllCoworkingSpaces() {
        List<CoworkingSpaceDTO> spaces = coworkingSpaceService.getAllCoworkingSpaces();
        
        ApiResponse<List<CoworkingSpaceDTO>> response = new ApiResponse<>(
            true, 
            "Coworking spaces retrieved successfully", 
            spaces
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get coworking space by ID (accessible to all authenticated users)
     */
    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CoworkingSpaceDTO>> getCoworkingSpaceById(@PathVariable Long id) 
            throws ResourceNotFoundException {
        
        CoworkingSpaceDTO space = coworkingSpaceService.getCoworkingSpaceById(id);
        
        ApiResponse<CoworkingSpaceDTO> response = new ApiResponse<>(
            true, 
            "Coworking space retrieved successfully", 
            space
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get coworking space with its workspaces (accessible to all authenticated users)
     */
    @GetMapping("/{id}/with-workspaces")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CoworkingSpaceDTO>> getCoworkingSpaceWithWorkspaces(
            @PathVariable Long id) throws ResourceNotFoundException {
        
        CoworkingSpaceDTO space = coworkingSpaceService.getCoworkingSpaceWithWorkspaces(id);
        
        ApiResponse<CoworkingSpaceDTO> response = new ApiResponse<>(
            true, 
            "Coworking space with workspaces retrieved successfully", 
            space
        );
        
        return ResponseEntity.ok(response);
    }
}
