package com.example.demo.Repository;

import com.example.demo.entity.TableBooking;
import com.example.demo.entity.TableBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
    
    List<TableBooking> findByEmployeeId(Long employeeId);
    
    List<TableBooking> findByEmployeeIdAndStatusIn(Long employeeId, List<TableBookingStatus> statuses);
    
    @Query("SELECT tb FROM TableBooking tb WHERE tb.employee.id = :employeeId ORDER BY tb.bookingDate DESC")
    List<TableBooking> findByEmployeeIdOrderByBookingDateDesc(@Param("employeeId") Long employeeId);
    
    @Query("SELECT tb FROM TableBooking tb WHERE tb.seat.id = :seatId AND tb.status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN') " +
           "AND ((tb.startTime <= :endTime AND tb.endTime >= :startTime))")
    List<TableBooking> findConflictingBookings(
            @Param("seatId") Long seatId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT tb FROM TableBooking tb WHERE tb.status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN') " +
           "AND tb.startTime >= :startOfDay AND tb.startTime < :endOfDay")
    List<TableBooking> findBookingsForDate(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
    
    List<TableBooking> findBySeatId(Long seatId);
    
    Optional<TableBooking> findByIdAndEmployeeId(Long id, Long employeeId);
}