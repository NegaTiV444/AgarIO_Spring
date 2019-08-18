package negativ.agario.data.entities;

import lombok.Data;

@Data
public class ConfigMessage {

    private int gameFieldWidth;
    private int gameFieldHeight;
    private boolean isOk;
    private String name;
}
