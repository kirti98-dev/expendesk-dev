package com.example.multitenancy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
    private Integer employeeId;
    private String employeeName;
    private String employeeCode;

    // Constructor to populate DTO from Employee entity
    public EmployeeDTO(Integer employeeId, String employeeName, String employeeCode) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeCode = employeeCode;
    }

    // Custom method to format display value
    public String getDisplayName() {
        return employeeName + " (" + employeeCode + ")";
    }
}
