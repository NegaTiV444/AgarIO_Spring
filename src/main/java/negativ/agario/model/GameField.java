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

    @Autowired
    private ConfigurationService config;

    @Autowired
    private UpdateScheduler updateScheduler;

    @Autowired
    private GameLogicProvider gameLogicProvider;

    @PostConstruct
    private void init() {
        GAME_FIELD_WIDTH = config.getGameField().getWidth();
        GAME_FIELD_HEIGHT = config.getGameField().getHeight();
        availableColors = config.getPlayer().getAvailableColors();
        speed = config.getPlayer().getSpeed();
    }

    private int GAME_FIELD_WIDTH;
    private int GAME_FIELD_HEIGHT;

    private List<String> availableColors;

    private float speed;

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
                .sorted(Comparator.comparingDouble(Player::getSize).reversed())
                .collect(Collectors.toList()));
        for (int i = 0; i < currentState.size(); i++) {
            Player firstPlayer = currentState.get(i);
            int j = i + 1;
            while (j < currentState.size()) {
                Player secondPlayer = currentState.get(j);
                if (gameLogicProvider.isNear(firstPlayer, secondPlayer.getX(), secondPlayer.getY())) {
                    gameLogicProvider.eatPlayer(firstPlayer, secondPlayer);
                    removePlayer(secondPlayer);
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

    public void removePlayer(Player player) {
        gameLogicProvider.checkRecord(player);
        players.remove(player.getName());
    }

    //returns empty string if the name is too long
    public String validateName(String name) {
        if (name == null && name.length() == 0)
            name = "Anonymous";
        if (name.length() < 16) {
            if (players.containsKey(name)) {
                return addNumberToName(name, 1);
            } else
                return name;
        } else
            return "";
    }

    private String addNumberToName(String name, int number) {
        String newName = name + "(" + number + ")";
        if (players.containsKey(newName)) {
            return addNumberToName(name, number + 1);
        } else
            return newName;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<GameEntity> getStaticObjects() {
        return staticObjects;
    }
}
