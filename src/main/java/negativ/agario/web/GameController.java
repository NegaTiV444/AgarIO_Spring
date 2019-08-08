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
        //gameField.addPlayer(name);
        return "gameField";
    }

    @MessageMapping("/add-user")
    @SendTo("/config")
    private ConfigMessage handleAddUser(ClientMessage message) {
        gameField.addPlayer(message.getName());
        //System.out.println(message.getName());
        return configMessage;
    }

    @MessageMapping("/update")
    public void handleUpdate(ClientMessage clientMessage) {
        Player player = gameField.getPlayers().get(clientMessage.getName());
        if (player != null) {
            double distance = Math.sqrt(Math.pow(clientMessage.getMouseX() - player.getX(), 2) +
                    Math.pow(player.getY() - clientMessage.getMouseY(), 2));
            int newX, newY;
            newY = player.getY() - (int)Math.round(gameField.getSpeed() * (player.getY() - clientMessage.getMouseY()) / distance);
            if ((newY >= 0) && (newY <= configurationService.getGameField().getHeight())) {
                player.setY(newY);
            }
            newX = player.getX() + (int)Math.round(gameField.getSpeed() * (clientMessage.getMouseX() - player.getX()) / distance);
            if ((newX >= 0) && (newX <= configurationService.getGameField().getWidth())) {
                player.setX(newX);
            }
            //System.out.println(player.getX() + player.getY());
//            Vector mousePos = new Vector();
//            mousePos.setX(clientMessage.getMouseX());
//            mousePos.setY(clientMessage.getMouseY());
//            mousePos.subtract(player.getPos());
//            mousePos.multiply(gameField.getSpeed());
//            player.getPos().add(mousePos);

//            player.setX((float)(player.getgetX() + gameField.getSpeed() *
//                    Math.sin(Math.atan((clientMessage.getMouseY() - player.getY())/(clientMessage.getMouseX() - player.getX())))));
//            player.setY((float)(player.getY() + gameField.getSpeed() *
//                    Math.cos(Math.atan((clientMessage.getMouseY() - player.getY())/(clientMessage.getMouseX() - player.getX())))));
        }
        //System.out.println("Message received ");
    }

}
