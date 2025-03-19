package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ComplaintRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WorkspaceRepository;
import com.example.demo.controller.dto.request.CreateComplaintRequest;
import com.example.demo.controller.dto.request.UpdateComplaintStatusRequest;
import com.example.demo.controller.dto.response.ComplaintResponseDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.entity.Workspace;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.ComplaintService;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ComplaintServiceImpl(
            ComplaintRepository complaintRepository,
            EmployeeRepository employeeRepository,
            WorkspaceRepository workspaceRepository,
            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.employeeRepository = employeeRepository;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
    }

    public List<Complaint> getComplaintsByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
        return complaintRepository.findBySubmittedById(employeeId);
    }

    public List<Complaint> getComplaintsByWorkspace(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new RuntimeException("Workspace not found with ID: " + workspaceId);
        }
        return complaintRepository.findByWorkspaceId(workspaceId);
    }

    public List<Complaint> getComplaintsByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }

//    public Complaint createComplaint(Long employeeId, CreateComplaintRequest request) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
//
//        Workspace workspace = null;
//        if (request.getWorkspaceId() != null) {
//            workspace = workspaceRepository.findById(request.getWorkspaceId())
//                    .orElseThrow(() -> new RuntimeException("Workspace not found with ID: " + request.getWorkspaceId()));
//        }
//
//        Complaint complaint = new Complaint();
//        complaint.setTitle(request.getTitle());
//        complaint.setDescription(request.getDescription());
//        complaint.setSubmittedBy(employee);
//        complaint.setWorkspace(workspace);
//        complaint.setStatus(ComplaintStatus.OPEN);
//        complaint.setCreatedAt(LocalDateTime.now());
//        complaint.setUpdatedAt(LocalDateTime.now());
//
//        return complaintRepository.save(complaint);
//    }
    public ComplaintResponseDTO createComplaint(Long employeeId, CreateComplaintRequest request) {
        // Fetch the employee, throwing a custom exception if not found
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee"+employeeId+"Not found"));
        
        System.out.println(employee);

        // Fetch the workspace if workspaceId is provided, throwing a custom exception if not found
        Workspace workspace = null;
        if (request.getWorkspaceId() != null) {
            workspace = workspaceRepository.findById(request.getWorkspaceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Workspace ID"+request.getWorkspaceId()+" not found"));
        }
        System.out.println(workspace);
        
        // Create a new complaint and set its properties
        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setSubmittedBy(employee);
        complaint.setWorkspace(workspace);
        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());

        // Save the complaint to the database
        Complaint savedComplaint = complaintRepository.save(complaint);
        System.out.println(savedComplaint.getWorkspace());
        
        // Convert and return the Complaint entity as a ComplaintResponseDTO
        return toComplaintResponseDTO(savedComplaint);
    }


    public ComplaintResponseDTO updateComplaintStatus(Long id, UpdateComplaintStatusRequest request, Long userId) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        complaint.setStatus(request.getStatus());
        complaint.setResolutionNotes(request.getResolutionNotes());
        complaint.setResolvedBy(user);
        complaint.setResolvedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());

     // Save the updated complaint
        Complaint updatedComplaint = complaintRepository.save(complaint);

        // Convert the updated complaint to ComplaintResponseDTO and return it
        return toComplaintResponseDTO(updatedComplaint);
        
    }
    
    public ComplaintResponseDTO toComplaintResponseDTO(Complaint complaint) {
        ComplaintResponseDTO dto = new ComplaintResponseDTO();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setStatus(complaint.getStatus());

     // Map EmployeeBasicInfo
        if (complaint.getSubmittedBy() != null) {
            ComplaintResponseDTO.EmployeeBasicInfo employeeInfo = new ComplaintResponseDTO.EmployeeBasicInfo();
            Employee employee = complaint.getSubmittedBy();
            employeeInfo.setId(employee.getId());
            employeeInfo.setFirstName(employee.getFirstName());
            employeeInfo.setLastName(employee.getLastName());
            employeeInfo.setEmail(employee.getEmail());
            employeeInfo.setPhone(employee.getPhone());
//            System.out.println(employeeInfo);
            dto.setSubmittedBy(employeeInfo);
        }

        // Map WorkspaceBasicInfo
        if (complaint.getWorkspace() != null) {
            ComplaintResponseDTO.WorkspaceBasicInfo workspaceInfo = new ComplaintResponseDTO.WorkspaceBasicInfo();
            Workspace workspace = complaint.getWorkspace();
            workspaceInfo.setId(workspace.getId());
            workspaceInfo.setName(workspace.getName());
            workspaceInfo.setLocation(workspace.getLocation());
            workspaceInfo.setType(workspace.getType());
//            System.out.println(workspaceInfo);
            dto.setWorkspace(workspaceInfo);
        }

        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setUpdatedAt(complaint.getUpdatedAt());

        return dto;
    }

}
