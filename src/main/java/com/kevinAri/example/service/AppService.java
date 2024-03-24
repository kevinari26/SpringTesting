package com.kevinAri.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;
import com.kevinAri.example.model.ActionEnum;
import com.kevinAri.example.service.testing.jasper.JasperService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oracle.sql.TIMESTAMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm.icu.text.NumberFormat;
import sun.applet.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class AppService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JasperService jasperService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Setter
    @Getter
    static class Class1 {
        private String date_1;
        private String angka_1;
        private String angka_2;
        private ActionEnum actionEnum;
    }
    @Setter
    @Getter
    @ToString
    static class Class2 {
        private Date date_1;
        @JsonProperty("ANGKA_1")
        private Float angka11;
        private Long aNgka_2;
        private Long angka_asd_asd;
    }


    public void execute() {
        try {
//            Date date = new Date();
//            System.out.println(date.toString());
//            System.out.println(date.getTime());
//            System.out.println(UUID.randomUUID());

//            jasperService.testJasper();
//            fileToBase64();
        } catch (Exception e) {
            try {
//                errorLog(log, e);
                e.printStackTrace();
                System.out.println("error");
            } catch (Exception ex) {

            }
        }
    }

    /* JASYPT
    java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=test password=pass algorithm=PBEWITHHMACSHA512ANDAES_256
    mvn jasypt:encrypt-value -Djasypt.encryptor.password="alpabitomfaiz" -Djasypt.plugin.value="welcome1"
    mvn jasypt:decrypt-value -Djasypt.encryptor.password="password encrypt" -Djasypt.plugin.value=GDjcyBQhAurTY2tAqunOKZmmsi8sbqRpkSxXKjpD8uWE5Sa5S0zZi86CEYORcx8NCSNF+gHNIz3Ea4gFWLZBBA==
    mvn jasypt:encrypt -Djasypt.encryptor.password="password encrypt"
    ENC(GDjcyBQhAurTY2tAqunOKZmmsi8sbqRpkSxXKjpD8uWE5Sa5S0zZi86CEYORcx8NCSNF+gHNIz3Ea4gFWLZBBA==)

    di setenv tomcat
    export JASYPT_ENCRYPTOR_PASSWORD="alpabitomfaiz"
    */
    // function
    private String randomNumber() {
        Random r = new Random();
        return String.format("%06d", r.nextInt(999999));
    }
    public static Long getBracketContent(String input) {
        try {
            return new Long(input.substring(input.indexOf("(")+1, input.indexOf(")")));
        } catch (Exception e) {
            return null;
        }
    }
    public static String formatCurrency (String currencyAmountStr) {
        try {
            Locale ind = new Locale("id", "ID");
            NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(ind);
            String output;
            if (currencyAmountStr==null || currencyAmountStr.equals("")) {
                output = dollarFormat.format(0);
            } else {
                output = dollarFormat.format(new Long(currencyAmountStr));
            }
            return output.substring(2, output.length()-3);
        } catch (Exception e) {
            return "";
        }
    }
    private BigDecimal countDiscountResult(BigDecimal input, String discountStr) {
        discountStr = "35";
        input = BigDecimal.valueOf(42362534123L);
        BigDecimal bigDecimal100 = new BigDecimal(100L);
//        double discountDouble = Double.parseDouble(discountStr);
//        float discountDouble = Float.parseFloat(discountStr);
//        discountDouble = (100 - discountDouble) / 100;
//        BigDecimal discountBigDecimal = BigDecimal.valueOf(discountDouble);
        BigDecimal discountBigDecimal = BigDecimal.valueOf(Long.parseLong(discountStr));
        discountBigDecimal = bigDecimal100.subtract(discountBigDecimal).divide(bigDecimal100);
        BigDecimal discountResult = input.multiply(discountBigDecimal);
        System.out.println(discountResult);
        return null;
//        System.out.println(input);
//        System.out.println(bigDecimal100.subtract(discount).divide(bigDecimal100));
//        return input.multiply(bigDecimal100.subtract(discount).divide(bigDecimal100, BigDecimal.ROUND_DOWN));
    }
    private String constructQuery(String schemaName, String tableName, String fieldName, String whereCondition) {
        fieldName = fieldName.replaceAll("\\|", ", ");
        String query;

        if (whereCondition == null) {
            query = String.format(
                    "SELECT %s FROM \"%s\".\"%s\"",
                    fieldName, schemaName, tableName);
        }
        else {
            whereCondition = whereCondition.replaceAll("\\|", " AND ");
            query = String.format(
                    "SELECT %s FROM \"%s\".\"%s\" WHERE %s",
                    fieldName, schemaName, tableName, whereCondition);
        }
        return query;
    }
    private void terbilang() {
        // terbilang
        ULocale locale = new ULocale("Id");
        NumberFormat formatter = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);
        Long number = 123456L;
        BigDecimal number2 = new BigDecimal("123.00");
