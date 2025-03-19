package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findBySubmittedById(Long employeeId);
    List<Complaint> findByWorkspaceId(Long workspaceId);
    List<Complaint> findByStatus(ComplaintStatus status);
}

