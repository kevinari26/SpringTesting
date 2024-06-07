package com.kevinAri.example.service.testing.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;
import com.kevinAri.example.model.ActionEnum;
import com.kevinAri.example.service.testing.jasper.JasperService;
import com.kevinAri.example.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oracle.sql.TIMESTAMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class OnlineTestService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JasperService jasperService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    public void execute() {
        try {
//            Date date = new Date();
//            System.out.println(date.toString());
//            System.out.println(date.getTime());
//            System.out.println(UUID.randomUUID());

            // case1
//            List<String> requests;
//            requests = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
//            requests = new ArrayList<>(Arrays.asList("1", "2", "1", "4", "5"));
//            System.out.println(getMinimumSize(requests, 0));

            // case2
//            List<Integer> numbers;
//            numbers = new ArrayList<>(Arrays.asList(6, 2, 4, 10));
//            numbers = new ArrayList<>(Arrays.asList(4, 2, 1, 3));
//            numbers = new ArrayList<>(Arrays.asList(4, -2, -1, 3));
//            closestNumbers(numbers);

            // case3
            List<Integer> scores;
            scores = new ArrayList<>(Arrays.asList(25, 50, 50, 100));
            scores = new ArrayList<>(Arrays.asList(0, 0, 1, 2, 3, 3, 4, 4, 5, 6, 6, 7));
            numPlayers(1, scores);
        } catch (Exception e) {
            try {
//                errorLog(log, e);
                e.printStackTrace();
                System.out.println("error");
            } catch (Exception ex) {

            }
        }
    }

    // case 1
    public static int getMinimumSize(List<String> requests, int k) {
        // 0: last location
        // 1: min distance
        Map<String, List<Integer>> mapMinDist = new HashMap<>();
        for (int i=0; i<requests.size(); i++) {
            String req = requests.get(i);
            List<Integer> temp = mapMinDist.get(req);
            if (temp == null) {
                mapMinDist.put(req, new ArrayList<>(Arrays.asList(i, null)));
            }
            else {
                Integer dist = i - temp.get(0);
                if (temp.get(1)==null || dist<temp.get(1)) {
                    temp.set(1, dist);
                }
            }
        }

        // find min val
        int output = 10001;
        for (Map.Entry<String, List<Integer>> entry : mapMinDist.entrySet()) {
            Integer temp = entry.getValue().get(1);
            if (temp!=null && temp<output) {
                output = temp;
            }
        }

        if (output==10001) return -1;
        else return output;
    }

    // case 2
    public static void closestNumbers(List<Integer> numbers) {
        quickSort(numbers, 0, numbers.size()-1);

        // algo
        List<Integer> listDist = new LinkedList<>();
        int minDist = 1000000001;
        for (int i = 1; i < numbers.size(); i++) {
//            int temp = Math.abs(numbers.get(i) - numbers.get(i-1));
            int temp = numbers.get(i) - numbers.get(i-1);
            listDist.add(temp);
            if (temp < minDist) minDist = temp;
        }

        for (int i = 0; i < listDist.size(); i++) {
            if (listDist.get(i) == minDist) {
                System.out.println(String.format("%d %d", numbers.get(i), numbers.get(i+1)));
            }
        }
    }

    private static void quickSort(List<Integer> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private static int partition(List<Integer> arr, int begin, int end) {
        int pivot = arr.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j) <= pivot) {
                i++;

                int swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        int swapTemp = arr.get(i+1);
        arr.set(i+1, arr.get(end));
        arr.set(end, swapTemp);

        return i+1;
    }

    // case3
    public static int numPlayers(int k, List<Integer> scores) {
        quickSort(scores, 0, scores.size()-1);

        // calculate rank
        int lastRank = 0;
        int lastScore = -1;
        int counter = 0;
        List<Integer> listRank = new LinkedList<>();
        for (int i=scores.size()-1; i>=0; i--) {
            int score = scores.get(i);

            if (score==0) {
                listRank.add(0, 100001);
            }
            else {
                if (lastScore != score) {
                    lastScore = score;
                    if (counter!=0) {
                        lastRank += counter+1;
                        counter = 0;
                    } else {
                        lastRank ++;
                    }
                }
                else {
                    counter ++;
                }
                listRank.add(0, lastRank);
            }
        }
//        System.out.println(listRank);

        // calculate winning player
        for (int i = 0; i < listRank.size(); i++) {
            if (listRank.get(i) <= k) {
                return listRank.size() - i;
            }
        }
        return 0;
    }
}
