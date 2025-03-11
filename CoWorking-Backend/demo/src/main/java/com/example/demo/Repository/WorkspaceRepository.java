package com.example.demo.Repository;

import com.example.demo.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByCoworkingSpaceId(Long coworkingSpaceId);
    
    List<Workspace> findByCoworkingSpaceIdAndAvailableTrue(Long coworkingSpaceId);
}