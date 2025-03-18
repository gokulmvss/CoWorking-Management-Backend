package com.example.demo.controller;

import com.example.demo.controller.dto.request.AddEmployeeRequest;
import com.example.demo.controller.dto.request.PasswordResetRequest;
import com.example.demo.controller.dto.request.RegisterCompanyRequest;
import com.example.demo.controller.dto.request.RegisterSpaceOwnerRequest;
import com.example.demo.controller.dto.response.ApiResponse;
import com.example.demo.controller.dto.response.CompanyDTO;
import com.example.demo.controller.dto.response.EmployeeCredentialsDto;
import com.example.demo.controller.dto.response.LoginRequest;
import com.example.demo.controller.dto.response.UserDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.CompanyService;
import com.example.demo.service.CredentialGeneratorService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", allowCredentials = "true")
public class AuthController {
	@GetMapping("/test")
    public ApiResponse<String> test() {
        return new ApiResponse<>(true, "Public endpoint working", "Success");
    }
	
	private final UserService userService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final CredentialGeneratorService credentialGeneratorService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UserService userService,CompanyService companyService,EmployeeService employeeService,
    		CredentialGeneratorService credentialGeneratorService,AuthenticationManager authenticationManager) {
    	this.authenticationManager=authenticationManager;
    	this.companyService=companyService;
    	this.credentialGeneratorService = credentialGeneratorService;
    	this.employeeService=employeeService;
    	this.userService=userService;
    }
    
    @GetMapping("/current-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Cast to your User class
        UserDto userDto = mapUserToDto(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Current user retrieved successfully", userDto));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Create authentication object
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Get authenticated user details
        User user = userService.findByEmail(loginRequest.getEmail());
        UserDto userDto = mapUserToDto(user);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Authentication successful", userDto));
    }
    
    @PostMapping("/register/space-owner")
    public ResponseEntity<ApiResponse<UserDto>> registerSpaceOwner(
            @Valid @RequestBody RegisterSpaceOwnerRequest request) {
        
        // Check if email is already in use
        if (userService.findOptionalByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Email already in use", null));
        }
        
        // Create new user with SPACE_OWNER role
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.SPACE_OWNER);
        
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Will be encoded by UserService
        user.setRoles(roles);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        
        User savedUser = userService.saveUser(user);
        UserDto userDto = mapUserToDto(savedUser);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Space owner registered successfully", userDto));
    }
    
    @PostMapping("/register/company")
    public ResponseEntity<ApiResponse<CompanyDTO>> registerCompany(
            @Valid @RequestBody RegisterCompanyRequest request) {
        
        // Check if company email or admin email already exists
        if (companyService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Company email already in use", null));
        }
        
//        if (userService.findOptionalByEmail(request.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(new ApiResponse<>(false, "Admin email already in use", null));
//        }
        
    
        Company company = new Company();
        company.setName(request.getCompanyName());
        company.setAddress(request.getAddress());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setDescription(request.getDescription());
        company.setActive(true);
        
        Company savedCompany = companyService.saveCompany(company);
        
        // Create company admin user
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.COMPANY_ADMIN);
        
        User adminUser = new User();
        adminUser.setFirstName(request.getAdminFirstName());
        adminUser.setLastName(request.getAdminLastName());
        adminUser.setEmail(request.getAdminEmail());
        adminUser.setPassword(request.getPassword()); // Will be encoded by UserService
        adminUser.setRoles(roles);
        adminUser.setActive(true);
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setCompanyId(savedCompany.getId());
        
        userService.saveUser(adminUser);
        
        // Return company data
        CompanyDTO companyDto = mapCompanyToDto(savedCompany);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Company registered successfully", companyDto));
    }
    
