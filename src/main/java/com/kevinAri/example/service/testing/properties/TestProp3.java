package com.kevinAri.example.service.testing.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
// prefix harus kebab-case
@ConfigurationProperties(prefix="prop-test2")
@Setter(AccessLevel.PACKAGE)
@Getter
public class TestProp3 {
    private String prop1;
    private String prop2;
    private NestedProp nestedProp;

    @Setter(AccessLevel.PACKAGE)
    @Getter
    public static class NestedProp {
        private String prop1;
        private String prop2;
    }
}
