package com.sohu.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Druid配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 0:00
 */
@Service
@Order(Integer.MAX_VALUE)
public class MasterDruidConfig {
    @Value("${spring.master.datasource.url:#{null}}")
    private String dbUrl;
    @Value("${spring.master.datasource.username:#{null}}")
    private String username;
    @Value("${spring.master.datasource.password:#{null}}")
    private String password;
    @Value("${spring.master.datasource.driver-class-name:#{null}}")
    private String driverClassName;
    @Value("${spring.master.datasource.initial-size:#{null}}")
    private Integer initialSize;
    @Value("${spring.master.datasource.min-idle:#{null}}")
    private Integer minIdle;
    @Value("${spring.master.datasource.max-idle:#{null}}")
    private Integer maxIdle;
    @Value("${spring.master.datasource.max-active:#{null}}")
    private Integer maxActive;
    @Value("${spring.master.datasource.max-wait-millis:#{null}}")
    private Integer maxWaitMillis;
    @Value("${spring.master.datasource.validation-query:#{null}}")
    private String validationQuery;
    @Value("${spring.master.datasource.connection-properties:#{null}}")
    private String connectionProperties;
    
    @Bean(name="dataSource")
    @Primary
    public DataSource dataSource(){
    	
    	BasicDataSource datasource = new  BasicDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        //configuration
        if(initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if(minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if(minIdle != null) {
            datasource.setMaxIdle(maxIdle);
        }
        if(maxActive != null) {
            datasource.setMaxWaitMillis(maxWaitMillis);;
        }
        if(maxIdle != null) {
            datasource.setMaxIdle(maxIdle);
        }
        if(validationQuery!=null) {
            datasource.setValidationQuery(validationQuery);
        }

        return datasource;
    }

    
  
}

