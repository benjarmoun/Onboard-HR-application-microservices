package org.onboard.employeeservice.dto;

import lombok.Data;
import org.onboard.employeeservice.entities.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Getter
//@Setter
@NoArgsConstructor
@Data
public class EmployeeInputDto {
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private Contract contract;

}
