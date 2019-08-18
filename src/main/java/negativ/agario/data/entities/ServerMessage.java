package negativ.agario.data.entities;

import lombok.Data;

import java.util.List;

@Data
public class ServerMessage {

    private List<GameEntity> staticObjects;
    private List<Player> players;
}
