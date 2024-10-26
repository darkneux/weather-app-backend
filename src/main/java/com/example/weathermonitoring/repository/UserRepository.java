package com.example.weathermonitoring.repository;

import com.example.weathermonitoring.alerts.UserAlertConditionType;
import com.example.weathermonitoring.entity.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserAlerts_ConditionType(UserAlertConditionType conditionType);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userAlerts WHERE u.id = :id")
    Optional<User> findByIdWithAlerts(@Param("id") Long id);


//    Page<User> findByUserAlerts_ConditionType(UserAlertConditionType conditionType, Pageable pageable);


}
