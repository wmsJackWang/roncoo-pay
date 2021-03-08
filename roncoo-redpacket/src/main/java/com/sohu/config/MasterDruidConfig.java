package com.sohu.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Druid配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 0:00
 */
@Configuration
@MapperScan(basePackages = "com.sohu", sqlSessionTemplateRef = "dbSqlSessionTemplate")
public class MasterDruidConfig {
   
    /**
     * 生成数据源.  @Primary 注解声明为默认数据源
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建 SqlSessionFactory
     */
    @Bean(name = "dbSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        bean.setTypeAliasesPackage("com.sohu.modules.redpacket.entity");
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*.xml");
        bean.setMapperLocations(resources);

        // 如果使用xml请放开下面配置
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/db1/*.xml"));
        return bean.getObject();
    }
    
    /**
     * 配置事务管理
     */
    @Bean(name = "dbTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 配置数据库操作模板
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "dbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("dbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

  
}

