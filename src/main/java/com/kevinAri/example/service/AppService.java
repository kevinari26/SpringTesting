package com.kevinAri.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


@Service
public class AppService {
    @Autowired
    ObjectMapper objectMapper;

    @Setter
    @Getter
    static class Class1 {
        private String date_1;
        private String angka_1;
        private String angka_2;
    }
    @Setter
    @Getter
    static class Class2 {
        private Date date_1;
        @JsonProperty("ANGKA_1")
        private Float angka11;
        private Long angka_2;
        private Long angka_asd_asd;
    }


    public void execute() {
        try {
//            TIMESTAMP timestamp = new TIMESTAMP();
//            requestBody.put("dob", new Timestamp(System.currentTimeMillis()));

//            Class1 class1 = new Class1();
////            class1.setDate("2023-09-21");
//            class1.setDate_1("2023-09-21T21:38:10.461+07:00");
////            class1.setDate("2023-090");
//            class1.setAngka_1("1.12345");
//            class1.setAngka_2("12345");
//            Class2 class2 = new Class2();
//            objectMapper.updateValue(class2, class1);
//            System.out.println(objectMapper.writeValueAsString(class2));


//            String sDate1="21/09/2023";
//            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//            System.out.println(sDate1+"\t"+date1);
//            System.out.println(objectMapper.writeValueAsString(date1));



            System.out.println();
            System.out.println("test");
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("error");
        }
    }

}
