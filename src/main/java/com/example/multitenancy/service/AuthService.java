package com.example.multitenancy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.multitenancy.config.TenantContext;
import com.example.multitenancy.dto.TenantInfo;
import com.example.multitenancy.model.Client;
import com.example.multitenancy.model.Employee;
import com.example.multitenancy.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class AuthService {
    @Autowired
    private ClientRepository clientRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    private final Map<String, EntityManagerFactory> tenantFactories = new HashMap<>();

    public Employee authenticateUser(String clientCode, String email, String password) {
    	Optional<Client> client = clientRepository.findByClientCode(clientCode);
        if (!client.isPresent())
        	return null;

		String dbUrl = client.get().getDbUrl();
		String schemaName = client.get().getSchemaName();
		String dbUsername = client.get().getDbUsername();
		String dbPassword = client.get().getDbPassword();

		// Store Tenant Info in ThreadLocal
		TenantInfo tenantInfo = new TenantInfo(clientCode, dbUrl, schemaName, dbUsername, dbPassword);
        TenantContext.setTenantInfo(tenantInfo);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tenantPU", getProperties(schemaName, dbUsername, dbPassword));
        EntityManager tenantEntityManager = emf.createEntityManager();

        try {
            TypedQuery<Employee> query = tenantEntityManager.createQuery(
                "SELECT e FROM Employee e WHERE e.emailId = :email AND e.password = :password", Employee.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            List<Employee> employees = query.getResultList();
            return employees.isEmpty() ? null : employees.get(0);
        } finally {
            tenantEntityManager.close();
            emf.close();
        }
    }

    private Map<String, String> getProperties(String schema, String user, String password) {
        Map<String, String> properties = new HashMap<>();
        
        properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/" + schema);
        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("jakarta.persistence.jdbc.password", password);
        properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("jakarta.persistence.provider", "org.hibernate.jpa.HibernatePersistenceProvider");
        
        return properties;
    }
    
    public void logoutUser() {
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if (tenantInfo != null) {
            String schema = tenantInfo.getSchema();
            EntityManagerFactory emf = tenantFactories.remove(schema); // Remove from cache

            if (emf != null && emf.isOpen()) {
                emf.close(); // Close factory
            }
        }
        TenantContext.clear(); // Always clear tenant context
    }
    
    // Shutdown method to clean up EntityManagerFactory instances
    public void closeEntityManagerFactories() {
        for (EntityManagerFactory emf : tenantFactories.values()) {
            if (emf.isOpen()) {
                emf.close();
            }
        }
        tenantFactories.clear();
    }
}
