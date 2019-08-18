package negativ.agario;

import negativ.agario.configuration.ConfigurationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(ConfigurationService.class)
public class AgarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgarioApplication.class, args);
    }

}
