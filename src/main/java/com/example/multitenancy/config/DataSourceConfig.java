package com.example.multitenancy.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.multitenancy.dto.TenantInfo;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    DataSource dataSource() {
        TenantAwareDataSource tenantAwareDataSource = new TenantAwareDataSource();

        // Default DataSource (used when no tenant is set)
        DataSource defaultDataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://192.168.1.106:3306/expendesk_global")
            .username("kirti")
            .password("kirti")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build();

        // Define target data sources (map for tenants)
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("global", defaultDataSource);

        tenantAwareDataSource.setDefaultTargetDataSource(defaultDataSource);
        tenantAwareDataSource.setTargetDataSources(targetDataSources);
        tenantAwareDataSource.afterPropertiesSet();

        return tenantAwareDataSource;
    }
    
    public static DataSource createDataSource(TenantInfo tenantInfo) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String Url = "jdbc:mysql://" + tenantInfo.getDbUrl() + "/" + tenantInfo.getSchema();
        dataSource.setUrl(Url);
        dataSource.setUsername(tenantInfo.getUserId());
        dataSource.setPassword(tenantInfo.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }
}
