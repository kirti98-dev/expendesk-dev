package com.example.multitenancy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;

    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id")
    private String emailId;

}
