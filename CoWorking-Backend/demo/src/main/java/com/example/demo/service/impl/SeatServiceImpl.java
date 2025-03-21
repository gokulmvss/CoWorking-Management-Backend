package com.example.demo.service.impl;


import com.example.demo.Repository.SeatRepository;
import com.example.demo.Repository.WorkspaceRepository;
import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CompanyAllocationRequest;
import com.example.demo.controller.dto.request.CreateBulkSeatsRequest;
import com.example.demo.controller.dto.request.CreateSeatRequest;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Seat;
import com.example.demo.entity.Workspace;
import com.example.demo.service.CompanyService;
import com.example.demo.service.SeatService;
import com.example.demo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceService workspaceService;
    private final CompanyService companyService;

    @Autowired
    public SeatServiceImpl(
            SeatRepository seatRepository,
            WorkspaceRepository workspaceRepository,
            WorkspaceService workspaceService,
            CompanyService companyService) {
        this.seatRepository = seatRepository;
        this.workspaceRepository = workspaceRepository;
        this.workspaceService = workspaceService;
        this.companyService = companyService;
    }

    @Override
    @Transactional
    public SeatDTO createSeat(Long workspaceId, CreateSeatRequest request) 
            throws ResourceNotFoundException {
        
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id: " + workspaceId));
        
//        if (seatRepository.existsBySeatNumberAndWorkspaceId(request.getSeatNumber(), workspaceId)) {
//            throw new ConflictException(
//                    "Seat with number " + request.getSeatNumber() + " already exists in this workspace");
//        }
//        
        Seat seat = new Seat();
        seat.setSeatNumber(request.getSeatNumber());
        seat.setType(request.getType());
        seat.setFeatures(request.getFeatures());
        seat.setStatus(Seat.SeatStatus.AVAILABLE);
        seat.setWorkspace(workspace);
        
        Seat savedSeat = seatRepository.save(seat);
        
        return convertToDTO(savedSeat);
    }

    @Override
    @Transactional
    public List<SeatDTO> createBulkSeats(Long workspaceId, CreateBulkSeatsRequest request)
            throws ResourceNotFoundException {
        
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id: " + workspaceId));
        
        List<Seat> seats = new ArrayList<>();
        
        for (int i = 0; i < request.getNumberOfSeats(); i++) {
            int seatNumber = request.getStartingNumber() + i;
            String formattedSeatNumber = String.format("%s%03d", request.getPrefix(), seatNumber);
            
            // Skip if seat number already exists
            if (seatRepository.existsBySeatNumberAndWorkspaceId(formattedSeatNumber, workspaceId)) {
                continue;
            }
            
            Seat seat = new Seat();
            seat.setSeatNumber(formattedSeatNumber);
            seat.setType(request.getType());
            seat.setFeatures(request.getFeatures());
            seat.setStatus(Seat.SeatStatus.AVAILABLE);
            seat.setWorkspace(workspace);
            
            seats.add(seat);
        }
        
        List<Seat> savedSeats = seatRepository.saveAll(seats);
        
        return savedSeats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> getSeatsByWorkspace(Long workspaceId) throws ResourceNotFoundException {
        // Verify workspace exists
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new ResourceNotFoundException("Workspace not found with id: " + workspaceId);
        }
        
        List<Seat> seats = seatRepository.findByWorkspaceId(workspaceId);
        
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> getAvailableSeatsByWorkspace(Long workspaceId) throws ResourceNotFoundException {
        // Verify workspace exists
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new ResourceNotFoundException("Workspace not found with id: " + workspaceId);
        }
        
        List<Seat> seats = seatRepository.findByWorkspaceIdAndStatus(workspaceId, Seat.SeatStatus.AVAILABLE);
        
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> getSeatsByCompany(Long companyId) throws ResourceNotFoundException {
        // Verify company exists
        companyService.getCompanyEntityById(companyId);
        
        List<Seat> seats = seatRepository.findByCompanyId(companyId);
        
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SeatDTO> allocateSeatsToCompany(Long companyId, CompanyAllocationRequest request)
            throws ResourceNotFoundException {
        
        Company company = companyService.getCompanyEntityById(companyId);
        
        Workspace workspace = workspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Workspace not found with id: " + request.getWorkspaceId()));
        
        List<Seat> seatsToAllocate;
        
        if (request.getSeatIds() != null && !request.getSeatIds().isEmpty()) {
            // Allocate specific seats
            seatsToAllocate = new ArrayList<>();
            
            for (Long seatId : request.getSeatIds()) {
                Seat seat = seatRepository.findById(seatId)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));
                
                if (seat.getStatus() != Seat.SeatStatus.AVAILABLE) {
                    throw new IllegalStateException("Seat with id " + seatId + " is not available");
                }
                
                if (!seat.getWorkspace().getId().equals(request.getWorkspaceId())) {
                    throw new IllegalStateException(
                            "Seat with id " + seatId + " does not belong to workspace " + request.getWorkspaceId());
                }
                
                seatsToAllocate.add(seat);
            }
            
            if (seatsToAllocate.size() != request.getSeatIds().size()) {
                throw new IllegalStateException("Not all requested seats were found");
            }
        } else {
            // Allocate any available seats up to the requested number
            long availableSeatsCount = seatRepository.countByWorkspaceIdAndStatus(
                    request.getWorkspaceId(), Seat.SeatStatus.AVAILABLE);
            
            if (availableSeatsCount < request.getNumberOfSeats()) {
                throw new IllegalStateException(
                        "Not enough available seats. Requested: " + request.getNumberOfSeats() + 
                        ", Available: " + availableSeatsCount);
            }
            
            seatsToAllocate = seatRepository.findAvailableSeatsByWorkspaceId(
                    request.getWorkspaceId(), PageRequest.of(0, request.getNumberOfSeats()));
        }
        
        for (Seat seat : seatsToAllocate) {
            seat.setStatus(Seat.SeatStatus.COMPANY_ALLOCATED);
            seat.setCompany(company);
        }
        
        List<Seat> allocatedSeats = seatRepository.saveAll(seatsToAllocate);
        
        return allocatedSeats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SeatDTO> releaseCompanyAllocatedSeats(Long companyId, List<Long> seatIds)
            throws ResourceNotFoundException, IllegalStateException {
        
        // Verify company exists
        companyService.getCompanyEntityById(companyId);
        
        List<Seat> seatsToRelease = new ArrayList<>();
        
        for (Long seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));
            
            if (seat.getCompany() == null || !seat.getCompany().getId().equals(companyId)) {
                throw new IllegalStateException(
                        "Seat with id " + seatId + " is not allocated to company " + companyId);
            }
            
            if (seat.getStatus() != Seat.SeatStatus.COMPANY_ALLOCATED) {
                throw new IllegalStateException(
                        "Seat with id " + seatId + " is not in COMPANY_ALLOCATED status");
            }
            
            seat.setStatus(Seat.SeatStatus.AVAILABLE);
            seat.setCompany(null);
            seatsToRelease.add(seat);
        }
        
        List<Seat> releasedSeats = seatRepository.saveAll(seatsToRelease);
        
        return releasedSeats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDTO getSeatById(Long seatId) throws ResourceNotFoundException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));
        
        return convertToDTO(seat);
    }

    @Override
    @Transactional
    public SeatDTO updateSeatStatus(Long seatId, Seat.SeatStatus status) throws ResourceNotFoundException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));
        
        seat.setStatus(status);
        
        Seat updatedSeat = seatRepository.save(seat);
        
        return convertToDTO(updatedSeat);
    }
    
    private SeatDTO convertToDTO(Seat seat) {
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