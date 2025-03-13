package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CompanyRepository;
import com.example.demo.controller.dto.ResourceNotFoundException;
import com.example.demo.controller.dto.request.CreateCompanyRequest;
import com.example.demo.controller.dto.response.CompanyDTO;
import com.example.demo.controller.dto.response.SeatDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Seat;
import com.example.demo.service.CompanyService;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public CompanyDTO createCompany(CreateCompanyRequest request)  {
//        if (companyRepository.existsByName(request.getName())) {
//            throw new ConflictException("Company with name " + request.getName() + " already exists");
//        }
        
        Company company = new Company();
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setDescription(request.getDescription());
        company.setActive(true);
        
        Company savedCompany = companyRepository.save(company);
        
        return convertToDTO(savedCompany, null);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findByActiveTrue();
        
        return companies.stream()
                .map(company -> convertToDTO(company, null))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO getCompanyById(Long id) throws ResourceNotFoundException {
        Company company = companyRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        
        return convertToDTO(company, null);
    }

    @Override
    public CompanyDTO getCompanyWithAllocatedSeats(Long id) throws ResourceNotFoundException {
        Company company = companyRepository.findByIdWithAllocatedSeats(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        
        List<SeatDTO> seatDTOs = company.getAllocatedSeats().stream()
                .map(this::convertToSeatDTO)
                .collect(Collectors.toList());
        
        return convertToDTO(company, seatDTOs);
    }

    @Override
    public Company getCompanyEntityById(Long id) throws ResourceNotFoundException {
        return companyRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }
    
    private CompanyDTO convertToDTO(Company company, List<SeatDTO> allocatedSeats) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .email(company.getEmail())
                .phone(company.getPhone())
                .description(company.getDescription())
                .active(company.getActive())
                .allocatedSeatsCount(company.getAllocatedSeats().size())
                .allocatedSeats(allocatedSeats)
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
}
