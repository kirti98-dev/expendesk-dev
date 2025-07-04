package com.example.multitenancy.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.multitenancy.dto.TenantInfo;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class DynamicDataSourceConfig {

	public EntityManagerFactory createEntityManagerFactory(TenantInfo tenantInfo) {
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "none"); // or update, validate
		properties.put("hibernate.show_sql", true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(DataSourceConfig.createDataSource(tenantInfo));
		factory.setPackagesToScan("com.example.multitenancy.model"); // your entity package
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaPropertyMap(properties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}
}
