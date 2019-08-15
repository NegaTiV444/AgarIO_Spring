package negativ.agario.entities;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Player extends GameEntity {


    @Id
    private String name;

//    public boolean isNear(int x, int y) {
//        return ((Math.abs(this.getX() - x) < this.getSize() * 0.75) && (Math.abs(this.getY() - y) < this.getSize() * 0.75));
//    }


}
