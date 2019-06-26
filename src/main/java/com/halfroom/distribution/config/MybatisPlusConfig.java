package com.halfroom.distribution.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.halfroom.distribution.config.properties.DruidProperties;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.halfroom.distribution.dao","com.halfroom.distribution.persistence.dao"})
public class MybatisPlusConfig {

    @Autowired
    DruidProperties druidProperties;
    
    
    @Bean
    public DruidDataSource dataSourceDis(){
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 数据源连接池配置
 
    @Bean
    public DruidDataSource singleDatasource() {
        return dataSourceDis();
    }
    */
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

