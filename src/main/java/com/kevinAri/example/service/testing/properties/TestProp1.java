package com.kevinAri.example.service.testing.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class TestProp1 {
    public static String prop1;
    public static String prop2;
    public static String nestedProp1;
    public static String nestedProp2;

    // default value
//    @Value("#{${propTest_1:{key: 'value'}}}")
    @Value("#{${propTest_1}}")
    private void setProperties(Map<String, String> keyValues) {
        prop1 = keyValues.get("prop1");
        prop2 = keyValues.get("prop2");
    }
    @Value("${propTest_2.nestedProp.prop1}")
    private void setNested1(String value) {
        nestedProp1 = value;
    }
    @Value("${propTest_2.nestedProp.prop2}")
    private void setNested2(String value) {
        nestedProp2 = value;
    }
}
