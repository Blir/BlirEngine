package blir.engine.util;

import static blir.engine.BlirEngine.rng;

/**
 *
 * @author Blir
 */
public class Location {

    public static Location wander(int x, int y, int dist) {
        int xDist = rng.nextInt(dist + 1);
        int yDist = rng.nextInt(dist + 1);
        return new Location(x + rng.nextInt(2 + xDist) - xDist, y + rng.nextInt(2 + yDist) - yDist);
    }

    public static Location idleWander(int x, int y, int dist, int rate) {
        if (rng.nextInt(100) > rate - 1) {
            int xDist = rng.nextInt(dist + 1);
            int yDist = rng.nextInt(dist + 1);
            return new Location(x + rng.nextInt(2 + xDist) - xDist, y + rng.nextInt(2 + yDist) - yDist);
        }
        return null;
    }

    public final int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
