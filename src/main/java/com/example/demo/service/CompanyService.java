package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.CreateCompanyRequest;
import com.example.demo.controller.dto.response.CompanyDTO;
import com.example.demo.entity.Company;
import com.example.demo.exceptions.ResourceNotFoundException;

public interface CompanyService {
    
    /**
     * Create a new company
     * 
     * @param request The company details
     * @return The created company DTO
     * @throws ConflictException if company with same name already exists
     */
    CompanyDTO createCompany(CreateCompanyRequest request) ;
    
    /**
     * Get all active companies
     * 
     * @return List of company DTOs
     */
    List<CompanyDTO> getAllCompanies();
    
    /**
     * Get a company by ID
     * 
     * @param id The company ID
     * @return The company DTO
     * @throws ResourceNotFoundException if company not found
     */
    CompanyDTO getCompanyById(Long id) throws ResourceNotFoundException;
    
    /**
     * Get a company with its allocated seats
     * 
     * @param id The company ID
     * @return The company DTO with allocated seats
     * @throws ResourceNotFoundException if company not found
     */
    CompanyDTO getCompanyWithAllocatedSeats(Long id) throws ResourceNotFoundException;
    
    /**
     * Get company entity by ID (for internal service use)
     * 
     * @param id The company ID
     * @return The company entity
     * @throws ResourceNotFoundException if company not found
     */
    Company getCompanyEntityById(Long id) throws ResourceNotFoundException;
    
    List<Company> findAll();
    Company findById(Long id);
    boolean existsByEmail(Object object);
    Company saveCompany(Company company);
    void deleteCompany(Long id);
}