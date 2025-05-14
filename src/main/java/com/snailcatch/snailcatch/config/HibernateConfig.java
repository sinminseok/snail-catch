package com.snailcatch.snailcatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class HibernateConfig {

    @Bean
    public HibernateSQLInterceptor hibernateSQLInterceptor() {
        return new HibernateSQLInterceptor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            HibernateSQLInterceptor interceptor) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.getJpaPropertyMap().put("hibernate.session_factory.interceptor", interceptor);
        return factoryBean;
    }
}

