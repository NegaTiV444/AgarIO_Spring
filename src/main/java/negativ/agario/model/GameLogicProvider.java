package negativ.agario.model;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Player;
import negativ.agario.entities.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameLogicProvider {

    private float growCoefficient;
    private float foodCost;
    private int numberOfRecords;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private ConfigurationService config;

    @PostConstruct
    private void init() {
        growCoefficient = config.getPlayer().getGrowCoefficient();
        foodCost = config.getGameField().getFoodCost();
        numberOfRecords = config.getStatistics().getNumberOfRecords();
    }

    public boolean isNear(GameEntity gameEntity, double x, double y) {
        return ((Math.abs(gameEntity.getX() - x) < gameEntity.getSize() * 0.75)
                && (Math.abs(gameEntity.getY() - y) < gameEntity.getSize() * 0.75));
    }

    public void eatPlayer(Player player1, Player player2) {
        player1.setSize(player1.getSize() + (int) (player2.getSize() * growCoefficient));
    }

    public void eatFood(Player player) {
        player.setSize(player.getSize() + foodCost);
    }

    public void checkRecord(Player player) {
        List<Record> records = recordRepository.findAllByOrderByScore();
        if (records.size() == numberOfRecords) {
            if (records.get(numberOfRecords - 1).getScore() < player.getSize() * 10) {
                recordRepository.delete(records.get(numberOfRecords - 1));
                recordRepository.save(new Record(player.getName(), player.getSize() * 10));
            }
        } else {
            recordRepository.save(new Record(player.getName(), player.getSize() * 10));
        }

    }

}
