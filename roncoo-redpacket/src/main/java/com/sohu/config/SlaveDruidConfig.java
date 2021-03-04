package com.sohu.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
public class SlaveDruidConfig {

    @Value("${spring.slave.datasource.url:#{null}}")
    private String dbUrl;
    @Value("${spring.slave.datasource.username:#{null}}")
    private String username;
    @Value("${spring.slave.datasource.password:#{null}}")
    private String password;
    @Value("${spring.slave.datasource.driverClassName:#{null}}")
    private String driverClassName;
    @Value("${spring.slave.datasource.initialSize:#{null}}")
    private Integer initialSize;
    @Value("${spring.slave.datasource.minIdle:#{null}}")
    private Integer minIdle;
    @Value("${spring.slave.datasource.maxActive:#{null}}")
    private Integer maxActive;
    @Value("${spring.slave.datasource.maxWait:#{null}}")
    private Integer maxWait;
    @Value("${spring.slave.datasource.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.slave.datasource.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.slave.datasource.validationQuery:#{null}}")
    private String validationQuery;
    @Value("${spring.slave.datasource.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;
    @Value("${spring.slave.datasource.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;
    @Value("${spring.slave.datasource.testOnReturn:#{null}}")
    private Boolean testOnReturn;
    @Value("${spring.slave.datasource.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;
    @Value("${spring.slave.datasource.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.slave.datasource.filters:#{null}}")
    private String filters;
    @Value("{spring.slave.datasource.connectionProperties:#{null}}")
    private String connectionProperties;

    @Bean(name="slaveBean")
    public DataSource dataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//
//        datasource.setUrl(this.dbUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//        //configuration
//        if(initialSize != null) {
//            datasource.setInitialSize(initialSize);
//        }
//        if(minIdle != null) {
//            datasource.setMinIdle(minIdle);
//        }
//        if(maxActive != null) {
//            datasource.setMaxActive(maxActive);
//        }
//        if(maxWait != null) {
//            datasource.setMaxWait(maxWait);
//        }
//        if(timeBetweenEvictionRunsMillis != null) {
//            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        }
//        if(minEvictableIdleTimeMillis != null) {
//            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        }
//        if(validationQuery!=null) {
//            datasource.setValidationQuery(validationQuery);
//        }
//        if(testWhileIdle != null) {
//            datasource.setTestWhileIdle(testWhileIdle);
//        }
//        if(testOnBorrow != null) {
//            datasource.setTestOnBorrow(testOnBorrow);
//        }
//        if(testOnReturn != null) {
//            datasource.setTestOnReturn(testOnReturn);
//        }
//        if(poolPreparedStatements != null) {
//            datasource.setPoolPreparedStatements(poolPreparedStatements);
//        }
//        if(maxPoolPreparedStatementPerConnectionSize != null) {
//            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        }
//
//        if(connectionProperties != null) {
//            datasource.setConnectionProperties(connectionProperties);
//        }
//
//        List<Filter> filters = new ArrayList<>();
//        filters.add(statFilter());
//        filters.add(wallFilter());
//        datasource.setProxyFilters(filters);

        return null;
    }

//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        servletRegistrationBean.setServlet(new StatViewServlet());
//        servletRegistrationBean.addUrlMappings("/druid/*");
//        return servletRegistrationBean;
//    }
//
//    @Bean
//    public StatFilter statFilter(){
//        StatFilter statFilter = new StatFilter();
//        statFilter.setLogSlowSql(true);
//        statFilter.setMergeSql(true);
//        statFilter.setSlowSqlMillis(1000);
//
//        return statFilter;
//    }
//
//    @Bean
//    public WallFilter wallFilter(){
//        WallFilter wallFilter = new WallFilter();
//
//        //允许执行多条SQL
//        WallConfig config = new WallConfig();
//        config.setMultiStatementAllow(true);
//        wallFilter.setConfig(config);
//
//        return wallFilter;
//    }

}
