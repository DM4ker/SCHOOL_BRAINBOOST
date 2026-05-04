package DM4.brainboostbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BrainBoostBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrainBoostBackendApplication.class, args);
    }

}

