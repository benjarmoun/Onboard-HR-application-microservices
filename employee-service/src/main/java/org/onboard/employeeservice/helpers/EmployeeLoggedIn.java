package org.onboard.employeeservice.helpers;

import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EmployeeLoggedIn {
    private Employee employee;
    private String Token;
}
