package com.aua.mechanics.utils.line;

import com.aua.mechanics.vector.Vector;
import com.aua.mechanics.vector.position.Position;

/*
    y = mx + b
    b = y - mx
 */
public class LineUtils {
    public static double getSlope(final Position p1, final Position p2) {
        var dy = p1.getY() - p2.getY();
        var dx = p1.getX() - p2.getX();

        return dy / dx;
    }

    public static double getIntersection(final Position p, final double slope) {
        return p.getY() - slope * p.getX();
    }

    public static Vector intersect(final double m1, final double b1, final double m2, final double b2) {
        var x = (b2 - b1) / (m1 - m2);
        var y = m1 * x + b1;

        return new Vector(x, y);
    }
}
