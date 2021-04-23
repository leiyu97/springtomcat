package com.example.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("file:${CONFIG_LOCATION}/deploy.properties")
public class Application {

    @Value("${CONFIG_LOCATION}")
    private String configLocation;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");
            System.out.println("Config location: " + configLocation);

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

            printWelcome();
            //readFromPropertiesFile();
        };
    }


    /*public void readFromPropertiesFile() {
        Properties prop = new Properties();
        try {

            String propsLocation = System.getenv("CONFIG_LOCATION");
           // prop.load(Application.class.getClassLoader().getResourceAsStream("deploy.properties"));

            System.out.println("**** properties content is "+ prop.getProperty("test"));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }*/

    public  void printWelcome() {
        System.out.println("This is to check if the build is taking the new code");
        System.out.println("****"+ env.getProperty("test"));
    }

}