package org.onboard.employeeservice.services;

import org.onboard.employeeservice.entities.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeRestClientService {
    @GetMapping("employees/{id}")
    public Employee employeeById(@PathVariable Long id);

    @GetMapping("employees")
    public List <Employee> allEmployees();
}
