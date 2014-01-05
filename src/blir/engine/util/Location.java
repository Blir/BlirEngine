package blir.engine.util;

import static blir.engine.BlirEngine.rng;

/**
 *
 * @author Blir
 */
public class Location {

    public static Location wander(int x, int y, int dist) {
        return new Location((x - dist) + rng.nextInt(1 + 2 * dist), (y - dist) + rng.nextInt(1 + 2 * dist));
    }

    public static Location idleWander(int x, int y, int dist, int rate) {
        if (rng.nextInt(100) > rate - 1) {
            return wander(x, y, dist);
        }
        return null;
    }

    public final int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
