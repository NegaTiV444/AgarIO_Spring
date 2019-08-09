package negativ.agario.entities;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Player extends GameEntity {

    private final int BASIC_SIZE = 20; //TODO player factory

    @Id
    private String name;
    private int score = 0;

    public boolean isNear(GameEntity gameEntity) {
        return ((Math.abs(this.getX() - gameEntity.getX()) < this.getSize() * 0.75) && (Math.abs(this.getY() - gameEntity.getY()) < this.getSize() * 0.75));
    }

    public void addPoints(float number) {
        score += number * 100; //TODO do
        setSize(BASIC_SIZE + score / 100);
    }

}
