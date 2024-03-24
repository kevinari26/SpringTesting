package com.kevinAri.example;

import com.kevinAri.example.service.AppService;
import com.kevinAri.example.service.mortgage.MortgageAppService;
import com.kevinAri.example.service.TestAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Autowired
    AppService appService;
    @Autowired
    MortgageAppService mortgageAppService;
    @Autowired
    TestAppService testAppService;

	@Override
	public void run(String... args) {
		appService.execute();
		mortgageAppService.execute();
		testAppService.execute();
	}

}
