package com.kevinAri.example.service.testing.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TestProp4 {
    @Bean
    @ConfigurationProperties(prefix="prop-test3")
    public Properties getPropTest3() {
        return new Properties();
    }
    @Bean
    @ConfigurationProperties(prefix="prop-test4")
    public Properties getPropTest4() {
        return new Properties();
    }

    @Setter(AccessLevel.PACKAGE)
    @Getter
    public static class Properties {
        private String prop1;
        private String prop2;
    }
}
