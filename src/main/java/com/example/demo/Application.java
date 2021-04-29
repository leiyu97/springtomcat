package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.AbstractFileConfiguration;

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
           /* for (String beanName : beanNames) {
                System.out.println(beanName);
            }*/

            printWelcome();
          //  readFromPropertiesFile();
            // readFromPropFileFromClasspath();
            readFromPropConfig();
        };
    }


    public void readFromPropertiesFile() {
        Properties prop = new Properties();
        try {

            String propsLocation = System.getenv("CONFIG_LOCATION");
            System.out.println("propsLocation is " + propsLocation);
            // prop.load(Application.class.getClassLoader().getResourceAsStream("deploy.properties"));
            FileInputStream fis = new FileInputStream(propsLocation + "/deploy.properties");
            prop.load(fis);
            System.out.println("**** properties content is " + prop.getProperty("test"));


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void readFromPropFileFromClasspath() {
        Properties prop2 = new Properties();

        try {
            prop2.load(Application.class.getClassLoader().getResourceAsStream("deploy.properties"));
            // ClassLoader cls=  Application.class.getClassLoader();

            System.out.println("**** properties content from classloader is " + prop2.getProperty("test"));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void printWelcome() {
        System.out.println("This is to check if the build is taking the new code");
        System.out.println("****" + env.getProperty("test"));
        //env.
    }



    public void readFromPropConfig() throws ConfigurationException {
        PropertiesConfiguration configCustomisations = new PropertiesConfiguration("deploy.properties");
        System.out.println("Application.readFromPropConfig: "+ configCustomisations.getString("test"));
    }


}