package blir.engine.game;

import java.util.LinkedList;

/**
 *
 * @author Blir
 */
public class Scoreboard extends LinkedList<Alliance> {

    private final Game game;

    public Scoreboard(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(game.name)
                .append(" - (")
                .append(game.getTick())
                .append(":")
                .append(String.format("%.3f",(double) game.getFrames() / game.getTick()))
                .append("): ");
        Alliance no1 = null;
        for (Alliance alliance : this) {
            if (no1 == null || no1.getScore() < alliance.getScore()) {
                no1 = alliance;
            }
            string.append(alliance.name).append(": ").append(alliance.getScore()).append(", ");
        }
        return no1 == null ? string.toString() : string.append(no1.name).append(no1.name.endsWith("s") ? " are" : " is").append(" winning").toString();
    }
}
