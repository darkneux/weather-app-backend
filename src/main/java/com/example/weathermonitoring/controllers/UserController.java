package com.example.weathermonitoring.controllers;

import com.example.weathermonitoring.entity.User;
import com.example.weathermonitoring.entity.UserAlert;
import com.example.weathermonitoring.alerts.UserAlertConditionType;
import com.example.weathermonitoring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/alerts")
    public ResponseEntity<UserAlert> addUserAlert(@RequestBody Map<String, Object> requestBody) {
        Long userId = ((Number) requestBody.get("userId")).longValue();
        UserAlertConditionType conditionType = UserAlertConditionType.valueOf((String) requestBody.get("conditionType"));
        Double threshold = ((Number) requestBody.get("threshold")).doubleValue();

        UserAlert userAlert = userService.addUserAlert(userId, conditionType, threshold);
        return ResponseEntity.ok(userAlert);
    }


    @GetMapping("/{id}/alerts")
    public ResponseEntity<List<UserAlert>> getUserAlerts(@PathVariable Long id) {
        List<UserAlert> alerts = userService.getUserAlerts(id);
        return ResponseEntity.ok(alerts);
    }


    @GetMapping("/alerts/condition/{conditionType}")
    public ResponseEntity<List<User>> getUsersByAlertCondition(@PathVariable UserAlertConditionType conditionType) {
        List<User> users = userService.getUsersByAlertCondition(conditionType);
        return ResponseEntity.ok(users);
    }
}
