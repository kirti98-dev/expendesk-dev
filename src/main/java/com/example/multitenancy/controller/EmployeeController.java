package com.example.multitenancy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.multitenancy.config.DynamicDataSourceConfig;
import com.example.multitenancy.config.TenantContext;
import com.example.multitenancy.dto.EmployeeDTO;
import com.example.multitenancy.dto.TenantInfo;
import com.example.multitenancy.model.City;
import com.example.multitenancy.model.Employee;
import com.example.multitenancy.service.EmployeeService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployees(@RequestParam String query) {
        return employeeService.searchEmployees(query);
    }
    
    @GetMapping("/city")
    public List<City> sayHello() {
    	TenantInfo tenantInfo = TenantContext.getTenantInfo();
    	if (tenantInfo == null) {
    		//return "No tenant info found in context.";
        }
		EntityManagerFactory emf = dynamicDataSourceConfig.createEntityManagerFactory(tenantInfo);
		EntityManager em = emf.createEntityManager();
		
		String jpql = "SELECT e FROM City e";
		return em.createQuery(jpql, City.class).getResultList();
		//List<String> users = em.createQuery("FROM Employee").getResultList();
    	/*if (tenantInfo == null) {
            return "No tenant info found in context.";
        }
    	String dbUrl = tenantInfo.getDbUrl();
    	String schema = tenantInfo.getSchema();
    	return "Tenant ID: " + tenantInfo.getClientId() +
                 "\nDB Url: " + dbUrl +
                 "\nDB Schema: " + schema +
                 "\nUsername: " + tenantInfo.getUserId() +
                 "\nPassword: " + tenantInfo.getPassword(); 
    	*/ 
    }
}
