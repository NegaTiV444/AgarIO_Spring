package negativ.agario.model;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameField {

    private ConfigurationService configurationService;

    @Autowired
    private ScheduledUpdatesOnTopic scheduledUpdatesOnTopic;

    @Autowired
    public GameField(ConfigurationService configurationService) {
        this.configurationService = configurationService;
        GAME_FIELD_WIDTH = configurationService.getGameField().getWidth();
        GAME_FIELD_HEIGHT = configurationService.getGameField().getHeight();
        availableColors = configurationService.getPlayer().getAvailableColors();
        speed = configurationService.getPlayer().getSpeed();
    }

    private final int GAME_FIELD_WIDTH;
    private final int GAME_FIELD_HEIGHT;

    private final List<String> availableColors;

    private final float speed;

    public float getSpeed() {
        return speed;
    }

    private List<GameEntity> staticObjects = new ArrayList<>();
    private Map<String, Player> players = new HashMap<>();

    public List<GameEntity> getStaticObjects() {
        return staticObjects;
    }

    public void addPlayer(String name) {
        Player newPlayer = new Player();
        newPlayer.setColor(availableColors.get(new Random().nextInt(availableColors.size())));

        newPlayer.setName(name);

        newPlayer.setSize(configurationService.getPlayer().getBasicSize());

        newPlayer.setX(new Random().nextInt(GAME_FIELD_WIDTH - GAME_FIELD_WIDTH / 5 * 2) + GAME_FIELD_WIDTH / 5);
        newPlayer.setY(new Random().nextInt(GAME_FIELD_HEIGHT - GAME_FIELD_HEIGHT / 5 * 2) + GAME_FIELD_HEIGHT / 5);
        players.put(newPlayer.getName(), newPlayer);
    }

    private void findSpawnPlace(Player player) { //TODO do
        Random random = new Random();
        int x, y;
    }

    public List<Player> updateAndGet() { //TODO Check time and choose the fastest way
        List<Player> currentState = new ArrayList<>(players
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .collect(Collectors.toList()));
        for (int i = 0; i < currentState.size(); i++) {
            Player firstPlayer = currentState.get(i);
            int j = i + 1;
            while (j < currentState.size()) {
                Player secondPlayer = currentState.get(j);
                if (firstPlayer.isNear(secondPlayer)) {
                    firstPlayer.addPoints(secondPlayer.getScore() / 100);
                    players.remove(secondPlayer.getName());
                    currentState.remove(secondPlayer);
                } else
                    j++;
            }
            j = 0;
            while (j < staticObjects.size()) {
                GameEntity entity = staticObjects.get(j);
                if (firstPlayer.isNear(entity)) {
                    firstPlayer.addPoints(0.5f); //TODO add entity cost to properties
                    staticObjects.remove(entity);
                } else
                    j++;
            }
        }
        return currentState;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }
}
