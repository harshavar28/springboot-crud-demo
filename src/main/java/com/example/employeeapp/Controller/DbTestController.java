package com.example.employeeapp.controller;

import com.example.employeeapp.model.Employee;
import com.example.employeeapp.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestController {

    private final EmployeeRepository repo;

    public DbTestController(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/test-db")
    public String testDb() {
        repo.save(new Employee(null, "Harsha", "harsha@mail.com", 50000));
        return "Employee saved to H2 successfully";
    }
}
