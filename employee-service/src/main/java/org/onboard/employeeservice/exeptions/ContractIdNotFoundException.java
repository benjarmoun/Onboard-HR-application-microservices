package org.onboard.employeeservice.exeptions;

public class ContractIdNotFoundException extends Exception {
    public ContractIdNotFoundException(Long id) {
        super("Contract with Id: " + id + ", Not Found!!!!");
    }
}
