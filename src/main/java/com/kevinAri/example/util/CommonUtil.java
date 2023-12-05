package com.kevinAri.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.UUID;

public class CommonUtil {
    public static String generateUuid() {
        StringBuilder uuid = new StringBuilder();
        uuid.append(UUID.randomUUID());
        uuid.replace(23, 24, "");
        uuid.replace(18, 19, "");
        uuid.replace(13, 14, "");
        uuid.replace(8, 9, "");
        uuid.insert(0, System.currentTimeMillis());
        return uuid.toString();
    }

    public static void errorMaker() {
        try {
//        String bigDecStr = null;
//        BigDecimal bigDecimal = new BigDecimal(bigDecStr);
//        Integer temp = 1/0;
            CommonUtil2.errorMaker2();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue("12345:'asd'", Object.class);
        } catch (Exception e) {
            Integer temp = 1/0;
        }
    }
    private static void errorMaker2() {
        Integer temp = 1/0;
    }
}