//    @PostMapping("/company/{companyId}/add-employee")
//    @PreAuthorize("hasRole('COMPANY_ADMIN')")
//    public ResponseEntity<ApiResponse<EmployeeCredentialsDto>> addEmployee(
//            @PathVariable Long companyId,
//            @Valid @RequestBody AddEmployeeRequest request) {
//        
//        // Get authentication from security context instead of parameter
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new ApiResponse<>(false, "Authentication required", null));
//        }
//        
//        // Get username from authentication
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        
//        try {
//            // Get the authenticated user
//            User currentUser = userService.findByEmail(userDetails.getUsername());
//            System.out.println("Authenticated User: " + userDetails.getUsername());
//            
//            // Check if the authenticated user is associated with the requested company
//            if (currentUser.getCompanyId() == null || !companyId.equals(currentUser.getCompanyId())) {
//                ApiResponse<EmployeeCredentialsDto> response = new ApiResponse<>(
//                    false, "You are not authorized to manage employees for this company", null);
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
//            }
//            
//            // Check if company exists
//            Company company = companyService.findById(companyId);
//            
//            // Check if employee email already exists
//            if (employeeService.existsByEmail(request.getEmail())) {
//                return ResponseEntity.status(HttpStatus.CONFLICT)
//                        .body(new ApiResponse<>(false, "Employee email already in use", null));
//            }
//            
//            // Create employee
//            Employee employee = new Employee();
//            employee.setFirstName(request.getFirstName());
//            employee.setLastName(request.getLastName());
//            employee.setEmail(request.getEmail());
//            employee.setPhone(request.getPhone());
//            employee.setJobTitle(request.getJobTitle());
//            employee.setDepartment(request.getDepartment());
//            employee.setActive(true);
//            employee.setCompany(company);
//            
//            Employee savedEmployee = employeeService.saveEmployee(employee);
//            
//            // Generate credentials for the employee
//            EmployeeCredentialsDto credentials = credentialGeneratorService.generateCredentials(savedEmployee, company);
//            
//            // Create user account for the employee
//            Set<UserRole> roles = new HashSet<>();
//            roles.add(UserRole.EMPLOYEE);
//            
//            User employeeUser = new User();
//            employeeUser.setFirstName(savedEmployee.getFirstName());
//            employeeUser.setLastName(savedEmployee.getLastName());
//            employeeUser.setEmail(savedEmployee.getEmail());
//            employeeUser.setPassword(credentials.getPassword()); // Will be encoded by UserService
//            employeeUser.setRoles(roles);
//            employeeUser.setActive(true);
//            employeeUser.setCreatedAt(LocalDateTime.now());
//            // Set the company ID for the employee user
//            employeeUser.setCompanyId(companyId);
//            
//            User savedUser = userService.saveUser(employeeUser);
//            
//            // Update employee with user ID reference
//            savedEmployee.setUserId(savedUser.getId());
//            employeeService.saveEmployee(savedEmployee);
//            
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ApiResponse<>(true, "Employee added successfully", credentials));
//            
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(false, "Error adding employee: " + e.getMessage(), null));
//        }
//    }
    
    @PostMapping("/company/{companyId}/add-employee")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeCredentialsDto>> addEmployee(
            @PathVariable Long companyId,
            @Valid @RequestBody AddEmployeeRequest request) {
        
        // Get authentication from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Authentication required", null));
        }
        
        User currentUser = null;
        // Handle different principal types
        if (authentication.getPrincipal() instanceof User) {
            currentUser = (User) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            currentUser = userService.findByEmail(username);
        } else if (authentication.getPrincipal() instanceof String) {
            currentUser = userService.findByEmail((String) authentication.getPrincipal());
        }
        
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "User not found", null));
        }
        
        // Check if user has permission for this company
        if (currentUser.getCompanyId() == null || !companyId.equals(currentUser.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(false, "You are not authorized to manage employees for this company", null));
        }
        
        // Check if company exists
        Company company = companyService.findById(companyId);
        
        // Check if employee email already exists
        if (employeeService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, "Employee email already in use", null));
        }
        
        // Create employee
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setJobTitle(request.getJobTitle());
        employee.setDepartment(request.getDepartment());
        employee.setActive(true);
        employee.setCompany(company);
        
        Employee savedEmployee = employeeService.saveEmployee(employee);
        
        // Generate credentials
        EmployeeCredentialsDto credentials = credentialGeneratorService.generateCredentials(savedEmployee, company);
        
        // Create user account for employee
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.EMPLOYEE);
        
        User employeeUser = new User();
        employeeUser.setFirstName(savedEmployee.getFirstName());
        employeeUser.setLastName(savedEmployee.getLastName());
        employeeUser.setEmail(savedEmployee.getEmail());
        employeeUser.setPassword(credentials.getPassword());
        employeeUser.setRoles(roles);
        employeeUser.setActive(true);
        employeeUser.setCreatedAt(LocalDateTime.now());
        employeeUser.setCompanyId(companyId);
        
        User savedUser = userService.saveUser(employeeUser);
        
        // Update employee with user ID
        savedEmployee.setUserId(savedUser.getId());
        employeeService.saveEmployee(savedEmployee);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Employee added successfully", credentials));
    }
    
