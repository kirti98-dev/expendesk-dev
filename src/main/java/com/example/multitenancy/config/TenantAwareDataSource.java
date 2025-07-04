package com.example.multitenancy.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.example.multitenancy.dto.TenantInfo;

public class TenantAwareDataSource extends AbstractRoutingDataSource {
	
	private final Map<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();
	
    @Override
    protected Object determineCurrentLookupKey() {
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        return tenantInfo != null ? tenantInfo.getSchema() : null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if (tenantInfo == null)
        	return tenantDataSources.computeIfAbsent("global", key -> getGlobalDataSource());
        
        return tenantDataSources.computeIfAbsent(tenantInfo.getSchema(), key -> createTenantDataSource(tenantInfo));
    }
    
    private DataSource createTenantDataSource(TenantInfo tenantInfo) {
        try {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://192.168.1.106:3306/" + tenantInfo.getSchema())
                    .username(tenantInfo.getUserId())
                    .password(tenantInfo.getPassword())
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create DataSource for tenant: " + tenantInfo.getSchema(), e);
        }
    }

    private DataSource getGlobalDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://192.168.1.106:3306/expendesk_global")
                .username("kirti")
                .password("kirti")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
    
    public void closeTenantDataSources() {
        for (Map.Entry<String, DataSource> entry : tenantDataSources.entrySet()) {
            try {
                ((AutoCloseable) entry.getValue()).close();
            } catch (Exception ignored) {}
        }
        tenantDataSources.clear();
    }
}