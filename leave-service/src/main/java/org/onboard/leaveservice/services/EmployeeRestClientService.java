package org.onboard.leaveservice.services;

import org.onboard.leaveservice.models.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "employee-service")
public interface EmployeeRestClientService {
    @GetMapping("employees/{id}")
    public Employee employeeById(@PathVariable Long id);

    @GetMapping("employees")
    public PagedModel<Employee> allEmployees();
}
