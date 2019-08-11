package negativ.agario.model;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GameLogicProvider {

    private float growCoefficient;

    @Autowired
    private ConfigurationService config;

    @PostConstruct
    private void init() {
        growCoefficient = config.getPlayer().getGrowCoefficient();
    }

    public boolean isNear(GameEntity gameEntity, int x, int y) {
        return ((Math.abs(gameEntity.getX() - x) < gameEntity.getSize() * 0.0075)
                && (Math.abs(gameEntity.getY() - y) < gameEntity.getSize() * 0.0075));
    }

    public void eatPlayer(Player player1, Player player2) {
        player1.setSize(player1.getSize() + (int)(player2.getSize() * growCoefficient));
    }

    public void eatFood(Player player) {
        player.setSize(player.getSize() + config.getGameField().getFoodCost());
    }
}
