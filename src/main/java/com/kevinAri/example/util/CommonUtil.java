package com.kevinAri.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import oracle.sql.TIMESTAMP;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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
    public static String generateRandomTraceNumber() {
        Random random = new Random();
        // 000000 sampai 999999
        return String.format("%06d", random.nextInt(1000000));
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


    // format
    public static String oracleTimestampToStringPreserveNull(TIMESTAMP timestampInput, String dateFormat) throws Exception {
        try {
            if (timestampInput==null) return null;
            return new SimpleDateFormat(dateFormat).format(timestampInput.dateValue());
        } catch (Exception e) {
            return null;
        }
    }
    public static String formatDateToStringPreserveNull(Date dateInput, String dateFormat) {
        try {
            if (dateInput==null) return null;
            return new SimpleDateFormat(dateFormat).format(dateInput);
        } catch (Exception e) {
            return null;
        }
    }

    // number
    // casting
    public static Long bigDecimalObjectToLong(Object input) {
        try {
            return ((BigDecimal) input).longValue();
        } catch (Exception e) {
            return null;
        }
    }
    // parsing
    public static Long stringToLong(Object input) {
        try {
            if (input==null) return null;
            else return new Long(input.toString());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal stringToBigDecimal (String input) {
        try {
            if (input==null || "".equals(input)) return null;
            else return new BigDecimal(input);
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal stringToBigDecimal (String input, BigDecimal defaultValue) {
        try {
            if (input==null || "".equals(input)) return defaultValue;
            else return new BigDecimal(input);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    // jsonNode
    public static boolean isJsonNodeNull(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode instanceof NullNode) return true;
        else return false;
    }
    public static String jsonNodeToStringAndTrim(JsonNode input) {
        try {
            if (input==null || input instanceof NullNode) return "";
            else return input.asText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    public static Date parseJsonNodeStringToDate(JsonNode jsonNode, String parseFormat) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return null;
            return new SimpleDateFormat(parseFormat).parse(jsonNode.asText());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal jsonNodeStringToBigDecimal(JsonNode jsonNode, BigDecimal defaultValue) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return defaultValue;
            return new BigDecimal(jsonNode.asText());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
