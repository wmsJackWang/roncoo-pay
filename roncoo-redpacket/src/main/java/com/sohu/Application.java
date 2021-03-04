package com.sohu;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
//@ImportResource(locations={"classpath:application-quatrz.xml"})
public class Application extends SpringBootServletInitializer implements TransactionManagementConfigurer {

	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return platformTransactionManager;
	}
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dynmicDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
