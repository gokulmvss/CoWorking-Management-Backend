package com.example.demo.service.impl;
import com.example.demo.controller.dto.request.CreateEmployeeRequest;
import com.example.demo.controller.dto.request.UpdateEmployeeRequest;
import com.example.demo.controller.dto.response.EmployeeDTO;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.controller.dto.response.WorkspaceDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Seat;
import com.example.demo.entity.Workspace;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.SeatRepository;
import com.example.demo.Repository.WorkspaceRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final SeatRepository seatRepository;
    private final WorkspaceRepository workspaceRepository;
    
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    public List<Employee> findByCompanyId(Long companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }
    
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
    
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            CompanyService companyService,
            SeatRepository seatRepository,
            WorkspaceRepository workspaceRepository) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.seatRepository = seatRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(Long companyId, CreateEmployeeRequest request)
            throws ResourceNotFoundException {
        
        // Verify company exists
        Company company = companyService.getCompanyEntityById(companyId);
        
        // Check if email already exists
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Employee with email " + request.getEmail() + " already exists");
        }
        
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setJobTitle(request.getJobTitle());
        employee.setDepartment(request.getDepartment());
        employee.setActive(true);
        employee.setCompany(company);
        
        // Set user ID if provided
        if (request.getUserId() != null) {
            employee.setUserId(request.getUserId());
        }
        
        Employee savedEmployee = employeeRepository.save(employee);
        
        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        return convertToDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByUserId(Long userId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findByUserIdAndActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with user id: " + userId));
        
        return convertToDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, UpdateEmployeeRequest request)
            throws ResourceNotFoundException {
        
        Employee employee = employeeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        // Check if email is being changed and if it's already in use
        if (request.getEmail() != null && !request.getEmail().equals(employee.getEmail())) {
            if (employeeRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new ConflictException("Employee with email " + request.getEmail() + " already exists");
            }
            employee.setEmail(request.getEmail());
        }
        
        // Update other fields if provided
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }
        
        if (request.getJobTitle() != null) {
            employee.setJobTitle(request.getJobTitle());
        }
        
        if (request.getDepartment() != null) {
            employee.setDepartment(request.getDepartment());
        }
        
        if (request.getActive() != null) {
            employee.setActive(request.getActive());
        }
        
        Employee updatedEmployee = employeeRepository.save(employee);
        
        return convertToDTO(updatedEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesByCompany(Long companyId)
            throws ResourceNotFoundException {
        
        // Verify company exists
        companyService.getCompanyEntityById(companyId);
        
        List<Employee> employees = employeeRepository.findByCompanyIdAndActiveTrue(companyId);
        
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> getAvailableSeatsForEmployee(Long employeeId)
            throws ResourceNotFoundException {
        
        Employee employee = getEmployeeEntityById(employeeId);
        
        // Get seats allocated to the company with COMPANY_ALLOCATED status
        List<Seat> companyAllocatedSeats = seatRepository.findByCompanyIdAndStatus(
                employee.getCompany().getId(), Seat.SeatStatus.COMPANY_ALLOCATED);
        
        return companyAllocatedSeats.stream()
                .map(this::convertToSeatDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkspaceDTO> getAvailableWorkspacesForEmployee(Long employeeId)
            throws ResourceNotFoundException {
        
        Employee employee = getEmployeeEntityById(employeeId);
        
        // Get all workspaces with at least one seat allocated to the company
        List<Workspace> workspaces = new ArrayList<>();
        List<Seat> companyAllocatedSeats = seatRepository.findByCompanyIdAndStatus(
                employee.getCompany().getId(), Seat.SeatStatus.COMPANY_ALLOCATED);
        
        // Group seats by workspace
        companyAllocatedSeats.stream()
                .map(Seat::getWorkspace)
                .distinct()
                .forEach(workspaces::add);
        
        return workspaces.stream()
                .map(workspace -> convertToWorkspaceDTO(workspace, null))
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeEntityById(Long id) throws ResourceNotFoundException {
        return employeeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
    
    private EmployeeDTO convertToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .jobTitle(employee.getJobTitle())
                .department(employee.getDepartment())
                .active(employee.getActive())
                .companyId(employee.getCompany().getId())
                .companyName(employee.getCompany().getName())
                .userId(employee.getUserId())
                .createdAt(employee.getCreatedAt())
                .build();
    }
    
    private SeatDTO convertToSeatDTO(Seat seat) {
        return SeatDTO.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus().toString())
                .type(seat.getType())
                .features(seat.getFeatures())
                .workspaceId(seat.getWorkspace().getId())
                .workspaceName(seat.getWorkspace().getName())
                .companyId(seat.getCompany() != null ? seat.getCompany().getId() : null)
                .companyName(seat.getCompany() != null ? seat.getCompany().getName() : null)
                .build();
    }
    
    private WorkspaceDTO convertToWorkspaceDTO(Workspace workspace, List<SeatDTO> seats) {
        return WorkspaceDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .type(workspace.getType())
                .capacity(workspace.getCapacity())
                .location(workspace.getLocation())
                .pricePerSeatPerHour(workspace.getPricePerHour())
                .available(workspace.getAvailable())
                .coworkingSpaceId(workspace.getCoworkingSpace().getId())
                .coworkingSpaceName(workspace.getCoworkingSpace().getName())
                .totalSeats(workspace.getTotalSeatsCount())
                .availableSeats(workspace.getAvailableSeatsCount())
                .companyAllocatedSeats(workspace.getCompanyAllocatedSeatsCount())
                .employeeBookedSeats(workspace.getEmployeeBookedSeatsCount())
                .seats(seats)
                .build();
    }
}