package org.onboard.employeeservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "fname", nullable = false)
    private String fname;

    @Basic
    @Column(name = "lname", nullable = false)
    private String lname;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    @Basic
    @Column(name = "role", nullable = false)
    private String role;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", nullable = true)
    private Contract contract;

    public Employee(String fName, String lName, String email, String password, String phone, String address, String role, Contract contract) {
        this.fname = fName;
        this.lname = lName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fName='" + fname + '\'' +
                ", lName='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", contract=" + contract +
                '}';
    }
}