//            String number2 = "123.123";
        System.out.println(formatter.format(number2));
        number = 190347800L;
        System.out.println(formatter.format(number));
    }
    public static Date parseStringDateToDate(String dateStr, String parseFormat) throws ParseException {
        try {
            if (dateStr==null || "".equals(dateStr.trim())) return null;
            return new SimpleDateFormat(parseFormat).parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }



    // date
    private void testDate() {
        try {
            TIMESTAMP timestamp = new TIMESTAMP(new Timestamp(System.currentTimeMillis()));
            Date date = timestamp.dateValue();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
            System.out.println(dateFormat.format(date));

            String sDate1="21/09/2023";
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            System.out.println(sDate1+"\t"+date1);
            System.out.println(objectMapper.writeValueAsString(date1));
            YearMonth yearMonth = YearMonth.of(2023, 10);
            System.out.println(yearMonth);
            System.out.println(yearMonth.atEndOfMonth());
            System.out.println(yearMonth.atDay(1));
            LocalDate localDate = LocalDate.now();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testDate2() {
        try {
            Date date;
            String date1Str = "1983/01/29";
            String date2Str = "29011983";
            System.out.println(parseAmlDateToDate(date1Str));
            System.out.println(parseAmlDateToDate(date2Str));

            Date date1 = new Date(412621200000L);
//            Date date2 = new Date(412621200001L);
//            System.out.println(date);
//            System.out.println(date.toString());
//            Date temp = null;
//            System.out.println(temp.equals(date));

//            System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(date1));

//            Date date = timestamp.dateValue();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            System.out.println(dateFormat.format(date));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<String, String> getStartEnd6Month() {
        LocalDate currentDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        LocalDate endDate = yearMonth.minusMonths(1).atEndOfMonth();
        LocalDate startDate = yearMonth.minusMonths(6).atDay(1);
        Map<String, String> response = new HashMap<>();
        response.put("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//        response.put("startDate", "20120901");
        response.put("endDate", endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        return response;
    }
    public static List<String> getPast6Month() {
        List<String> response = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth()).minusMonths(1);
        response.add(yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")));
        for (int i=0; i<5; i++) {
            yearMonth = yearMonth.minusMonths(1);
            response.add(yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")));
        }
        return response;
    }

    public static String parseAmlDateToDate (String amlDateStr) {
        try {
            String date1Str = "1983/01/29";
            String date2Str = "29011983";
//            String xmlDateFormat = "yyyy-MM-dd hh:mm:ss.SSS";
            String xmlDateFormat = "yyyy-MM-dd";
            if (amlDateStr==null || "".equals(amlDateStr.trim())) return "";
            else if (amlDateStr.contains("/")) {
                Date tempDate = new SimpleDateFormat("yyyy/MM/dd").parse(amlDateStr.trim());
                return new SimpleDateFormat(xmlDateFormat).format(tempDate);
            }
            else {
                Date tempDate = new SimpleDateFormat("ddMMyyyy").parse(amlDateStr.trim());
                return new SimpleDateFormat(xmlDateFormat).format(tempDate);
            }
        } catch (Exception e) {
            return "";
        }
    }
    public static String dateToString (Date date, String dateFormat) {
        try {
            if (date==null) return null;
            else return new SimpleDateFormat(dateFormat).format(date);
        } catch (Exception e) {
            return null;
        }
    }


    // test regex
    public static void testRegex() {
        String temp = "halo [%=APPNUMBER% halo";
        Pattern pattern = Pattern.compile("(\\[%=([\\w]+)%])"); // pattern cari semua yang di dalam [%=...%]
        pattern = Pattern.compile("\\[%=([\\w\\d]+)%\\]"); // pattern cari semua yang di dalam [%=...%]
        Matcher matcher = pattern.matcher(temp);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
//                System.out.println(matcher.group(2));
        }
        System.out.println("ok");

        System.out.println(temp);
//            temp = temp.replaceAll("(?i)\\[%="+"APPNUMBER"+"%\\]", "testAri2");
        temp = temp.replaceAll("(?i)\\[%="+"APPNUMBER"+"%\\]", "testAri2");
        System.out.println(temp);
        temp = temp.replaceAll("APPNUMBER", "testAri2");
        System.out.println(temp);
//            System.out.println(temp.get("test2").toString());
    }

    // big decimal
    private void testBigDecimal() {
        try {
            BigDecimal bigDecimal = BigDecimal.valueOf(123.456);
            bigDecimal = bigDecimal.setScale(6, RoundingMode.CEILING);
            System.out.println(bigDecimal);
            bigDecimal = BigDecimal.valueOf(123.451);
            bigDecimal = bigDecimal.setScale(6, RoundingMode.HALF_DOWN);
            System.out.println(bigDecimal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // json node, object mapper
    private void testJsonNode() {
        try {
            Class1 class1 = new Class1();
//            class1.setAngka_2("angka2");
            class1.setAngka_2("123");
            class1.setActionEnum(ActionEnum.REQUEST);
            System.out.println(objectMapper.writeValueAsString(class1));
            JsonNode jsonNode = objectMapper.convertValue(class1, JsonNode.class);
            Class2 class2 = objectMapper.convertValue(class1, Class2.class);
            System.out.println(jsonNode);

//            Map<String, Object> temp = objectMapper.convertValue(class1, Map.class);
//            ((ObjectNode) jsonNode).set("test", ((ObjectNode) jsonNode).textNode("asda"));
//            System.out.println(jsonNode);

            ObjectNode jNode = objectMapper.createObjectNode();
            jNode.set("temp", objectMapper.createObjectNode());
            jNode.set("temp2", objectMapper.createObjectNode());
            System.out.println(jNode);



//            Map<String, String> map = new HashMap<>();
//            map.put("angka_2", "2");
//            map.put("actionEnum", "REQUEST");
//            Class1 class2 = new Class1();
//            System.out.println(objectMapper.writeValueAsString(class2));
//            class2 = objectMapper.convertValue(map, Class1.class);
//            System.out.println(objectMapper.writeValueAsString(class2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testJsonNode2() {
        try {
            String jsonStr;
            {
                jsonStr =
                        "{\n" +
                                "\t\"gcn\":\"\",\n" +
                                "\t\"appNumber\":\"TESTARI1\",\n" +
                                "\t\"applicantId\":\"\",\n" +
                                "\t\"casaacctDetailBatchByGCNList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"00904498858\",\n" +
                                "\t\t\t\"accountOpenDate\": \"2013-04-22\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"USD\",\n" +
                                "\t\t\t\"avgBalanceLast1Month\": \"6243.00\",\n" +
                                "\t\t\t\"avgBalanceLast2Months\": \"6241.19\",\n" +
                                "\t\t\t\"avgBalanceLast3Months\": \"6239.00\",\n" +
                                "\t\t\t\"avgBalanceLast4Months\": \"6239.00\",\n" +
                                "\t\t\t\"currentBalance\": \"6243.09\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"87637375.875000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"6243.090000\",\n" +
                                "\t\t\t\"availableBalance\": \"6243.09\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast1MonthLCY\": \"\",\n" +
                                "\t\t\t\"agreementDate\": \"\",\n" +
                                "\t\t\t\"maturityDate\": \"\",\n" +
                                "\t\t\t\"overdraftBalance\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceLCY\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceFCY\": \"\",\n" +
                                "\t\t\t\"creditLimit\": \"\",\n" +
                                "\t\t\t\"collectabilityCode\": \"\",\n" +
                                "\t\t\t\"collectabilityDesc\": \"\",\n" +
                                "\t\t\t\"overlimitDPD\": \"\",\n" +
                                "\t\t\t\"odinterestRate\": \"\"\n" +
                                "\t\t},\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"01217773330\",\n" +
                                "\t\t\t\"accountOpenDate\": \"2011-06-20\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\"avgBalanceLast1Month\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast2Months\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast3Months\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast4Months\": \"0.00\",\n" +
                                "\t\t\t\"currentBalance\": \"0.32\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"0.320000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"\",\n" +
                                "\t\t\t\"availableBalance\": \"0.32\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast1MonthLCY\": \"\",\n" +
                                "\t\t\t\"agreementDate\": \"\",\n" +
                                "\t\t\t\"maturityDate\": \"\",\n" +
                                "\t\t\t\"overdraftBalance\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceLCY\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceFCY\": \"\",\n" +
                                "\t\t\t\"creditLimit\": \"\",\n" +
                                "\t\t\t\"collectabilityCode\": \"\",\n" +
                                "\t\t\t\"collectabilityDesc\": \"\",\n" +
                                "\t\t\t\"overlimitDPD\": \"\",\n" +
                                "\t\t\t\"odinterestRate\": \"\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"tdacctDetailBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"01217773330\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\"currentBalance\": \"0.32\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"0.320000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"\",\n" +
                                "\t\t\t\"currentCashValue\": \"0.00\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"issuedDate\": \"0.00\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"mfbondsAcctDetailBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"unitHolderIDNo\": \"C558415\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\n" +
                                "\t\t\t\"investmentValue\": \"0.32\",\n" +
                                "\t\t\t\"investmentValueLCY\": \"0.320000\",\n" +
                                "\t\t\t\"investmentValueFCY\": \"\",\n" +
                                "\t\t\t\"openDate\": \"0.00\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"holdAmtLCY\": \"0.00\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\n" +
                                "\t\n" +
                                "\t\"casatdacctBalMthlyBatchByGCNList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcnnumber\": \"\",\n" +
                                "\t\t\t\"cifnumber\": \"\",\n" +
                                "\t\t\t\"accountNo\": \"\",\n" +
                                "\t\t\t\"batchDate\": \"\",\n" +
                                "\t\t\t\"currency\": \"\",\n" +
                                "\t\t\t\"currentBalance\": \"\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"\",\n" +
                                "\t\t\t\"averageBalance\": \"\",\n" +
                                "\t\t\t\"averageBalanceLCY\": \"\",\n" +
                                "\t\t\t\"totalDebitTransAmt\": \"\",\n" +
                                "\t\t\t\"numberOfDebitTrans\": \"\",\n" +
                                "\t\t\t\"totalCreditTransAmt\": \"\",\n" +
                                "\t\t\t\"numberOfCreditTrans\": \"\",\n" +
                                "\t\t\t\"currentCashValue\": \"\",\n" +
                                "\t\t\t\"currentCashValueLCY\": \"\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"mfbondsAcctBalMthlyBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcnnumber\": \"\",\n" +
                                "\t\t\t\"segment\": \"\",\n" +
                                "\t\t\t\"unitHolderIDNo\": \"\",\n" +
                                "\t\t\t\"batchDate\": \"\",\n" +
                                "\t\t\t\"currency\": \"\",\n" +
                                "\t\t\t\"investmentValue\": \"\",\n" +
                                "\t\t\t\"investmentValueLCY\": \"\",\n" +
                                "\t\t\t\"holdAmt\": \"\",\n" +
                                "\t\t\t\"holdAmtLCY\": 4.11852E8\n" +
                                "\t\t}\n" +
                                "\t]\n" +
                                "}";
            }
            JsonNode jsonNode = objectMapper.readTree(jsonStr);
//            System.out.println(jsonNode.get("casaacctDetailBatchByGCNList").get(0).get("avgBalanceLast2Months").asDouble());
//            System.out.println(jsonNode.get("casaacctDetailBatchByGCNList").get(0).get("currentBalanceLCY").asDouble());
//            System.out.println(jsonNode.get("mfbondsAcctBalMthlyBatchByGCNInqList").get(0).get("holdAmtLCY").asDouble());

            jsonStr = "{" +
                    "\"key1\":\"null\"" +
                    "}";
            jsonNode = objectMapper.readTree(jsonStr);
            String temp = jsonNode.get("key1").asText();
            System.out.println(temp);
            temp = null;
            System.out.println(temp);
            System.out.println(jsonNode.get("key2"));
            System.out.println(jsonNode.get("key2").get("key2"));

            {
                jsonStr = "{\n" +
                        "    \"requestAddInfo\": {\n" +
                        "        \"functionId\": \"1047\",\n" +
                        "        \"workstationIp\": \"string\"\n" +
                        "    },\n" +
                        "    \"scheduleAuto\": {\n" +
                        "        \"channel\": \"751\",\n" +
                        "        \"execHour\": \"11\",\n" +
                        "        \"execMinute\": \"11\",\n" +
                        "        \"firstRunDate\": \"2023-11-29 11:11:46.4646\",\n" +
                        "        \"frequency\": 3,\n" +
                        "        \"function\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"functionName\": \"Autodebet PNET/PMOBX\",\n" +
                        "            \"functionModule\": \"autodebet\",\n" +
                        "            \"processName\": \"AUTODEBET_PROCESS\",\n" +
                        "            \"description\": \"Autodebet PNET/PMOBX \",\n" +
                        "            \"status\": \"1\",\n" +
                        "            \"lastModifiedDate\": \"2021-11-10 14:08:38\"\n" +
                        "        },\n" +
                        "        \"name\": \"123\",\n" +
                        "        \"nextExecDate\": \"2023-11-29\",\n" +
                        "        \"status\": \"1\"\n" +
                        "    }\n" +
                        "}";
            }
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testJsonNodeReadFile() {
        try {
            //            File jsonFile = new File("D:\\Project\\Java\\SpringTesting\\src\\main\\resources\\jsonFiles\\data.json");
            Path path = Paths.get("src/main/resources/jsonFiles/", "pefindo1.json");
            File jsonFile = path.toFile();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testJsonNodeSort() {
        try {
            String jsonStr;
            {
                jsonStr =
                        "{\n" +
                                "\t\"gcn\":\"\",\n" +
                                "\t\"appNumber\":\"TESTARI1\",\n" +
                                "\t\"applicantId\":\"\",\n" +
                                "\t\"casaacctDetailBatchByGCNList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"00904498858\",\n" +
                                "\t\t\t\"accountOpenDate\": \"2013-04-22\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"USD\",\n" +
                                "\t\t\t\"avgBalanceLast1Month\": \"6243.00\",\n" +
                                "\t\t\t\"avgBalanceLast2Months\": \"6241.19\",\n" +
                                "\t\t\t\"avgBalanceLast3Months\": \"6239.00\",\n" +
                                "\t\t\t\"avgBalanceLast4Months\": \"6239.00\",\n" +
                                "\t\t\t\"currentBalance\": \"6243.09\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"87637375.875000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"6243.090000\",\n" +
                                "\t\t\t\"availableBalance\": \"6243.09\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast1MonthLCY\": \"\",\n" +
                                "\t\t\t\"agreementDate\": \"\",\n" +
                                "\t\t\t\"maturityDate\": \"\",\n" +
                                "\t\t\t\"overdraftBalance\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceLCY\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceFCY\": \"\",\n" +
                                "\t\t\t\"creditLimit\": \"\",\n" +
                                "\t\t\t\"collectabilityCode\": \"\",\n" +
                                "\t\t\t\"collectabilityDesc\": \"\",\n" +
                                "\t\t\t\"overlimitDPD\": \"\",\n" +
                                "\t\t\t\"odinterestRate\": \"\"\n" +
                                "\t\t},\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"01217773330\",\n" +
                                "\t\t\t\"accountOpenDate\": \"2011-06-20\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\"avgBalanceLast1Month\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast2Months\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast3Months\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast4Months\": \"0.00\",\n" +
                                "\t\t\t\"currentBalance\": \"0.32\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"0.320000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"\",\n" +
                                "\t\t\t\"availableBalance\": \"0.32\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"avgBalanceLast1MonthLCY\": \"\",\n" +
                                "\t\t\t\"agreementDate\": \"\",\n" +
                                "\t\t\t\"maturityDate\": \"\",\n" +
                                "\t\t\t\"overdraftBalance\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceLCY\": \"\",\n" +
                                "\t\t\t\"overdraftBalanceFCY\": \"\",\n" +
                                "\t\t\t\"creditLimit\": \"\",\n" +
                                "\t\t\t\"collectabilityCode\": \"\",\n" +
                                "\t\t\t\"collectabilityDesc\": \"\",\n" +
                                "\t\t\t\"overlimitDPD\": \"\",\n" +
                                "\t\t\t\"odinterestRate\": \"\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"tdacctDetailBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"cif\": \"C558415\",\n" +
                                "\t\t\t\"accountNo\": \"01217773330\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\"currentBalance\": \"0.32\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"0.320000\",\n" +
                                "\t\t\t\"currentBalanceFCY\": \"\",\n" +
                                "\t\t\t\"currentCashValue\": \"0.00\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"issuedDate\": \"0.00\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"mfbondsAcctDetailBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcn\": \"CEI0730000000001\",\n" +
                                "\t\t\t\"unitHolderIDNo\": \"C558415\",\n" +
                                "\t\t\t\"productTypeCode\": \"CA\",\n" +
                                "\t\t\t\"productType\": \"Current Account\",\n" +
                                "\t\t\t\"accountStatusGroup\": \"Active\",\n" +
                                "\t\t\t\"currency\": \"IDR\",\n" +
                                "\t\t\t\n" +
                                "\t\t\t\"investmentValue\": \"0.32\",\n" +
                                "\t\t\t\"investmentValueLCY\": \"0.320000\",\n" +
                                "\t\t\t\"investmentValueFCY\": \"\",\n" +
                                "\t\t\t\"openDate\": \"0.00\",\n" +
                                "\t\t\t\"holdAmt\": \"0.00\",\n" +
                                "\t\t\t\"holdAmtLCY\": \"0.00\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\n" +
                                "\t\n" +
                                "\t\"casatdacctBalMthlyBatchByGCNList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcnnumber\": \"\",\n" +
                                "\t\t\t\"cifnumber\": \"\",\n" +
                                "\t\t\t\"accountNo\": \"\",\n" +
                                "\t\t\t\"batchDate\": \"\",\n" +
                                "\t\t\t\"currency\": \"\",\n" +
                                "\t\t\t\"currentBalance\": \"\",\n" +
                                "\t\t\t\"currentBalanceLCY\": \"\",\n" +
                                "\t\t\t\"averageBalance\": \"\",\n" +
                                "\t\t\t\"averageBalanceLCY\": \"\",\n" +
                                "\t\t\t\"totalDebitTransAmt\": \"\",\n" +
                                "\t\t\t\"numberOfDebitTrans\": \"\",\n" +
                                "\t\t\t\"totalCreditTransAmt\": \"\",\n" +
                                "\t\t\t\"numberOfCreditTrans\": \"\",\n" +
                                "\t\t\t\"currentCashValue\": \"\",\n" +
                                "\t\t\t\"currentCashValueLCY\": \"\"\n" +
                                "\t\t}\n" +
                                "\t],\n" +
                                "\t\"mfbondsAcctBalMthlyBatchByGCNInqList\": [\n" +
                                "\t\t{\n" +
                                "\t\t\t\"gcnnumber\": \"\",\n" +
                                "\t\t\t\"segment\": \"\",\n" +
                                "\t\t\t\"unitHolderIDNo\": \"\",\n" +
                                "\t\t\t\"batchDate\": \"\",\n" +
                                "\t\t\t\"currency\": \"\",\n" +
                                "\t\t\t\"investmentValue\": \"\",\n" +
                                "\t\t\t\"investmentValueLCY\": \"\",\n" +
                                "\t\t\t\"holdAmt\": \"\",\n" +
                                "\t\t\t\"holdAmtLCY\": 4.11852E8\n" +
                                "\t\t}\n" +
                                "\t]\n" +
                                "}";
            }
            JsonNode jsonNode = objectMapper.readTree(jsonStr);
//            System.out.println(jsonNode.get("casaacctDetailBatchByGCNList").get(0).get("avgBalanceLast2Months").asDouble());
//            System.out.println(jsonNode.get("casaacctDetailBatchByGCNList").get(0).get("currentBalanceLCY").asDouble());
//            System.out.println(jsonNode.get("mfbondsAcctBalMthlyBatchByGCNInqList").get(0).get("holdAmtLCY").asDouble());

            jsonStr = "{" +
                    "\"key1\":\"null\"" +
                    "}";
            jsonNode = objectMapper.readTree(jsonStr);
            String temp = jsonNode.get("key1").asText();
            System.out.println(temp);
            temp = null;
            System.out.println(temp);
            System.out.println(jsonNode.get("key2"));
            System.out.println(jsonNode.get("key2").get("key2"));

            {
                jsonStr = "{\n" +
                        "    \"requestAddInfo\": {\n" +
                        "        \"functionId\": \"1047\",\n" +
                        "        \"workstationIp\": \"string\"\n" +
                        "    },\n" +
                        "    \"scheduleAuto\": {\n" +
                        "        \"channel\": \"751\",\n" +
                        "        \"execHour\": \"11\",\n" +
                        "        \"execMinute\": \"11\",\n" +
                        "        \"firstRunDate\": \"2023-11-29 11:11:46.4646\",\n" +
                        "        \"frequency\": 3,\n" +
                        "        \"function\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"functionName\": \"Autodebet PNET/PMOBX\",\n" +
                        "            \"functionModule\": \"autodebet\",\n" +
                        "            \"processName\": \"AUTODEBET_PROCESS\",\n" +
                        "            \"description\": \"Autodebet PNET/PMOBX \",\n" +
                        "            \"status\": \"1\",\n" +
                        "            \"lastModifiedDate\": \"2021-11-10 14:08:38\"\n" +
                        "        },\n" +
                        "        \"name\": \"123\",\n" +
                        "        \"nextExecDate\": \"2023-11-29\",\n" +
                        "        \"status\": \"1\"\n" +
                        "    }\n" +
                        "}";
            }
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testObjectMapper() {
        //            Class1 class1 = new Class1();
////            class1.setDate("2023-09-21");
//            class1.setDate_1("2023-09-21T21:38:10.461+07:00");
////            class1.setDate("2023-090");
//            class1.setAngka_1("1.12345");
//            class1.setAngka_2("12345");
//            Class2 class2 = new Class2();
//            objectMapper.updateValue(class2, class1);
//            System.out.println(objectMapper.writeValueAsString(class2));
    }


}
