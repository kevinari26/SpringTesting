package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class AppService2 {
    @Autowired
    ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
//            int count = 5;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//            String sysdate = sdf.format(new Date());
//            String fraudId = "FR".concat(sysdate).concat(setLeadingZero(String.valueOf(count), "0000000"));
//            System.out.println();
        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println("error");
            } catch (Exception ex) {

            }
        }
    }

    public static String setLeadingZero(String str, String format){
        NumberFormat formatter = new DecimalFormat(format);
        String res = formatter.format(Long.valueOf(str).longValue());
        return res;
    }

}
