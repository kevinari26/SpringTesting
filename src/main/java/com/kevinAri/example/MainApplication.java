package com.kevinAri.example;

import com.kevinAri.example.service.AppService;
import com.kevinAri.example.service.AppService2;
import com.kevinAri.example.service.AppService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Autowired
    AppService appService;
    @Autowired
    AppService2 appService2;
    @Autowired
    AppService3 appService3;

	@Override
	public void run(String... args) {
		appService.execute();
		appService2.execute();
		appService3.execute();
	}

}
