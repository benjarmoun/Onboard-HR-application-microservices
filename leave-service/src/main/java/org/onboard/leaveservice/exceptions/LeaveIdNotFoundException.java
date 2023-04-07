package org.onboard.leaveservice.exceptions;

public class LeaveIdNotFoundException extends Exception {
    public LeaveIdNotFoundException(Long id) {
        super("Leave with Id: " + id + ", Not Found!!!!");
    }
}
