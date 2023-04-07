package org.onboard.employeeservice.models;

import lombok.Getter;
import lombok.Setter;
import org.onboard.employeeservice.entities.Employee;

import java.util.Date;

@Getter
@Setter
public class Leave {
    private Long id;
    private Long employeeId;
    private Long numbOfDays;
    private Date startDate;
    private Date endDate;
    private String  status;
    private String type;
    private Employee employee;
}
