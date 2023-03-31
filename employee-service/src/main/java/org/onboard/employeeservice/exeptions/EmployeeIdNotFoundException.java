package org.onboard.employeeservice.exeptions;

public class EmployeeIdNotFoundException extends Exception{
    public EmployeeIdNotFoundException(Long id) {
        super("Employee with Id: " + id + ", Not Found!!!!");
    }
}
