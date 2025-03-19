package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.CreateComplaintRequest;
import com.example.demo.controller.dto.request.UpdateComplaintStatusRequest;
import com.example.demo.controller.dto.response.ComplaintResponseDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;

public interface ComplaintService {
    List<Complaint> getAllComplaints();
    Complaint getComplaintById(Long id);
    List<Complaint> getComplaintsByEmployeeId(Long employeeId);
    List<Complaint> getComplaintsByWorkspace(Long workspaceId);
    List<Complaint> getComplaintsByStatus(ComplaintStatus status);
    ComplaintResponseDTO createComplaint(Long employeeId, CreateComplaintRequest request);
    ComplaintResponseDTO updateComplaintStatus(Long id, UpdateComplaintStatusRequest request, Long userId);
}

