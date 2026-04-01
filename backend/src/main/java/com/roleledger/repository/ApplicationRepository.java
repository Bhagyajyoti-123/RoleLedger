package com.roleledger.repository;

import com.roleledger.entity.Application;
import com.roleledger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUser(User user);

    long countByUser(User user);

    @Query("SELECT a.status, COUNT(a) FROM Application a WHERE a.user = :user GROUP BY a.status")
    List<Object[]> countByStatusAndUser(@Param("user") User user);
}