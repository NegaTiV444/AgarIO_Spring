package negativ.agario.model;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Messages.ServerMessage;
import negativ.agario.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UpdateScheduler {

    private ServerMessage message = new ServerMessage();

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ConfigurationService config;

    @Autowired
    private GameField gameField;

    @Scheduled(fixedDelay = 1000)
    private void sendFoodState() {
        int difference = config.getGameField().getNumberOfStaticObjects() - gameField.getStaticObjects().size();
        for (int i = 0; i < difference; i++) {
            GameEntity gameEntity = new GameEntity();
            gameEntity.setColor(config.getPlayer().getAvailableColors()
                    .get(new Random().nextInt(config.getPlayer().getAvailableColors().size())));
            gameEntity.setSize(config.getGameField().getFoodSize());
            gameEntity.setX(new Random().nextInt(config.getGameField().getWidth()));
            gameEntity.setY(new Random().nextInt(config.getGameField().getHeight()));
            gameField.getStaticObjects().add(gameEntity);
        }
    }

    @Scheduled(fixedDelay = 10)
    public void sendPlayersState() {
        List<Player> currentState = new ArrayList<>(gameField.updateAndGet()
                .stream()
                .sorted(Comparator.comparingDouble(Player::getSize))
                .collect(Collectors.toList()));
        message.setPlayers(currentState);
        message.setStaticObjects(gameField.getStaticObjects());
        template.convertAndSend("/update", message);
    }
}
