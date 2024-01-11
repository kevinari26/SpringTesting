package com.kevinAri.example.service.testing.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class TestProp2 {
    // default value
//    @Value("${propTest_2.key1:default}")
    @Value("${propTest_2.prop1}")
    private String prop1;
    @Value("${propTest_2.prop2}")
    private String prop2;
    @Value("${propTest_2.nestedProp.prop1}")
    private String nestedProp1;
    @Value("${propTest_2.nestedProp.prop2}")
    private String nestedProp2;
}
