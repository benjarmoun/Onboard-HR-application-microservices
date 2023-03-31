package org.onboard.leaveservice.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Contract {
    private Long id;
    private Long salary;
    private String type;
    private String fonction;
    private Employee employee;
    private Date startDate;
    private Date endDate;
}
