package org.onboard.employeeservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "salary", nullable = false)
    private Long salary;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @Basic
    @Column(name = "fonction", nullable = false)
    private String fonction;

    @JsonIgnore
    @OneToOne(mappedBy = "contract")
    private Employee employee;

    @Basic
    @Column
    private LocalDate startDate;

    @Basic
    @Column
    private LocalDate endDate;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", salary=" + salary +
                ", type='" + type + '\'' +
                ", fonction='" + fonction + '\'' +
//                ", employee=" + employee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
