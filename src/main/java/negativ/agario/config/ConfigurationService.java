package negativ.agario.config;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "app")
@Validated
public class ConfigurationService {

    private GameField gameField = new GameField();
    private Player player = new Player();

    @Data
    public static class GameField {
        @NotNull
        private Integer height;

        @NotNull
        private Integer width;

        @NotNull
        private Integer numberOfStaticObjects;
    }

    @Data
    public static class Player {
        @NotNull
        private Float speed;
        @NotNull
        private Integer basicSize;
        @NotNull
        private Float growCoefficient;
        @NotEmpty
        private List<String> availableColors = new ArrayList<>();
    }


}
