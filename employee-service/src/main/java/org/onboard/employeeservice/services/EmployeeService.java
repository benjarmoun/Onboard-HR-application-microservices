package org.onboard.employeeservice.services;

import jakarta.servlet.http.HttpServletRequest;
import org.onboard.employeeservice.dto.EmployeeInputDto;
import org.onboard.employeeservice.dto.EmployeeOutputDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.exeptions.EmployeeEmailExistException;
import org.onboard.employeeservice.exeptions.EmployeeIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmployeeService {
    List<Employee> allEmployees();
    Employee createEmployee(EmployeeInputDto EmployeeInputDto) throws MissingFieldsException, EmployeeEmailExistException;
    void deleteEmployee(Long id) throws EmployeeIdNotFoundException;
    EmployeeOutputDto updateEmployee(Long id, EmployeeInputDto EmployeeInputDto) throws MissingFieldsException, EmployeeIdNotFoundException;

    UserDetails findByEmail(String email);

    Employee getEmployeeByEmail(String email);

    Employee getEmployeeById(Long id);

    List<Employee>  getEmployeesWithNoContract();

    Employee  getEmployeesByContractId(Contract id);

    EmployeeOutputDto addContractToEmployee(Long employeeId, Contract contract) throws EmployeeIdNotFoundException;

    String getEmail(HttpServletRequest httpSerReq);

    Long getEmpId(HttpServletRequest httpSerReq);
}
