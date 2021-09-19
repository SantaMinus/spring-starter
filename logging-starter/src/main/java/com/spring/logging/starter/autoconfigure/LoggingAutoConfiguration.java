package com.spring.logging.starter.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class LoggingAutoConfiguration {
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        return new CommonsRequestLoggingFilter();
    }
}
