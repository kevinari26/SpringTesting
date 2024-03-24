package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TestAppService {
    @Autowired
    ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
            log.info("test app service");
//            terbilang();


        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println("error");
            } catch (Exception ex) {

            }
        }
    }

    private void test() {
        try {
            int bilangan = 1234;
            String bilStr = String.valueOf(bilangan);
            int total = 0;
            while (bilStr.length() > 1) {
                total = 0;
                for (int i = 0; i < bilStr.length(); i++) {
                    total += Integer.parseInt(String.valueOf(bilStr.charAt(i)));
                }
                bilStr = String.valueOf(total);
            }
        } catch (Exception e) {

        }
    }

}
