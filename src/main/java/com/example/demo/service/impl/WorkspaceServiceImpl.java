package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.SeatRepository;
import com.example.demo.Repository.WorkspaceRepository;
import com.example.demo.controller.dto.request.CreateWorkspaceRequest;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.entity.CoworkingSpace;
import com.example.demo.entity.Seat;
import com.example.demo.entity.Workspace;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.CoworkingSpaceService;
import com.example.demo.service.WorkspaceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final SeatRepository seatRepository;
    private final CoworkingSpaceService coworkingSpaceService;

    @Autowired
    public WorkspaceServiceImpl(
            WorkspaceRepository workspaceRepository,
            SeatRepository seatRepository,
            CoworkingSpaceService coworkingSpaceService) {
        this.workspaceRepository = workspaceRepository;
        this.seatRepository = seatRepository;
        this.coworkingSpaceService = coworkingSpaceService;
    }

    @Override
    @Transactional
    public WorkspaceDTO createWorkspace(Long coworkingSpaceId, CreateWorkspaceRequest request)
            throws ResourceNotFoundException {
        
        CoworkingSpace coworkingSpace = coworkingSpaceService.getCoworkingSpaceEntityById(coworkingSpaceId);
        
        Workspace workspace = new Workspace();
        workspace.setName(request.getName());
        workspace.setType(request.getType());
        workspace.setCapacity(request.getCapacity());
        workspace.setLocation(request.getLocation());
        workspace.setPricePerHour(request.getPricePerSeatPerHour());
        workspace.setAvailable(request.getAvailable());
        workspace.setCoworkingSpace(coworkingSpace);
        
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        
        return convertToDTO(savedWorkspace, null);
    }

    @Override
    public List<WorkspaceDTO> getAllWorkspacesByCoworkingSpace(Long coworkingSpaceId)
            throws ResourceNotFoundException {
        
        // Verify coworking space exists
        coworkingSpaceService.getCoworkingSpaceEntityById(coworkingSpaceId);
        
        List<Workspace> workspaces = workspaceRepository.findByCoworkingSpaceId(coworkingSpaceId);
        
        return workspaces.stream()
                .map(workspace -> convertToDTO(workspace, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkspaceDTO> getAvailableWorkspaces(Long coworkingSpaceId)
            throws ResourceNotFoundException {
        
        // Verify coworking space exists
        coworkingSpaceService.getCoworkingSpaceEntityById(coworkingSpaceId);
        
        List<Workspace> workspaces = workspaceRepository.findByCoworkingSpaceIdAndAvailableTrue(coworkingSpaceId);
        
        return workspaces.stream()
                .map(workspace -> convertToDTO(workspace, null))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<WorkspaceDTO> getWorkspacesWithAvailableSeats(Long coworkingSpaceId, Integer minSeats)
            throws ResourceNotFoundException {
        
        // Verify coworking space exists
        coworkingSpaceService.getCoworkingSpaceEntityById(coworkingSpaceId);
        
        List<Workspace> workspaces = workspaceRepository.findByCoworkingSpaceIdAndAvailableTrue(coworkingSpaceId);
        
        return workspaces.stream()
                .filter(workspace -> workspace.getAvailableSeatsCount() >= minSeats)
                .map(workspace -> convertToDTO(workspace, null))
                .collect(Collectors.toList());
    }
    
    @Override
    public WorkspaceDTO getWorkspaceWithSeats(Long workspaceId) throws ResourceNotFoundException {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id: " + workspaceId));
        
        List<Seat> seats = seatRepository.findByWorkspaceId(workspaceId);
        List<SeatDTO> seatDTOs = seats.stream()
                .map(this::convertToSeatDTO)
                .collect(Collectors.toList());
        
        return convertToDTO(workspace, seatDTOs);
    }
    
    private WorkspaceDTO convertToDTO(Workspace workspace, List<SeatDTO> seats) {
        return WorkspaceDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .type(workspace.getType())
                .capacity(workspace.getCapacity())
                .location(workspace.getLocation())
                .pricePerSeatPerHour(workspace.getPricePerHour())
                .available(workspace.getAvailable())
                .coworkingSpaceId(workspace.getCoworkingSpace().getId())
                .coworkingSpaceName(workspace.getCoworkingSpace().getName())
                .totalSeats(workspace.getTotalSeatsCount())
                .availableSeats(workspace.getAvailableSeatsCount())
                .companyAllocatedSeats(workspace.getCompanyAllocatedSeatsCount())
                .employeeBookedSeats(workspace.getEmployeeBookedSeatsCount())
                .seats(seats)
                .build();
    }
    
    private SeatDTO convertToSeatDTO(Seat seat) {
        return SeatDTO.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus().toString())
                .type(seat.getType())
                .features(seat.getFeatures())
                .workspaceId(seat.getWorkspace().getId())
                .workspaceName(seat.getWorkspace().getName())
                .companyId(seat.getCompany() != null ? seat.getCompany().getId() : null)
                .companyName(seat.getCompany() != null ? seat.getCompany().getName() : null)
                .build();
    }
}
