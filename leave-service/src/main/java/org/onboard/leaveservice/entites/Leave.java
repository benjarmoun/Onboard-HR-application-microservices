package org.onboard.leaveservice.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.onboard.leaveservice.models.Employee;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "employee_id", nullable = false)
    private Long employee_id;

    @Basic
    @Column(name = "numb_of_days", nullable = false)
    private Double numb_of_days;

    @Basic
    @Column(name = "start_date", nullable = false)
    private Date start_date;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date end_date;

    @Basic
    @Column(name = "status", nullable = false)
    private String  status;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @Transient
    private Employee employee;

}
