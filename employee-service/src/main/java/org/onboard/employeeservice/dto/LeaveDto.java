package org.onboard.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class LeaveDto {
    private Long employeeId;
    private Double numbOfDays;
    private Date startDate;
    private Date endDate;
    private String status;
    private String type;

}
