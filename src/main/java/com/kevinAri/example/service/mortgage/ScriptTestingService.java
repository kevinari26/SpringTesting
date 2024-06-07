package com.kevinAri.example.service.mortgage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ScriptTestingService {
    @Autowired
    ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void testIncreaseEfficiency() throws Exception {
//        test2();

    }



    private void test1() throws Exception {
        File jsonFile = Paths.get("src/main/resources/jsonFiles/mortgage/", "testJson.json").toFile();
        JsonNode jsonNode = objectMapper.readTree(jsonFile);
        List<String> scriptType = new ArrayList<>(Arrays.asList(
                "LinkedListAndDirty",
                "LinkedListAndClean",
                "ArrayListAndDirty",
                "ArrayListAndClean",
                "empty"
        ));
        for (int k=0; k<1; k++) {
            System.out.println(k);
            for (int i=0; i<4; i++) {
                Date start = new Date();
                for (int j=0; j<100000; j++) {
                    doScript(jsonNode, scriptType.get(i));
                }
                Date end = new Date();
                long diffInMillies = Math.abs(end.getTime() - start.getTime());
                System.out.println(diffInMillies);
            }
        }
    }

    private void doScript(JsonNode jsonNode, String type) {
        // ArrayList and dirty get
        if ("ArrayListAndDirty".equals(type)) {
            List<String> arrayList = new ArrayList<>();
            for (int i=0; i<jsonNode.get("PaymentCalendarList").get("CalendarItem").size(); i++) {
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("Currency").asText());
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("LocalValue").asText());
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("Value").asText());
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("Currency").asText());
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("LocalValue").asText());
                arrayList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("Value").asText());
            }
        }
        // ArrayList and clean get
        else if ("ArrayListAndClean".equals(type)) {
            List<String> arrayList = new ArrayList<>();
            JsonNode calendar = jsonNode.get("PaymentCalendarList").get("CalendarItem");
            for (JsonNode calendarItem : calendar) {
                JsonNode temp = calendarItem.get("OutstandingAmount");
                arrayList.add(temp.get("Currency").asText());
                arrayList.add(temp.get("LocalValue").asText());
                arrayList.add(temp.get("Value").asText());
                temp = calendarItem.get("PastDueAmount");
                arrayList.add(temp.get("Currency").asText());
                arrayList.add(temp.get("LocalValue").asText());
                arrayList.add(temp.get("Value").asText());
            }
        }
        // LinkedList and dirty get
        else if ("LinkedListAndDirty".equals(type)) {
            List<String> linkedList = new LinkedList<>();
            for (int i=0; i<jsonNode.get("PaymentCalendarList").get("CalendarItem").size(); i++) {
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("Currency").asText());
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("LocalValue").asText());
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("OutstandingAmount").get("Value").asText());
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("Currency").asText());
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("LocalValue").asText());
                linkedList.add(jsonNode.get("PaymentCalendarList").get("CalendarItem").get(i).get("PastDueAmount").get("Value").asText());
            }
        }
        // LinkedList and clean get
        else if ("LinkedListAndClean".equals(type)) {
            List<String> linkedList = new LinkedList<>();
            JsonNode calendar = jsonNode.get("PaymentCalendarList").get("CalendarItem");
            for (JsonNode calendarItem : calendar) {
                JsonNode temp = calendarItem.get("OutstandingAmount");
                linkedList.add(temp.get("Currency").asText());
                linkedList.add(temp.get("LocalValue").asText());
                linkedList.add(temp.get("Value").asText());
                temp = calendarItem.get("PastDueAmount");
                linkedList.add(temp.get("Currency").asText());
                linkedList.add(temp.get("LocalValue").asText());
                linkedList.add(temp.get("Value").asText());
            }
        }
    }


    private void test2() throws Exception {
        List<String> scriptType = new ArrayList<>(Arrays.asList(
                "SingleDate",
                "MultipleDate",
                "LinkedList",
                "ArrayList",
                "empty"
        ));
        for (int k=0; k<2; k++) {
            System.out.println(k);
            for (int i=0; i<2; i++) {
                Date start = new Date();
                for (int j=0; j<100; j++) {
                    doScript2(scriptType.get(i), 10000000);
                }
                Date end = new Date();
                long diffInMillies = Math.abs(end.getTime() - start.getTime());
                System.out.println(diffInMillies);
            }
        }
    }

    private void doScript2(String type, int nRep) {
        if ("ArrayList".equals(type)) {
            List<String> list = new ArrayList<>();
            for (int i=0; i<nRep; i++) {
                list.add("ok");
            }
        }
        else if ("LinkedList".equals(type)) {
            List<String> list = new LinkedList<>();
            for (int i=0; i<nRep; i++) {
                list.add("ok");
            }
        }
        else if ("SingleDate".equals(type)) {
            List<Date> list = new ArrayList<>();
            Date date = new Date();
            for (int i=0; i<nRep; i++) {
                list.add(date);
            }
        }
        else if ("MultipleDate".equals(type)) {
            List<Date> list = new ArrayList<>();
            for (int i=0; i<nRep; i++) {
                list.add(new Date());
            }
        }
    }
}
