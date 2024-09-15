package com.lp.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.lp.repository" })
public class DatabaseConfiguration {

	@Autowired
	private Environment env;

	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntitiyMangerfactory() {
		LocalContainerEntityManagerFactoryBean localFactoryBean = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		localFactoryBean.setDataSource(getDataSource());
		localFactoryBean.setJpaVendorAdapter(vendorAdapter);
		localFactoryBean.setJpaProperties(getAdditionalProperties());
		localFactoryBean.setPackagesToScan("com.lp.model");
		return localFactoryBean;
	}

	@Bean
	public HikariDataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(DatabaseDriver.POSTGRESQL.getDriverClassName());
		dataSource.setUsername(env.getProperty("spring.database.username"));
		dataSource.setPassword(env.getProperty("spring.database.password"));
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		return dataSource;
	}

	@Bean("transactionManager")
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf) {
		  JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		  jpaTransactionManager.setEntityManagerFactory(emf);
		return jpaTransactionManager;
	}

	private Properties getAdditionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", Boolean.FALSE.toString());
		return properties;
	}

}
