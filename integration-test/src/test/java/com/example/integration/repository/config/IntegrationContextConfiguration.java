package com.example.integration.repository.config;

import com.example.integration.repository.api.TapDispatcherSyncDateRepositoryJpa;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackageClasses = {TapDispatcherSyncDateRepositoryJpa.class})
@ComponentScan(value= {"com.example.integration.repository"})
@Import(PropertyPlaceholderAutoConfiguration.class)
public class IntegrationContextConfiguration {

    @Bean
    @Primary
    public DataSource dataSource(Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setPackagesToScan("com.example.integration.repository.entity");
        entityManager.setJpaPropertyMap(jpaPropertiesMap());
        return entityManager;
    }

    @Bean
    public Map<String, String> jpaPropertiesMap() {
        Map<String, String> map = new HashMap<>();
        map.put("eclipselink.weaving", "false");
        map.put("eclipselink.multitenant.tenants-share-cache", "true");
        map.put("eclipselink.logging.level.sql", "FINEST");
        return map;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
