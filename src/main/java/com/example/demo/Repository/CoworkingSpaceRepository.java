package com.example.demo.Repository;

import com.example.demo.entity.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoworkingSpaceRepository extends JpaRepository<CoworkingSpace, Long> {
    List<CoworkingSpace> findByActiveTrue();
    
    Optional<CoworkingSpace> findByIdAndActiveTrue(Long id);
    
    @Query("SELECT cs FROM CoworkingSpace cs LEFT JOIN FETCH cs.workspaces WHERE cs.id = :id")
    Optional<CoworkingSpace> findByIdWithWorkspaces(Long id);
    
    boolean existsByName(String name);

	boolean existsByContactEmail(String email);
}
