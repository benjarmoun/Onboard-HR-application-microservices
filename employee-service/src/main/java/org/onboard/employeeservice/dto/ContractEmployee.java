package org.onboard.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.onboard.employeeservice.entities.Employee;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ContractEmployee {
    private Long salary;
    private String type;
    private String fonction;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long employeeId;
    private Employee employee;


}
