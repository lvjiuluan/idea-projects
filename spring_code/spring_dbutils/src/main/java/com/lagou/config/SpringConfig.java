package com.lagou.config;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
@ComponentScan("com.lagou")
@Import(DataSourceConfig.class)
public class SpringConfig {

    @Bean("queryRunner")
    public QueryRunner getQueryRunner(@Autowired @Qualifier("dataSource") DataSource dataSource) {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner;
    }

}
