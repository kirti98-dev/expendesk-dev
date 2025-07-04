package com.example.multitenancy.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.multitenancy.dto.TenantInfo;
import com.example.multitenancy.model.Client;
import com.example.multitenancy.repository.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    @Autowired
    private ClientRepository clientRepository;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientId = request.getHeader("X-Tenant-Id"); // Expecting client ID in header
        if (clientId != null) {

        	// Fetch tenant details from the database
        	Optional<Client> client = clientRepository.findByClientCode(clientId);
        	if (!client.isPresent()) {
        		throw new RuntimeException("Invalid tenant ID");
        	}

        	// Store tenant info in ThreadLocal
        	TenantInfo tenantInfo = new TenantInfo(
        			client.get().getClientCode(),
        			client.get().getDbUrl(),
        			client.get().getSchemaName(),
        			client.get().getDbUsername(),
        			client.get().getDbPassword()
        			);
        	TenantContext.setTenantInfo(tenantInfo);
        }
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}