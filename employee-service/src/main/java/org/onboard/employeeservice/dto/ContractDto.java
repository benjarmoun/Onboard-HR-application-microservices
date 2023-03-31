package org.onboard.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ContractDto {
    private Long salary;
    private String type;
    private String fonction;
    private LocalDate startDate;
    private LocalDate endtDate;
    private Long employeeId;

}
