package com.roleledger.repository;

import com.roleledger.entity.Application;
import com.roleledger.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserId(Long userId);
    
    @Query("SELECT a.status, COUNT(a) FROM Application a WHERE a.user.id = :userId GROUP BY a.status")
    List<Object[]> countByStatusAndUserId(@Param("userId") Long userId);
    
    long countByUserId(Long userId);
}
