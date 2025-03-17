package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	List<Company> findByActiveTrue();

	Optional<Company> findByIdAndActiveTrue(Long id);

	boolean existsByName(String name);

	@Query("SELECT c FROM Company c LEFT JOIN FETCH c.allocatedSeats WHERE c.id = :id")
	Optional<Company> findByIdWithAllocatedSeats(@Param("id") Long id);

	Optional<Company> findByName(String name);

	Optional<Company> findByEmail(String email);

	boolean existsByEmail(String email);

}
