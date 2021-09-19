package com.spring.logging.starter.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class LoggingAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter(
            @Value("${starter.logging.message-prefix:Starter > }") String prefix,
            @Value("${starter.logging.payload.max-length:100}") int maxLength) {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setBeforeMessagePrefix(prefix);
        filter.setAfterMessagePrefix(prefix);
        filter.setBeforeMessageSuffix("");
        filter.setAfterMessageSuffix("");
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(maxLength);

        LOGGER.info("Using the following configuration: 'message prefix' - {}, 'max payload length' - {}",
                prefix, maxLength);
        return filter;
    }
}
