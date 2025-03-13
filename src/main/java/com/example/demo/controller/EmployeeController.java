package com.example.demo.controller;

import com.example.demo.controller.dto.ConflictException;
import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateEmployeeRequest;
import com.example.demo.controller.dto.request.UpdateEmployeeRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.EmployeeDTO;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Create a new employee for a company (company admin only)
     */
    @PostMapping("/company/{companyId}")
//    @PreAuthorize("hasAuthority('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(
            @PathVariable Long companyId,
            @Valid @RequestBody CreateEmployeeRequest request) {
        
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(companyId, request);
            
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                true, 
                "Employee created successfully", 
                createdEmployee
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ConflictException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    /**
     * Get employee by ID (accessible to the employee and company admin)
     */
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                true, 
                "Employee retrieved successfully", 
                employee
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get currently authenticated employee
     */
    @GetMapping("/me")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getCurrentEmployee(
            /*@AuthenticationPrincipal UserDetails userDetails*/) {
        try {
            // For testing without security, use a hardcoded user ID
            // In production, get the user ID from the authenticated user
            Long userId = 1L; // This would be extracted from userDetails
            
            EmployeeDTO employee = employeeService.getEmployeeByUserId(userId);
            
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                true, 
                "Current employee retrieved successfully", 
                employee
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Update employee details (accessible to the employee and company admin)
     */
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest request) {
        
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, request);
            
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                true, 
                "Employee updated successfully", 
                updatedEmployee
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ConflictException e) {
            ApiResponse<EmployeeDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    /**
     * Get all employees for a company (company admin only)
     */
    @GetMapping("/company/{companyId}")
//    @PreAuthorize("hasAuthority('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployeesByCompany(
            @PathVariable Long companyId) {
        
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployeesByCompany(companyId);
            
            ApiResponse<List<EmployeeDTO>> response = new ApiResponse<>(
                true, 
                "Employees retrieved successfully", 
                employees
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<EmployeeDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get available seats for an employee to book (employee only)
     */
    @GetMapping("/{id}/available-seats")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<SeatDTO>>> getAvailableSeatsForEmployee(
            @PathVariable Long id) {
        
        try {
            List<SeatDTO> seats = employeeService.getAvailableSeatsForEmployee(id);
            
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
     * Get available workspaces for an employee to book (employee only)
     */
    @GetMapping("/{id}/available-workspaces")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<WorkspaceDTO>>> getAvailableWorkspacesForEmployee(
            @PathVariable Long id) {
        
        try {
            List<WorkspaceDTO> workspaces = employeeService.getAvailableWorkspacesForEmployee(id);
            
            ApiResponse<List<WorkspaceDTO>> response = new ApiResponse<>(
                true, 
                "Available workspaces retrieved successfully", 
                workspaces
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<WorkspaceDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}