package org.onboard.employeeservice.exeptions;

public class EmployeeEmailExistException extends Exception {
    public EmployeeEmailExistException(String email) {
        super("Professor with NIC: " + email + ", Already Exist!!!!!");
    }
}
