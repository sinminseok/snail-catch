package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.util.SlowQueryInspector;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlowQueryHibernateConfig {

    @Bean
    public StatementInspector slowQueryInspector() {
        return new SlowQueryInspector();
    }
}

