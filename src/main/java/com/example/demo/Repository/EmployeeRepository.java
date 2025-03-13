package com.example.demo.Repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyId(Long companyId);
    
    List<Employee> findByCompanyIdAndActiveTrue(Long companyId);
    
    Optional<Employee> findByIdAndActiveTrue(Long id);
    
    Optional<Employee> findByUserId(Long userId);
    
    Optional<Employee> findByUserIdAndActiveTrue(Long userId);
    
    boolean existsByEmail(String email);
    
    boolean existsByEmailAndIdNot(String email, Long id);
}