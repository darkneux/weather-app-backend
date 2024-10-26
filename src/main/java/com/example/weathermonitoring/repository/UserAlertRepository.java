package com.example.weathermonitoring.repository;

import com.example.weathermonitoring.entity.UserAlert;
import com.example.weathermonitoring.alerts.UserAlertConditionType;
import com.example.weathermonitoring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAlertRepository extends JpaRepository<UserAlert, Long> {

    @Query("SELECT ua FROM UserAlert ua WHERE ua.user.id = :userId")
    List<UserAlert> findByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM User u JOIN u.userAlerts ua WHERE ua.conditionType = :conditionType")
    List<User> findUsersByConditionType(@Param("conditionType") UserAlertConditionType conditionType);

    @Query("SELECT ua FROM UserAlert ua JOIN FETCH ua.user WHERE ua.user.id = :userId")
    List<UserAlert> findByUserIdWithUser(@Param("userId") Long userId);

}
