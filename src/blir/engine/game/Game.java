package blir.engine.game;

import blir.engine.BlirEngine;
import java.util.logging.Level;

/**
 *
 * @author Blir
 */
public abstract class Game implements Runnable {

    public static final GameOfLife gameOfLife = new GameOfLife();
    public static final ArchIorZard archiorzard = new ArchIorZard();
    public static final Apocalypse apocalypse = new Apocalypse();
    public static final ScrollingGame test = new ScrollingGame("test", 0, 15, 50);

    /**
     * Delegate Logger method.
     *
     * @param level log level
     * @param msg log message
     * @param thrown exception
     */
    public void log(Level level, String msg, Throwable thrown) {
        log(level, name, msg, thrown);
    }

    /**
     * Delegate Logger method.
     *
     * @param level log level
     * @param msg log format string message
     * @param params log parameters
     * @see
     * http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
     */
    public void log(Level level, String msg, Object... params) {
        log(level, name, msg, params);
    }

    /**
     * Delegate Logger method.
     *
     * @param level log level
     * @param msg log message
     */
    public void log(Level level, String msg) {
        log(level, name, msg);
    }
    
    public static void log(Level level, String name, String msg, Throwable thrown) {
        BlirEngine.LOGGER.log(level, String.format("[%s] %s", name, msg), thrown);
    }

    public static void log(Level level, String name, String msg, Object... params) {
        BlirEngine.LOGGER.log(level, String.format("[%s] %s", name, msg), params);
    }

    public static void log(Level level, String name, String msg) {
        BlirEngine.LOGGER.log(level, String.format("[%s] %s", name, msg));
    }

    /**
     * The name of the Game.
     */
    public final String name;

    public Game(String name) {
        this.name = name;
    }

    /**
     * Called once to initialize the SinglePlayerGame. This is where a
     * SinglePlayerGame should register EntityTypes, Items, set up Alliances,
     * Teams, and the Scoreboard, set GUIs to be visible and start the
     * SinglePlayerGame.
     */
    public abstract void init();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SinglePlayerGame ? ((SinglePlayerGame) obj).name.equals(this.name) : false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
