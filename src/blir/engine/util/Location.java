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

        int x, y;

        if (startX == endX) {
            x = startX;
        } else {
            x = startX + (endX - startX > 0 ? 1 : -1)
                         * Math.min(Math.abs(endX - startX) - 1, dist);
        }

        if (startY == endY) {
            y = startY;
        } else {
            y = startY + (endY - startY > 0 ? 1 : -1)
                         * Math.min(Math.abs(endY - startY) - 1, dist);
        }

        return new Location(x, y);
    }

    public static Location away(int startX, int startY, int endX, int endY,
                                int dist) {

        return new Location(startX + (endX - startX > 0 ? -1 : 1)
                                     * Math.min(Math.abs(endX - startX) - 1, dist),
                            startY + (endY - startY > 0 ? -1 : 1)
                                     * Math.min(Math.abs(endY - startY) - 1, dist));
    }

    public static Location towards(Location from, Location to, int dist) {

        int x, y;

        if (from.x == to.x) {
            x = from.x;
        } else {
            x = from.x + (to.x - from.x > 0 ? 1 : -1)
                         * Math.min(Math.abs(to.x - from.x) - 1, dist);
        }

        if (from.y == to.y) {
            y = from.y;
        } else {
            y = from.y + (to.y - from.y > 0 ? 1 : -1)
                         * Math.min(Math.abs(to.y - from.y) - 1, dist);
        }

        return new Location(x, y);
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
