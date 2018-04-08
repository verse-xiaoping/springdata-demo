package com.github.springdata.config;

import com.sun.media.jfxmediaimpl.platform.PlatformManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * javaconfig 方式加载配置
 * Created by verse on 2018/4/8.
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories(basePackages = "com.github.springdata.repository")
@EnableTransactionManagement //开启注解事务
public class Config {
    @Value("${jdbc.user}")
    private  String userName;
    @Value("${jdbc.password}")
    private  String password;
    @Value("${jdbc.driverClass}")
    private  String driverClass;
    @Value("${jdbc.jdbcUrl}")
    private  String jdbcUrl;
    //1、分离属性文件
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    //2、配置数据源
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        return dataSource;
    }
    //3、配置实体管理工厂
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        //1.设置数据源
        entityManagerFactory.setDataSource(dataSource());
        //2.设置扫描实体类的包
        entityManagerFactory.setPackagesToScan("com.github.springdata.entity");
        //3.设置jpa实现商
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        return entityManagerFactory;
    }
    //4、事物管理器
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return  jpaTransactionManager;
    }
}
