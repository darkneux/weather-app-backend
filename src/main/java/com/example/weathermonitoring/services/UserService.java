package com.example.weathermonitoring.services;

import com.example.weathermonitoring.entity.User;
import com.example.weathermonitoring.entity.UserAlert;
import com.example.weathermonitoring.alerts.UserAlertConditionType;
import com.example.weathermonitoring.repository.UserRepository;
import com.example.weathermonitoring.repository.UserAlertRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserAlertRepository userAlertRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserAlertRepository userAlertRepository) {
        this.userRepository = userRepository;
        this.userAlertRepository = userAlertRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        return user;
    }


    public UserAlert addUserAlert(Long userId, UserAlertConditionType conditionType, Double threshold) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (conditionType == UserAlertConditionType.TEMPERATURE) {
            boolean alertWithSameThresholdExists = user.getUserAlerts().stream()
                    .anyMatch(alert -> alert.getConditionType() == conditionType && alert.getThreshold().equals(threshold));

            if (alertWithSameThresholdExists) {
                throw new IllegalArgumentException("User already has an alert for this temperature threshold: " + threshold);
            }
        } else {
            boolean alertExists = user.getUserAlerts().stream()
                    .anyMatch(alert -> alert.getConditionType() == conditionType);

            if (alertExists) {
                throw new IllegalArgumentException("User already has an alert for this condition type: " + conditionType);
            }
        }

        UserAlert userAlert = new UserAlert();
        userAlert.setUser(user);
        userAlert.setConditionType(conditionType);
        userAlert.setThreshold(threshold);

        return userAlertRepository.save(userAlert);
    }


    public List<UserAlert> getUserAlerts(Long userId) {
        User user = getUserById(userId);
        return user.getUserAlerts().stream().toList();
    }

    public List<User> getUsersByAlertCondition(UserAlertConditionType conditionType) {
        return userAlertRepository.findUsersByConditionType(conditionType);
    }
}
