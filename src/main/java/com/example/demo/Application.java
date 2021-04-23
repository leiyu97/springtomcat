package com.example.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            System.out.println("************* start *******************");
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
            System.out.println("******************************");
            printWelcome();
            readFromPropertiesFile();
        };
    }

    public void readFromPropertiesFile() {
        Properties prop = new Properties();
        try {
            prop.load(Application.class.getClassLoader().getResourceAsStream("deploy.properties"));

            System.out.println("**** properties content is "+ prop.getProperty("test"));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public  void printWelcome() {
        System.out.println("This is to check if the build is taking the new code");
    }

}