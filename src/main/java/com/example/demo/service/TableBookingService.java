package com.example.demo.service;

import com.example.demo.controller.dto.request.CreateBookingRequest;
import com.example.demo.controller.dto.response.TableBookingDTO;
import com.example.demo.entity.TableBookingStatus;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface TableBookingService {

    /**
     * Create a new booking
     *
     * @param request The booking details
     * @return The created booking DTO
     * @throws ResourceNotFoundException if employee or seat not found
     * @throws IllegalStateException if the seat is not available
     */
    TableBookingDTO createBooking(CreateBookingRequest request) 
            throws ResourceNotFoundException, IllegalStateException;

    /**
     * Get a booking by ID
     *
     * @param id The booking ID
     * @return The booking DTO
     * @throws ResourceNotFoundException if booking not found
     */
    TableBookingDTO getBookingById(Long id) throws ResourceNotFoundException;

    /**
     * Get all bookings for an employee
     *
     * @param employeeId The employee ID
     * @return List of booking DTOs
     * @throws ResourceNotFoundException if employee not found
     */
    List<TableBookingDTO> getBookingsByEmployee(Long employeeId) throws ResourceNotFoundException;

    /**
     * Get bookings for an employee with specific statuses
     *
     * @param employeeId The employee ID
     * @param statuses List of statuses to filter by
     * @return List of booking DTOs
     * @throws ResourceNotFoundException if employee not found
     */
    List<TableBookingDTO> getBookingsByEmployeeAndStatus(Long employeeId, List<TableBookingStatus> statuses)
            throws ResourceNotFoundException;

    /**
     * Get all bookings for a specific date
     *
     * @param date The date to get bookings for
     * @return List of booking DTOs
     */
    List<TableBookingDTO> getBookingsByDate(LocalDate date);

    /**
     * Get all bookings for a specific seat
     *
     * @param seatId The seat ID
     * @return List of booking DTOs
     * @throws ResourceNotFoundException if seat not found
     */
    List<TableBookingDTO> getBookingsBySeat(Long seatId) throws ResourceNotFoundException;

    /**
     * Update booking status
     *
     * @param id The booking ID
     * @param status The new status
     * @return The updated booking DTO
     * @throws ResourceNotFoundException if booking not found
     */
    TableBookingDTO updateBookingStatus(Long id, TableBookingStatus status)
            throws ResourceNotFoundException;

    /**
     * Check in for a booking
     *
     * @param id The booking ID
     * @return The updated booking DTO
     * @throws ResourceNotFoundException if booking not found
     * @throws IllegalStateException if the booking cannot be checked in
     */
    TableBookingDTO checkIn(Long id) throws ResourceNotFoundException, IllegalStateException;

    /**
     * Check out from a booking
     *
     * @param id The booking ID
     * @return The updated booking DTO
     * @throws ResourceNotFoundException if booking not found
     * @throws IllegalStateException if the booking cannot be checked out
     */
    TableBookingDTO checkOut(Long id) throws ResourceNotFoundException, IllegalStateException;

    /**
     * Cancel a booking
     *
     * @param id The booking ID
     * @param employeeId The employee ID (for verification)
     * @return The updated booking DTO
     * @throws ResourceNotFoundException if booking not found
     * @throws IllegalStateException if the booking cannot be cancelled
     */
    TableBookingDTO cancelBooking(Long id, Long employeeId)
            throws ResourceNotFoundException, IllegalStateException;
}
