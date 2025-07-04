package com.example.multitenancy.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.multitenancy.dto.EmployeeDTO;
import com.example.multitenancy.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDTO> searchEmployees(String searchTerm) {
        return employeeRepository.findEmployeesByName(searchTerm);
    }
}
