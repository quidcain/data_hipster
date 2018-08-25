package com.datahipster.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QuartzConfiguration {
    DataSource dataSource;

    @Autowired
    public QuartzConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer()
    {
        return bean -> bean.setDataSource(dataSource);
    }
}
