package org.onboard.leaveservice.entites;

import jakarta.persistence.*;
import lombok.*;
import org.onboard.leaveservice.models.Employee;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Basic
    @Column(name = "numb_of_days", nullable = false)
    private Long numbOfDays;

    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Basic
    @Column(name = "status", nullable = false)
    private String  status;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @Transient
    private Employee employee;

}
