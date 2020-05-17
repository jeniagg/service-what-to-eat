package what.to.eat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import what.to.eat.services.CategoryService;
import what.to.eat.services.CookingMethodService;
import what.to.eat.services.UsersService;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories({"what.to.eat.repositories"})
public class TestPersistanceConfig {

    @Bean
    protected DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl("jdbc:hsqldb:WhatToEatDB");
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.show_sql", "true");

        em.setJpaProperties(properties);
        em.setDataSource(dataSource());
        em.setPackagesToScan("what.to.eat.entities");

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService();
    }

    @Bean
    public CookingMethodService cookingMethodService() {
        return new CookingMethodService();
    }

    @Bean
    public UsersService usersService() {
        return new UsersService();
    }
}
