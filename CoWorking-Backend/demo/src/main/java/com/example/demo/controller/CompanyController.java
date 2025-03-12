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
import com.example.demo.controller.dto.request.CompanyAllocationRequest;
import com.example.demo.controller.dto.request.CreateCompanyRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.CompanyDTO;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.service.CompanyService;
import com.example.demo.service.SeatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:4200/")
public class CompanyController {

    private final CompanyService companyService;
    private final SeatService seatService;

    @Autowired
    public CompanyController(CompanyService companyService, SeatService seatService) {
        this.companyService = companyService;
        this.seatService = seatService;
    }

    /**
     * Create a new company (admin only)
     */
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<CompanyDTO>> createCompany(
            @Valid @RequestBody CreateCompanyRequest request) {
        
        try {
            CompanyDTO createdCompany = companyService.createCompany(request);
            
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                true, 
                "Company created successfully", 
                createdCompany
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    /**
     * Get all companies
     */
    @GetMapping
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        
        ApiResponse<List<CompanyDTO>> response = new ApiResponse<>(
            true, 
            "Companies retrieved successfully", 
            companies
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get company by ID
     */
    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CompanyDTO>> getCompanyById(@PathVariable Long id) {
        try {
            CompanyDTO company = companyService.getCompanyById(id);
            
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                true, 
                "Company retrieved successfully", 
                company
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get company with its allocated seats
     */
    @GetMapping("/{id}/with-seats")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CompanyDTO>> getCompanyWithAllocatedSeats(@PathVariable Long id) {
        try {
            CompanyDTO company = companyService.getCompanyWithAllocatedSeats(id);
            
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                true, 
                "Company with allocated seats retrieved successfully", 
                company
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<CompanyDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Allocate seats to a company
     */
    @PostMapping("/{id}/allocate-seats")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> allocateSeatsToCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyAllocationRequest request) {
        
        try {
            List<SeatDTO> allocatedSeats = seatService.allocateSeatsToCompany(id, request);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                String.format("%d seats allocated to company successfully", allocatedSeats.size()), 
                allocatedSeats
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Release company-allocated seats
     */
    @PostMapping("/{id}/release-seats")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> releaseCompanyAllocatedSeats(
            @PathVariable Long id,
            @RequestBody List<Long> seatIds) {
        
        try {
            List<SeatDTO> releasedSeats = seatService.releaseCompanyAllocatedSeats(id, seatIds);
            
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                true, 
                String.format("%d seats released from company successfully", releasedSeats.size()), 
                releasedSeats
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<List<SeatDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