//    @PostMapping("/company/{companyId}/add-employee")
//    @PreAuthorize("hasRole('COMPANY_ADMIN')")
//    public ResponseEntity<ApiResponse<EmployeeCredentialsDto>> addEmployee(
//            @PathVariable Long companyId,
//            @Valid @RequestBody AddEmployeeRequest request,
//            @AuthenticationPrincipal UserDetails userDetails) {
//    	
//    	// Get the authenticated user
//        User currentUser = userService.findByEmail(userDetails.getUsername());
//        System.out.println("Authenticated User: " + userDetails.getUsername());
//
//        // Check if the authenticated user is associated with the requested company
//        if (!companyId.equals(currentUser.getCompanyId())) {
//        	ApiResponse<EmployeeCredentialsDto> response = new ApiResponse<>(
//                false, "You are not authorized to manage employees for this company", null);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
//        }
//        
//        // Check if company exists
//        Company company = companyService.findById(companyId);
//        
//        // Check if employee email already exists
//        if (employeeService.existsByEmail(request.getEmail())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(new ApiResponse<>(false, "Employee email already in use", null));
//        }
//        
//        // Create employee
////        Employee employee = Employee.builder()
////                .firstName(request.getFirstName())
////                .lastName(request.getLastName())
////                .email(request.getEmail())
////                .phone(request.getPhone())
////                .jobTitle(request.getJobTitle())
////                .department(request.getDepartment())
////                .active(true)
////                .company(company)
////                .build();
//        Employee employee = new Employee();
//        employee.setFirstName(request.getFirstName());
//        employee.setLastName(request.getLastName());
//        employee.setEmail(request.getEmail());
//        employee.setPhone(request.getPhone());
//        employee.setJobTitle(request.getJobTitle());
//        employee.setDepartment(request.getDepartment());
//        employee.setActive(true);
//        employee.setCompany(company);
//        
//        Employee savedEmployee = employeeService.saveEmployee(employee);
//        
//        // Generate credentials for the employee
//        EmployeeCredentialsDto credentials = credentialGeneratorService.generateCredentials(savedEmployee, company);
//        
//        // Create user account for the employee
//        Set<UserRole> roles = new HashSet<>();
//        roles.add(UserRole.EMPLOYEE);
//        
////        User employeeUser = User.builder()
////                .firstName(savedEmployee.getFirstName())
////                .lastName(savedEmployee.getLastName())
////                .email(savedEmployee.getEmail())
////                .password(credentials.getPassword()) // Will be encoded by UserService
////                .roles(roles)
////                .active(true)
////                .createdAt(LocalDateTime.now())
////                .build();
//        
//        User employeeUser = new User();
//        employeeUser.setFirstName(savedEmployee.getFirstName());
//        employeeUser.setLastName(savedEmployee.getLastName());
//        employeeUser.setEmail(savedEmployee.getEmail());
//        employeeUser.setPassword(credentials.getPassword()); // Will be encoded by UserService
//        employeeUser.setRoles(roles);
//        employeeUser.setActive(true);
//        employeeUser.setCreatedAt(LocalDateTime.now());
//        
//        User savedUser = userService.saveUser(employeeUser);
//        
//        // Update employee with user ID reference
//        savedEmployee.setUserId(savedUser.getId());
//        employeeService.saveEmployee(savedEmployee);
//        
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ApiResponse<>(true, "Employee added successfully", credentials));
//    }
    
    @PostMapping("/password-reset-request")
    public ResponseEntity<ApiResponse<Void>> requestPasswordReset(
            @Valid @RequestBody PasswordResetRequest request) {
        // Check if email exists
        if (userService.findOptionalByEmail(request.getEmail()).isPresent()) {
            // TODO: Implement password reset functionality
            // For now, just return success
            return ResponseEntity.ok(new ApiResponse<>(true, "Password reset request processed", null));
        } else {
            // Don't reveal if email exists or not for security reasons
            return ResponseEntity.ok(new ApiResponse<>(true, "Password reset request processed", null));
        }
    }
    
//    @GetMapping("/current-user")
//   @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.findByEmail(userDetails.getUsername());
//        UserDto userDto = mapUserToDto(user);
//        
//        return ResponseEntity.ok(new ApiResponse<>(true, "Current user retrieved successfully", userDto));
//    }
    
   
    
    // Helper methods for mapping entities to DTOs
//    private UserDto mapUserToDto(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .roles(user.getRoles())
//                .active(user.getActive())
//                .build();
//    }
    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setActive(user.getActive());
        userDto.setCompanyId(user.getCompanyId());
        return userDto;
    }
    
    private CompanyDTO mapCompanyToDto(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .email(company.getEmail())
                .phone(company.getPhone())
                .description(company.getDescription())
                .active(company.getActive())
                .build();
    }
}
