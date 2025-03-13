package com.example.demo.service;

import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateEmployeeRequest;
import com.example.demo.controller.dto.request.UpdateEmployeeRequest;
import com.example.demo.controller.dto.response.EmployeeDTO;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Create a new employee for a company
     *
     * @param companyId The company ID
     * @param request The employee details
     * @return The created employee DTO
     * @throws ResourceNotFoundException if company not found
     */
    EmployeeDTO createEmployee(Long companyId, CreateEmployeeRequest request) 
            throws ResourceNotFoundException;

    /**
     * Get an employee by ID
     *
     * @param id The employee ID
     * @return The employee DTO
     * @throws ResourceNotFoundException if employee not found
     */
    EmployeeDTO getEmployeeById(Long id) throws ResourceNotFoundException;

    /**
     * Get an employee by user ID
     *
     * @param userId The user ID
     * @return The employee DTO
     * @throws ResourceNotFoundException if employee not found
     */
    EmployeeDTO getEmployeeByUserId(Long userId) throws ResourceNotFoundException;

    /**
     * Update an employee's details
     *
     * @param id The employee ID
     * @param request The updated employee details
     * @return The updated employee DTO
     * @throws ResourceNotFoundException if employee not found
     */
    EmployeeDTO updateEmployee(Long id, UpdateEmployeeRequest request) 
            throws ResourceNotFoundException;

    /**
     * Get all employees for a company
     *
     * @param companyId The company ID
     * @return List of employee DTOs
     * @throws ResourceNotFoundException if company not found
     */
    List<EmployeeDTO> getAllEmployeesByCompany(Long companyId) 
            throws ResourceNotFoundException;

    /**
     * Get available seats for an employee to book
     *
     * @param employeeId The employee ID
     * @return List of available seat DTOs
     * @throws ResourceNotFoundException if employee not found
     */
    List<SeatDTO> getAvailableSeatsForEmployee(Long employeeId) 
            throws ResourceNotFoundException;

    /**
     * Get available workspaces for an employee's company
     *
     * @param employeeId The employee ID
     * @return List of workspace DTOs
     * @throws ResourceNotFoundException if employee not found
     */
    List<WorkspaceDTO> getAvailableWorkspacesForEmployee(Long employeeId) 
            throws ResourceNotFoundException;

    /**
     * Get employee entity by ID (for internal service use)
     *
     * @param id The employee ID
     * @return The employee entity
     * @throws ResourceNotFoundException if employee not found
     */
    Employee getEmployeeEntityById(Long id) throws ResourceNotFoundException;
}