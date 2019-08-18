package negativ.agario.entities.Messages;

import lombok.Data;
import negativ.agario.config.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConfigMessage {

    private int gameFieldWidth;
    private int gameFieldHeight;
    private boolean isOk;
    private String name;
}
