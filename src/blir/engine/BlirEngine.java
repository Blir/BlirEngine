package blir.engine;

import blir.engine.game.Game;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Blir
 */
public class BlirEngine {

    public static final Logger LOGGER = Logger.getLogger(BlirEngine.class.getName());
    private static final List<Game> games = new LinkedList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        registerGame(Game.gameOfLife);
        Game.gameOfLife.init();
    }
    
    public static void registerGame(Game game) {
        games.add(game);
    }
}
