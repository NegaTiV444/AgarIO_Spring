package negativ.agario.entities.Messages;

import lombok.Data;
import negativ.agario.entities.GameEntity;
import negativ.agario.entities.Player;

import java.util.List;

@Data
public class ServerMessage {

    private List<GameEntity> staticObjects;
    private List<Player> players;
}
