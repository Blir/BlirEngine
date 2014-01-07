package blir.engine.game;

import java.util.LinkedList;

/**
 *
 * @author Blir
 */
public class Alliance extends LinkedList<Team> {
    
    public final String name;
    
    public Alliance(String name) {
        this.name = name;
    }
    
    public int getScore() {
        int score = 0;
        for (Team team : this) {
            score += team.getScore();
        }
        return score;
    }
}
