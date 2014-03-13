package blir.engine;

import blir.engine.game.Game;
import blir.engine.swing.SelectorGUI;

import java.util.*;
import java.util.logging.*;
import javax.swing.JFrame;

/**
 * BlirEngine's main class. Everything starts here.
 *
 * @author Blir
 */
public class BlirEngine {

    public static final Logger LOGGER = Logger.getLogger(BlirEngine.class.getName());
    public static final Random rng = new Random();

    private static final Map<String, Game> games = new HashMap<>();

    static {
        /* set up Logger */
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new BlirEngineFormatter("yyyy/MM/dd HH:mm:ss"));
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        openGameSelector();
    }

    /**
     * Registers default games.
     */
    public static void init() {
        registerGame(Game.gameOfLife);
        registerGame(Game.archiorzard);
        registerGame(Game.apocalypse);
        registerGame(Game.test);
    }

    /**
     * Opens the SinglePlayerGame Selector GUI.
     *
     * @return the Selector GUI opened
     */
    public static SelectorGUI<Game> openGameSelector() {
        SelectorGUI selector = new SelectorGUI<Game>(games.values(), JFrame.EXIT_ON_CLOSE) {

            @Override
            public void onSelectionMade(Game selection) {
                selection.init();
            }
        };
        
        selector.setVisible(true);
        return selector;
    }

    /**
     * Registers a SinglePlayerGame.
     *
     * @param game the SinglePlayerGame to register
     */
    public static void registerGame(Game game) {
        games.put(game.name, game);
    }
}
