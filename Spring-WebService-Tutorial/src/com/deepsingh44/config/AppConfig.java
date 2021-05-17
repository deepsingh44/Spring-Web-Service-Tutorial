package com.deepsingh44.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.deepsingh44.model.Book;
import com.deepsingh44.model.User;

@Configuration
@ComponentScan(basePackages = { "com.deepsingh44" })
@EnableWebMvc
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {
	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.class"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.user"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.pass"));
		System.out.println("Deep database " + dataSource);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		// sessionFactory.setPackagesToScan(new String[] {"com.example.model"});
		sessionFactory.setAnnotatedClasses(User.class, Book.class);
		sessionFactory.setHibernateProperties(hibernateProperties());
		System.out.println("deep session " + sessionFactory);
		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		// properties.put("hibernate.dialect",
		// environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		System.out.println(properties);
		return properties;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		System.out.println("transaction " + "hello");
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		System.out.println("transaction " + transactionManager);
		return transactionManager;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver cm = new CommonsMultipartResolver();
		cm.setMaxUploadSize(1000000);
		return cm;
	}

}
