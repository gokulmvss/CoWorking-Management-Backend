package com.example.demo.Repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByWorkspaceId(Long workspaceId);
    
    List<Seat> findByWorkspaceIdAndStatus(Long workspaceId, Seat.SeatStatus status);
    
    List<Seat> findByCompanyId(Long companyId);
    
    List<Seat> findByCompanyIdAndStatus(Long companyId, Seat.SeatStatus status);
    
//    @Query("SELECT s FROM Seat s WHERE s.workspace.id = :workspaceId AND s.status = 'AVAILABLE' ORDER BY s.id")
//    List<Seat> findAvailableSeatsByWorkspaceId(@Param("workspaceId") Long workspaceId, PageRequest pageRequest);
    List<Seat> findAvailableSeatsByWorkspaceId(Long workspaceId, PageRequest of);
    
    long countByWorkspaceIdAndStatus(Long workspaceId, Seat.SeatStatus status);
    
    boolean existsBySeatNumberAndWorkspaceId(String seatNumber, Long workspaceId);

	
}
