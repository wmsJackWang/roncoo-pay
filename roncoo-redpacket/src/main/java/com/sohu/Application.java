package com.sohu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//这个注解使用场景： 开发者使用自己的数据源配置信息
//@ImportResource(locations={"classpath:application-quatrz.xml"})
public class Application extends SpringBootServletInitializer { // implements TransactionManagementConfigurer
//
//	@Autowired
//	private PlatformTransactionManager platformTransactionManager;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

//	@Override
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return platformTransactionManager;
//	}
//	@Bean
//	public PlatformTransactionManager transactionManager(@Qualifier("dynmicDataSource") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
}
