package org.onboard.employeeservice.exeptions;

public class MissingFieldsException  extends Exception {
    public MissingFieldsException(){
        super("Missing Fields!!!");
    }
}
