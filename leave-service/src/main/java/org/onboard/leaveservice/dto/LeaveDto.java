package org.onboard.leaveservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Data
public class LeaveDto {
    private Long employeeId;
    private Long numbOfDays;
    private Date startDate;
    private Date endDate;
    private String status;
    private String type;

}
