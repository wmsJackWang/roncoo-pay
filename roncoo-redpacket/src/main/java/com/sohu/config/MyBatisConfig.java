package com.sohu.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sohu.datasource.DynmicDataSource;

//@EnableTransactionManagement
//@Configuration
public class MyBatisConfig {
//	@Autowired
//	@Qualifier(value="dataSource")
//	private DataSource masterBean;
//	@Autowired
//	@Qualifier(value="dataSource")
//	private DataSource slaveBean;
//
//
//	@Bean
//	public DataSource dynmicDataSource() throws SQLException {
//		DynmicDataSource dynmicDataSource = new DynmicDataSource();
//		Map<Object, Object> targetDataSources = new HashMap<>();
//		targetDataSources.put("master", masterBean);
//		targetDataSources.put("slave", slaveBean);
//		dynmicDataSource.setTargetDataSources(targetDataSources);
//
//		dynmicDataSource.setDefaultTargetDataSource(masterBean);
//		return dynmicDataSource;
//	}
//
//	@Bean
//	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynmicDataSource") DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//		sqlSessionFactoryBean
//				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
//		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis.xml");
//		sqlSessionFactoryBean.setConfigLocation(resources[0]);
//		// 添加Mybatis插件，例如分页，在之类创建你插件添加进去即可，这里我就不做叙述了。
//		// sqlSessionFactoryBean.setPlugins(new Interceptor[]{你的插件});
//
//		return sqlSessionFactoryBean.getObject();
//	}


//    /**
//     * 	装配事务管理器
//     * @return
//     */
//    @Bean
//    public DataSourceTransactionManager transactionManager(@Qualifier("dynmicDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//	
//	
//	@Bean
//	public JdbcTemplate jdbcTemplate(@Qualifier("dynmicDataSource") DataSource dataSource) {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		return jdbcTemplate;
//	}
//	
}
