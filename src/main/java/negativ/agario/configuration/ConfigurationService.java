package negativ.agario.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    private Statistics statistics = new Statistics();

    @Data
    public static class GameField {
        @NotNull
        private Integer height;
        @NotNull
        private Integer width;
        @NotNull
        private Integer numberOfStaticObjects;
        @NotNull
        private Float foodCost;
        @NotNull
        private Float foodSize;
    }

    @Data
    public static class Player {
        @NotNull
        private Float speed;
        @NotNull
        private Float basicSize;
        @NotNull
        private Float growCoefficient;
        @NotEmpty
        private List<String> availableColors = new ArrayList<>();
    }

    @Data
    public static class Statistics {
        @NotNull
        private Integer numberOfRecords;
    }
}
