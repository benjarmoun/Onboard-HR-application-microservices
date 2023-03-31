package org.onboard.leaveservice.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private Contract contract;
}
