package blir.engine;

import blir.engine.game.Game;
import blir.engine.swing.SelectorGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Blir
 */
public class BlirEngine {

    public static final Logger LOGGER = Logger.getLogger(BlirEngine.class.getName());

    private static final Map<String, Game> games = new HashMap<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        registerGame(Game.gameOfLife);

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
