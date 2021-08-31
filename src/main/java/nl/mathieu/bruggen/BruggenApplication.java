package nl.mathieu.bruggen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BruggenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BruggenApplication.class, args);
    }

}
