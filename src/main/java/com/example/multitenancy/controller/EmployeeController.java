package com.example.multitenancy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.multitenancy.config.DynamicDataSourceConfig;
//import com.example.multitenancy.config.TenantContext;
import com.example.multitenancy.dto.EmployeeDTO;
//import com.example.multitenancy.dto.TenantInfo;
import com.example.multitenancy.model.City;
import com.example.multitenancy.repository.CityRepository;
import com.example.multitenancy.service.EmployeeService;

//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private CityRepository cityRepository;
    //private DynamicDataSourceConfig dynamicDataSourceConfig;

    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployees(@RequestParam String query) {
        return employeeService.searchEmployees(query);
    }
    
    @GetMapping("/city")
    public List<City> getCities() {
        /*TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if(tenantInfo == null) {
        	return null;
        }
        EntityManagerFactory emf = dynamicDataSourceConfig.createEntityManagerFactory(tenantInfo);
        EntityManager em = emf.createEntityManager();

        JpaRepositoryFactory factory = new JpaRepositoryFactory(em);
        CityRepository cityRepository = factory.getRepository(CityRepository.class);
		*/
        return cityRepository.findAll();
    }
}
