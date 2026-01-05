package com.hyungsuu.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.hyungsuu.common.util.YamlPropertySourceFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Configuration

//@PropertySource(value = "classpath:application.yml",  factory = YamlPropertySourceFactory.class)
@PropertySource("classpath:application.properties")
public class DatabaseConfig {
	@Autowired
	private ApplicationContext applicationContext;

	  public DatabaseConfig(ApplicationContext ac) {
	        this.applicationContext = ac;
	    }


	
//	@Bean(name="sqlSessionFactory")
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean
				.setConfigLocation(applicationContext.getResource("classpath:/ecid/mapper/config/mapper-config.xml"));

		sqlSessionFactoryBean
				.setMapperLocations(applicationContext.getResources("classpath:/ecid/mapper/gateway/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

//	@Bean(name="sqlSessionTemplate")
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}