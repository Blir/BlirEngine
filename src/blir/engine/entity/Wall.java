package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;
import java.awt.Color;

import java.util.List;

/**
 *
 * @author Blir
 */
public class Wall extends MortalEntityType {

    public Wall(int id) {
        super(id, "Wall", Color.ORANGE, 150);
    }

    @Override
    public void init() {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onSpawnTick(List<Location> entityLocations, Game game) {
    }
    
}
