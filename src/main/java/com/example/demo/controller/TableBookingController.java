package com.example.demo.controller;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateBookingRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.TableBookingDTO;
import com.example.demo.entity.TableBookingStatus;
import com.example.demo.service.TableBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200/")
public class TableBookingController {

    private final TableBookingService tableBookingService;

    @Autowired
    public TableBookingController(TableBookingService tableBookingService) {
        this.tableBookingService = tableBookingService;
    }


    /**
     * Create a new booking (employee only)
     */
    @PostMapping
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TableBookingDTO>> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {
        
        try {
            TableBookingDTO createdBooking = tableBookingService.createBooking(request);
            
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                true, 
                "Booking created successfully", 
                createdBooking
            );
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get a booking by ID
     */
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN', 'SPACE_OWNER')")
    public ResponseEntity<ApiResponse<TableBookingDTO>> getBookingById(@PathVariable Long id) {
        try {
            TableBookingDTO booking = tableBookingService.getBookingById(id);
            
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                true, 
                "Booking retrieved successfully", 
                booking
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get all bookings for an employee
     */
    @GetMapping("/employee/{employeeId}")
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<List<TableBookingDTO>>> getBookingsByEmployee(
            @PathVariable Long employeeId) {
        
        try {
            List<TableBookingDTO> bookings = tableBookingService.getBookingsByEmployee(employeeId);
            
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                true, 
                "Bookings retrieved successfully", 
                bookings
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get upcoming bookings for an employee
     */
    @GetMapping("/employee/{employeeId}/upcoming")
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<List<TableBookingDTO>>> getUpcomingBookings(
            @PathVariable Long employeeId) {
        
        try {
            List<TableBookingStatus> statuses = Arrays.asList(
                    TableBookingStatus.PENDING, 
                    TableBookingStatus.CONFIRMED
            );
            
            List<TableBookingDTO> bookings = tableBookingService.getBookingsByEmployeeAndStatus(
                    employeeId, statuses);
            
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                true, 
                "Upcoming bookings retrieved successfully", 
                bookings
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get bookings for a specific date
     */
    @GetMapping("/date/{date}")
//    @PreAuthorize("hasAnyAuthority('COMPANY_ADMIN', 'SPACE_OWNER')")
    public ResponseEntity<ApiResponse<List<TableBookingDTO>>> getBookingsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List<TableBookingDTO> bookings = tableBookingService.getBookingsByDate(date);
        
        ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
            true, 
            "Bookings for date retrieved successfully", 
            bookings
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get bookings for a specific seat
     */
    @GetMapping("/seat/{seatId}")
//    @PreAuthorize("hasAnyAuthority('COMPANY_ADMIN', 'SPACE_OWNER')")
    public ResponseEntity<ApiResponse<List<TableBookingDTO>>> getBookingsBySeat(
            @PathVariable Long seatId) {
        
        try {
            List<TableBookingDTO> bookings = tableBookingService.getBookingsBySeat(seatId);
            
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                true, 
                "Bookings for seat retrieved successfully", 
                bookings
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<List<TableBookingDTO>> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Check in for a booking
     */
    @PutMapping("/{id}/check-in")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TableBookingDTO>> checkIn(@PathVariable Long id) {
        try {
            TableBookingDTO booking = tableBookingService.checkIn(id);
            
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                true, 
                "Check-in successful", 
                booking
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Check out from a booking
     */
    @PutMapping("/{id}/check-out")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TableBookingDTO>> checkOut(@PathVariable Long id) {
        try {
            TableBookingDTO booking = tableBookingService.checkOut(id);
            
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                true, 
                "Check-out successful", 
                booking
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Cancel a booking
     */
    @PutMapping("/{id}/cancel")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<ApiResponse<TableBookingDTO>> cancelBooking(
            @PathVariable Long id,
            /*@AuthenticationPrincipal UserDetails userDetails*/
            @RequestParam Long employeeId) {
        
        try {
            // In production, get the employee ID from the authenticated user
            // For testing without security, use the request parameter
            
            TableBookingDTO booking = tableBookingService.cancelBooking(id, employeeId);
            
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                true, 
                "Booking cancelled successfully", 
                booking
            );
            
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            ApiResponse<TableBookingDTO> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }}
