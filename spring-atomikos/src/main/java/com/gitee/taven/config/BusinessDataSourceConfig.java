package com.gitee.taven.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gitee.taven.config.prop.BusinessProperties;
import com.gitee.taven.utils.PojoUtil;

@Configuration
@MapperScan(basePackages = BusinessDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "businessSqlSessionFactory")
public class BusinessDataSourceConfig {

	static final String PACKAGE = "com.gitee.taven.mapper.business";

	@Autowired
	private BusinessProperties businessProperties;

	@Bean(name = "businessDataSource")
    public DataSource businessDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaProperties(PojoUtil.obj2Properties(businessProperties));
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("businessProperties");
        ds.setPoolSize(5);
		ds.setTestQuery("SELECT 1");
        return ds;
    }

	@Bean
	public SqlSessionFactory businessSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(businessDataSource());
		return sqlSessionFactoryBean.getObject();
	}

}
