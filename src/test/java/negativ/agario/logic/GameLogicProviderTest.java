package negativ.agario.logic;

import negativ.agario.configuration.ConfigurationService;
import negativ.agario.data.entities.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
public class GameLogicProviderTest {

    @InjectMocks
    private GameLogicProvider gameLogicProvider = new GameLogicProvider();

    @Mock
    private ConfigurationService config;

    @Mock
    private ConfigurationService.Player playerConfig;

    @Mock
    private ConfigurationService.GameField gameFieldConfig;

    @Mock
    private ConfigurationService.Statistics statisticsConfig;

    private Player player = new Player();

    @BeforeClass
    public static void init() {

    }

    @Before
    public void setup() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(config.getPlayer()).thenReturn(playerConfig);
        Mockito.when(playerConfig.getGrowCoefficient()).thenReturn(0.5f);

        Mockito.when(config.getGameField()).thenReturn(gameFieldConfig);
        Mockito.when(gameFieldConfig.getFoodCost()).thenReturn(0.5f);

        Mockito.when(config.getStatistics()).thenReturn(statisticsConfig);

        //call post-constructor
        Method postConstruct =  GameLogicProvider.class.getDeclaredMethod("init",null); // methodName,parameters
        postConstruct.setAccessible(true);
        postConstruct.invoke(gameLogicProvider);

        player.setX(0);
        player.setY(0);
        player.setSize(100);
    }

    @Test
    public void isNearTest() {
        assertTrue(gameLogicProvider.isNear(player, 50, 50));
        assertTrue(gameLogicProvider.isNear(player, 74, 74));
        assertFalse(gameLogicProvider.isNear(player, 75, 74));
        assertFalse(gameLogicProvider.isNear(player, 74, 75));
    }

    @Test
    public void eatPlayerTest() {
        Player player2 = new Player();
        player2.setSize(60);
        gameLogicProvider.eatPlayer(player, player2);
        assertEquals(130f, player.getSize(), 0.1f);
    }

    @Test
    public void eatFoodTest() {
        Player player2 = new Player();
        player2.setSize(60);
        gameLogicProvider.eatFood(player2);
        assertEquals(60.5f, player2.getSize(), 0.1f);
    }

}
