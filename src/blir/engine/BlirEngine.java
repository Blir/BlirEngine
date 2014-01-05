package blir.engine;

import blir.engine.game.Game;
import blir.engine.swing.SelectorGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Blir
 */
public class BlirEngine {

    public static final Logger LOGGER = Logger.getLogger(BlirEngine.class.getName());
    public static final Random rng = new Random();

    private static final Map<String, Game> games = new HashMap<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new BlirEngineFormatter("yyyy/MM/dd HH:mm:ss"));
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);

        registerGame(Game.gameOfLife);
        registerGame(Game.archiorzard);

        new SelectorGUI<Game>(games.values()) {

            @Override
            public void onSelectionMade(Game selection) {
                selection.init();
            }
        }.setVisible(true);
    }

    public static void registerGame(Game game) {
        games.put(game.name, game);
    }
}
