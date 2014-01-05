package blir.engine.game;

import blir.engine.entity.Team;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Blir
 */
public class Scoreboard {

    List<Team> teams = new LinkedList<>();

    public void addTeam(Team team) {
        teams.add(team);
    }

    public String toString(String name) {
        StringBuilder string = new StringBuilder(name).append(" ");
        Team no1 = null;
        for (Team team : teams) {
            if (no1 == null || no1.getScore() < team.getScore()) {
                no1 = team;
            }
            string.append(team.getTeamName()).append(": ").append(team.getScore()).append(", ");
        }
        return no1 == null ? "" : string.append(no1.getTeamName()).append(" is winning").toString();
    }
}
