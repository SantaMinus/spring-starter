package com.spring.logging.starter.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingAutoConfigurationIT {
    @Test
    void shouldAutoConfigurationBeAppliedToWebApp() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .run(context -> assertThat(context).hasNotFailed()
                        .hasSingleBean(CommonsRequestLoggingFilter.class)
                        .hasSingleBean(LoggingProperties.class)
                        .getBean(LoggingProperties.class).hasNoNullFieldsOrProperties());
    }

    @Test
    void shouldAutoConfigurationBeFailedDueToValidationConstraints() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withPropertyValues("starter.logging.message-prefix=>")
                .run(context -> assertThat(context).hasFailed().getFailure()
                        .hasRootCauseInstanceOf(BindValidationException.class)
                        .hasStackTraceContaining("'starter.logging' on field 'messagePrefix'"));
    }

    @Test
    void shouldLoggingFilterBeNotAppliedToContext() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> assertThat(context).hasNotFailed()
                        .hasSingleBean(CommonsRequestLoggingFilter.class)
                        .hasSingleBean(TestLoggingFilter.class));
    }

    private static class TestConfiguration {
        @Bean
        public TestLoggingFilter testLoggingFilter() {
            return new TestLoggingFilter();
        }
    }

    private static class TestLoggingFilter extends CommonsRequestLoggingFilter {
        @Override
        protected boolean shouldLog(HttpServletRequest request) {
            return true;
        }
    }
}

