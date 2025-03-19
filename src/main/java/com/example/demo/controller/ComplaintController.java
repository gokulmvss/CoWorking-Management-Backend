package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.CreateComplaintRequest;
import com.example.demo.controller.dto.request.UpdateComplaintStatusRequest;
import com.example.demo.controller.dto.response.ComplaintResponseDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.service.ComplaintService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:4200/")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // ✅ Get all complaints
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // ✅ Get complaint by ID
    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }

    // ✅ Get complaints by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Complaint>> getComplaintsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(complaintService.getComplaintsByEmployeeId(employeeId));
    }

    // ✅ Get complaints by workspace ID
    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<Complaint>> getComplaintsByWorkspace(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(complaintService.getComplaintsByWorkspace(workspaceId));
    }

    // ✅ Get complaints by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Complaint>> getComplaintsByStatus(@PathVariable ComplaintStatus status) {
        return ResponseEntity.ok(complaintService.getComplaintsByStatus(status));
    }

    // ✅ Create a new complaint
    @PostMapping("/employee/{employeeId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ComplaintResponseDTO> createComplaint(
            @PathVariable Long employeeId,
            @Valid @RequestBody CreateComplaintRequest request) {
    	ComplaintResponseDTO createdComplaint = complaintService.createComplaint(employeeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComplaint);
    }

    // ✅ Update complaint status
    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintResponseDTO> updateComplaintStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComplaintStatusRequest request,
            @RequestParam Long userId) {
    	ComplaintResponseDTO updatedComplaint = complaintService.updateComplaintStatus(id, request, userId);
        return ResponseEntity.ok(updatedComplaint);
    }
}

