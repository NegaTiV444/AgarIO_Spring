package negativ.agario.model;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameField {

    private ConfigurationService config;

    @Autowired
    private UpdateScheduler updateScheduler;

    private GameLogicProvider gameLogicProvider;

    @Autowired
    public GameField(ConfigurationService configurationService, GameLogicProvider gameLogicProvider) {
        this.config = configurationService;
        GAME_FIELD_WIDTH = configurationService.getGameField().getWidth();
        GAME_FIELD_HEIGHT = configurationService.getGameField().getHeight();
        availableColors = configurationService.getPlayer().getAvailableColors();
        speed = configurationService.getPlayer().getSpeed();
        this.gameLogicProvider = gameLogicProvider;
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

    public void addPlayer(String name) {
        Player newPlayer = new Player();
        newPlayer.setColor(availableColors.get(new Random().nextInt(availableColors.size())));
        newPlayer.setName(name);
        newPlayer.setSize(config.getPlayer().getBasicSize());
        findSpawnPlace(newPlayer);
        players.put(newPlayer.getName(), newPlayer);
    }

    private void findSpawnPlace(Player player) {
        Random random = new Random();
        do {
            player.setX(random.nextInt(GAME_FIELD_WIDTH - GAME_FIELD_WIDTH / 5 * 2) + GAME_FIELD_WIDTH / 5);
            player.setY(random.nextInt(GAME_FIELD_HEIGHT - GAME_FIELD_HEIGHT / 5 * 2) + GAME_FIELD_HEIGHT / 5);
        } while (players.values().stream().anyMatch(p -> gameLogicProvider.isNear(p, player.getX(), player.getY())));
    }

    public List<Player> updateAndGet() { //TODO Check time and choose the fastest way
        List<Player> currentState = new ArrayList<>(players
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Player::getSize).reversed())
                .collect(Collectors.toList()));
        for (int i = 0; i < currentState.size(); i++) {
            Player firstPlayer = currentState.get(i);
            int j = i + 1;
            while (j < currentState.size()) {
                Player secondPlayer = currentState.get(j);
                if (gameLogicProvider.isNear(firstPlayer, secondPlayer.getX(), secondPlayer.getY())) {
                    gameLogicProvider.eatPlayer(firstPlayer, secondPlayer);
                    players.remove(secondPlayer.getName());
                    currentState.remove(secondPlayer);
                } else
                    j++;
            }
            j = 0;
            while (j < staticObjects.size()) {
                GameEntity entity = staticObjects.get(j);
                if (gameLogicProvider.isNear(firstPlayer, entity.getX(), entity.getY())) {
                    gameLogicProvider.eatFood(firstPlayer);
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

    public List<GameEntity> getStaticObjects() {
        return staticObjects;
    }
}
