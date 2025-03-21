package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.CoworkingSpaceRepository;
import com.example.demo.controller.dto.request.CreateCoworkingSpaceRequest;
import com.example.demo.controller.dto.response.CoworkingSpaceDTO;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.entity.CoworkingSpace;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.CoworkingSpaceService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoworkingSpaceServiceImpl implements CoworkingSpaceService {

    private final CoworkingSpaceRepository coworkingSpaceRepository;

    @Autowired
    public CoworkingSpaceServiceImpl(CoworkingSpaceRepository coworkingSpaceRepository) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
    }
    
    public boolean existsByContactEmail(String email) {
        return coworkingSpaceRepository.existsByContactEmail(email);
    }
    public CoworkingSpace saveCoworkingSpace(CoworkingSpace space) {
        return coworkingSpaceRepository.save(space);
    }
    @Override
    @Transactional
    public CoworkingSpaceDTO createCoworkingSpace(CreateCoworkingSpaceRequest request) {
        // Check if a coworking space with the same name already exists
//        if (coworkingSpaceRepository.existsByName(request.getName())) {
//            throw new BadRequestException("A coworking space with this name already exists");
//        }

        // Create new coworking space
        CoworkingSpace coworkingSpace = new CoworkingSpace();
        coworkingSpace.setName(request.getName());
        coworkingSpace.setAddress(request.getAddress());
        coworkingSpace.setContactEmail(request.getContactEmail());
        coworkingSpace.setContactPhone(request.getContactPhone());
        coworkingSpace.setDescription(request.getDescription());
        coworkingSpace.setActive(true);
        
     // Set new fields for totalSeats and availableSeats
        coworkingSpace.setTotalSeats(request.getTotalSeats() != null ? request.getTotalSeats() : 0);
        coworkingSpace.setAvailableSeats(request.getAvailableSeats() != null ? request.getAvailableSeats() : 0);

        
        CoworkingSpace savedSpace = coworkingSpaceRepository.save(coworkingSpace);
        
        return mapToDTO(savedSpace);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoworkingSpaceDTO> getAllCoworkingSpaces() {
        return coworkingSpaceRepository.findByActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CoworkingSpaceDTO getCoworkingSpaceById(Long id) throws ResourceNotFoundException {
        CoworkingSpace coworkingSpace = coworkingSpaceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking space not found with ID: " + id));
        
        return mapToDTO(coworkingSpace);
    }

    @Override
    @Transactional(readOnly = true)
    public CoworkingSpaceDTO getCoworkingSpaceWithWorkspaces(Long id) throws ResourceNotFoundException {
        CoworkingSpace coworkingSpace = coworkingSpaceRepository.findByIdWithWorkspaces(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking space not found with ID: " + id));
        
        if (!coworkingSpace.getActive()) {
            throw new ResourceNotFoundException("Coworking space not found with ID: " + id);
        }
        
        CoworkingSpaceDTO dto = mapToDTO(coworkingSpace);
        
        // Map workspaces
        List<WorkspaceDTO> workspaceDTOs = coworkingSpace.getWorkspaces().stream()
                .map(workspace -> WorkspaceDTO.builder()
                        .id(workspace.getId())
                        .name(workspace.getName())
                        .type(workspace.getType())
                        .capacity(workspace.getCapacity())
                        .location(workspace.getLocation())
                        .pricePerSeatPerHour(workspace.getPricePerHour())
                        .available(workspace.getAvailable())
                        .coworkingSpaceName(coworkingSpace.getName())
                        .coworkingSpaceId(coworkingSpace.getId())
                        .build())
                .collect(Collectors.toList());
        
        dto.setWorkspaces(workspaceDTOs);
        
        return dto;
    }
    @Override
    @Transactional
    public CoworkingSpaceDTO updateCoworkingSpace(Long id, CreateCoworkingSpaceRequest request) 
            throws ResourceNotFoundException {
        
        // Find the existing coworking space or throw exception if not found
        CoworkingSpace existingSpace = coworkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking space not found with id: " + id));
        
        // Update fields
        existingSpace.setName(request.getName());
        existingSpace.setAddress(request.getAddress());
        existingSpace.setContactEmail(request.getContactEmail());
        existingSpace.setContactPhone(request.getContactPhone());
        existingSpace.setDescription(request.getDescription());
        // Update total and available seats
        existingSpace.setTotalSeats(request.getTotalSeats() != null ? request.getTotalSeats() : existingSpace.getTotalSeats());
        existingSpace.setAvailableSeats(request.getAvailableSeats() != null ? request.getAvailableSeats() : existingSpace.getAvailableSeats());
        // Keep the active status as is since it might not be in the request
        // We're not modifying the active status through this update
        
        // Save the updated space
        CoworkingSpace savedSpace = coworkingSpaceRepository.save(existingSpace);
        
        // Return the updated space as DTO
        return mapToDTO(savedSpace);
    }

    @Override
    @Transactional
    public void deleteCoworkingSpace(Long id) throws ResourceNotFoundException {
        // Check if the coworking space exists
        if (!coworkingSpaceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Coworking space not found with id: " + id);
        }
        
        // Delete the coworking space
        coworkingSpaceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CoworkingSpace getCoworkingSpaceEntityById(Long id) throws ResourceNotFoundException {
        return coworkingSpaceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking space not found with ID: " + id));
    }
    
    private CoworkingSpaceDTO mapToDTO(CoworkingSpace coworkingSpace) {
        return CoworkingSpaceDTO.builder()
                .id(coworkingSpace.getId())
                .name(coworkingSpace.getName())
                .address(coworkingSpace.getAddress())
                .contactEmail(coworkingSpace.getContactEmail())
                .contactPhone(coworkingSpace.getContactPhone())
                .description(coworkingSpace.getDescription())
                .active(coworkingSpace.getActive())
                .totalSeats(coworkingSpace.getTotalSeats())
                .availableSeats(coworkingSpace.getTotalSeats())
                .workspaces(Collections.emptyList()) // Default empty, will be filled when needed
                .build();
    }
}
