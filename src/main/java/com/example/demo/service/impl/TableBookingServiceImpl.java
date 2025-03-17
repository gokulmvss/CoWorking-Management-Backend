package com.example.demo.service.impl;

import com.example.demo.controller.dto.request.CreateBookingRequest;
import com.example.demo.controller.dto.response.TableBookingDTO;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Seat;
import com.example.demo.entity.TableBooking;
import com.example.demo.entity.TableBookingStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.Repository.SeatRepository;
import com.example.demo.Repository.TableBookingRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.TableBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableBookingServiceImpl implements TableBookingService {

    private final TableBookingRepository tableBookingRepository;
    private final EmployeeService employeeService;
    private final SeatRepository seatRepository;

    @Autowired
    public TableBookingServiceImpl(
            TableBookingRepository tableBookingRepository,
            EmployeeService employeeService,
            SeatRepository seatRepository) {
        this.tableBookingRepository = tableBookingRepository;
        this.employeeService = employeeService;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public TableBookingDTO createBooking(CreateBookingRequest request)
            throws ResourceNotFoundException, IllegalStateException {
        
        // Validate employee exists
        Employee employee = employeeService.getEmployeeEntityById(request.getEmployeeId());
        
        // Validate seat exists
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + request.getSeatId()));
        
        // Verify seat belongs to the employee's company
        if (seat.getCompany() == null || !seat.getCompany().getId().equals(employee.getCompany().getId())) {
            throw new IllegalStateException("Seat is not allocated to the employee's company");
        }
        
        // Verify seat is available (COMPANY_ALLOCATED status)
        if (seat.getStatus() != Seat.SeatStatus.COMPANY_ALLOCATED) {
            throw new IllegalStateException("Seat is not available for booking");
        }
        
        // Check for conflicting bookings
        List<TableBooking> conflictingBookings = tableBookingRepository.findConflictingBookings(
                seat.getId(), request.getStartTime(), request.getEndTime());
        
        if (!conflictingBookings.isEmpty()) {
            throw new IllegalStateException("The seat is already booked for the requested time period");
        }
        
        // Create the booking
        TableBooking booking = new TableBooking();
        booking.setEmployee(employee);
        booking.setSeat(seat);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setStatus(TableBookingStatus.CONFIRMED); // Auto-confirm for now
        booking.setNotes(request.getNotes());
        
        TableBooking savedBooking = tableBookingRepository.save(booking);
        
        // Update seat status to EMPLOYEE_BOOKED
        seat.setStatus(Seat.SeatStatus.EMPLOYEE_BOOKED);
        seatRepository.save(seat);
        
        return convertToDTO(savedBooking);
    }

    @Override
    public TableBookingDTO getBookingById(Long id) throws ResourceNotFoundException {
        TableBooking booking = tableBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        return convertToDTO(booking);
    }

    @Override
    public List<TableBookingDTO> getBookingsByEmployee(Long employeeId) throws ResourceNotFoundException {
        // Verify employee exists
        employeeService.getEmployeeEntityById(employeeId);
        
        List<TableBooking> bookings = tableBookingRepository.findByEmployeeIdOrderByBookingDateDesc(employeeId);
        
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableBookingDTO> getBookingsByEmployeeAndStatus(Long employeeId, List<TableBookingStatus> statuses)
            throws ResourceNotFoundException {
        // Verify employee exists
        employeeService.getEmployeeEntityById(employeeId);
        
        List<TableBooking> bookings = tableBookingRepository.findByEmployeeIdAndStatusIn(employeeId, statuses);
        
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableBookingDTO> getBookingsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        
        List<TableBooking> bookings = tableBookingRepository.findBookingsForDate(startOfDay, endOfDay);
        
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableBookingDTO> getBookingsBySeat(Long seatId) throws ResourceNotFoundException {
        // Verify seat exists
        if (!seatRepository.existsById(seatId)) {
            throw new ResourceNotFoundException("Seat not found with id: " + seatId);
        }
        
        List<TableBooking> bookings = tableBookingRepository.findBySeatId(seatId);
        
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TableBookingDTO updateBookingStatus(Long id, TableBookingStatus status)
            throws ResourceNotFoundException {
        
        TableBooking booking = tableBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        booking.setStatus(status);
        
        TableBooking updatedBooking = tableBookingRepository.save(booking);
        
        return convertToDTO(updatedBooking);
    }

    @Override
    @Transactional
    public TableBookingDTO checkIn(Long id) throws ResourceNotFoundException, IllegalStateException {
        TableBooking booking = tableBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        // Verify booking can be checked in
        if (booking.getStatus() != TableBookingStatus.CONFIRMED) {
            throw new IllegalStateException("Booking cannot be checked in: current status is " + booking.getStatus());
        }
        
        booking.setStatus(TableBookingStatus.CHECKED_IN);
        booking.setCheckInTime(LocalDateTime.now());
        
        TableBooking updatedBooking = tableBookingRepository.save(booking);
        
        return convertToDTO(updatedBooking);
    }

    @Override
    @Transactional
    public TableBookingDTO checkOut(Long id) throws ResourceNotFoundException, IllegalStateException {
        TableBooking booking = tableBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        // Verify booking can be checked out
        if (booking.getStatus() != TableBookingStatus.CHECKED_IN) {
            throw new IllegalStateException("Booking cannot be checked out: current status is " + booking.getStatus());
        }
        
        booking.setStatus(TableBookingStatus.CHECKED_OUT);
        booking.setCheckOutTime(LocalDateTime.now());
        
        TableBooking updatedBooking = tableBookingRepository.save(booking);
        
        // Update seat status back to COMPANY_ALLOCATED
        Seat seat = booking.getSeat();
        seat.setStatus(Seat.SeatStatus.COMPANY_ALLOCATED);
        seatRepository.save(seat);
        
        return convertToDTO(updatedBooking);
    }

    @Override
    @Transactional
    public TableBookingDTO cancelBooking(Long id, Long employeeId)
            throws ResourceNotFoundException, IllegalStateException {
        
        TableBooking booking = tableBookingRepository.findByIdAndEmployeeId(id, employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id + " for employee: " + employeeId));
        
        // Verify booking can be cancelled
        if (booking.getStatus() != TableBookingStatus.PENDING && booking.getStatus() != TableBookingStatus.CONFIRMED) {
            throw new IllegalStateException("Booking cannot be cancelled: current status is " + booking.getStatus());
        }
        
        booking.setStatus(TableBookingStatus.CANCELLED);
        
        TableBooking updatedBooking = tableBookingRepository.save(booking);
        
        // Update seat status back to COMPANY_ALLOCATED
        Seat seat = booking.getSeat();
        seat.setStatus(Seat.SeatStatus.COMPANY_ALLOCATED);
        seatRepository.save(seat);
        
        return convertToDTO(updatedBooking);
    }
    
    private TableBookingDTO convertToDTO(TableBooking booking) {
        return TableBookingDTO.builder()
                .id(booking.getId())
                .employeeId(booking.getEmployee().getId())
                .employeeName(booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName())
                .seatId(booking.getSeat().getId())
                .seatNumber(booking.getSeat().getSeatNumber())
                .workspaceId(booking.getSeat().getWorkspace().getId())
                .workspaceName(booking.getSeat().getWorkspace().getName())
                .bookingDate(booking.getBookingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .checkInTime(booking.getCheckInTime())
                .checkOutTime(booking.getCheckOutTime())
                .status(booking.getStatus().toString())
                .notes(booking.getNotes())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
