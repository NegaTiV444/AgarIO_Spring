package negativ.agario.web;

import negativ.agario.config.ConfigurationService;
import negativ.agario.entities.Messages.ClientMessage;
import negativ.agario.entities.Messages.ConfigMessage;
import negativ.agario.entities.Player;
import negativ.agario.model.GameField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private GameField gameField;

    @Autowired
    private ConfigMessage configMessage;

    @GetMapping
    public String gameField() {
        return "gameField";
    }

    @MessageMapping("/add-user")
    @SendTo("/config")
    private ConfigMessage handleAddUser(ClientMessage message) {
        gameField.addPlayer(message.getName());
        return configMessage;
    }

    @MessageMapping("/update")
    public void handleUpdate(ClientMessage clientMessage) {
        Player player = gameField.getPlayers().get(clientMessage.getName());
        if (player != null) {
            double distance = Math.sqrt(Math.pow(clientMessage.getMouseX() - player.getX(), 2) +
                    Math.pow(player.getY() - clientMessage.getMouseY(), 2));
            double newX, newY;
            newY = player.getY() - gameField.getSpeed() * (player.getY() - clientMessage.getMouseY()) / distance;
            if ((newY >= 0) && (newY <= configurationService.getGameField().getHeight())) {
                player.setY(newY);
            }
            newX = player.getX() + gameField.getSpeed() * (clientMessage.getMouseX() - player.getX()) / distance;
            if ((newX >= 0) && (newX <= configurationService.getGameField().getWidth())) {
                player.setX(newX);
            }
        }
    }

}
