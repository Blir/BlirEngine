package blir.engine.game;

import java.util.LinkedList;

/**
 *
 * @author Blir
 */
public class Scoreboard extends LinkedList<Alliance> {

    public String toString(String name, int tick) {
        StringBuilder string = new StringBuilder(name)
                .append(" - (")
                .append(tick)
                .append("): ");
        Alliance no1 = null;
        for (Alliance alliance : this) {
            if (no1 == null || no1.getScore() < alliance.getScore()) {
                no1 = alliance;
            }
            string.append(alliance.name).append(": ").append(alliance.getScore()).append(", ");
        }
        return no1 == null ? name : string.append(no1.name).append(no1.name.endsWith("s") ? " are" : " is").append(" winning").toString();
    }
}
