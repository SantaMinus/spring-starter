package com.spring.logging.starter.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@EnableConfigurationProperties({LoggingProperties.class})
@ConditionalOnProperty(value = "starter.logging.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public CommonsRequestLoggingFilter requestLoggingFilter(LoggingProperties properties) {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setBeforeMessagePrefix(properties.getMessagePrefix());
        filter.setAfterMessagePrefix(properties.getMessagePrefix());
        filter.setBeforeMessageSuffix("");
        filter.setAfterMessageSuffix("");
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(properties.getPayload().getMaxLength());

        LOGGER.info("Using the following configuration: 'message prefix' - {}, 'max payload length' - {}",
                properties.getMessagePrefix(), properties.getPayload().getMaxLength());
        return filter;
    }
}
