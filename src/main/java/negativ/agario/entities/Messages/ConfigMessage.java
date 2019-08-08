package negativ.agario.entities.Messages;

import lombok.Data;
import negativ.agario.config.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
//@PropertySource("classpath/resources:application.properties")
@Component
public class ConfigMessage {

    private ConfigurationService configurationService;

    @Autowired
    public ConfigMessage(ConfigurationService configurationService) {
        this.configurationService = configurationService;
        gameFieldWidth = configurationService.getGameField().getWidth();
        gameFieldHeight = configurationService.getGameField().getHeight();
    }

    private int gameFieldWidth;
    private int gameFieldHeight;
}