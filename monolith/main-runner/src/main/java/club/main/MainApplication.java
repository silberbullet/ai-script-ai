package club.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "club")
@ConfigurationPropertiesScan(basePackages = "club")
public class MainApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
