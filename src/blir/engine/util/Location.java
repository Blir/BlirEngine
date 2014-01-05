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

    public static Location towards(int startX, int startY, int endX, int endY,
                                   int dist) {

        return new Location(startX + (startX - endX > 0 ? -dist : dist),
                            startY + (startY - endY > 0 ? -dist : dist));
    }
    
    public static Location towards(Location from, Location to, int dist) {
        return new Location(from.x + (from.x - to.x > 0 ? -dist : dist),
                            from.y + (from.y - to.y > 0 ? -dist : dist));
    }

    public final int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 31 * x;
        hash += 31 * y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
