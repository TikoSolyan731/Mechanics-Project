package com.aua.mechanics.vector.position;

import com.aua.mechanics.vector.Vector;

public class Position extends Vector {
    public Position(double x, double y) {
        super(x, y);
    }

    public double distanceFrom(final Position p) {
        return Math.sqrt(Math.pow(getX() - p.getX(), 2) + Math.pow(getY() - p.getY(), 2));
    }

    public static Position randomInsideCircle(final double radius) {
        var x = Math.random() * radius * 2 - radius;

        var yUpper = Math.sqrt(Math.pow(radius, 2) - Math.pow(x, 2));
        var yLower = -1 * yUpper;
        var y = yLower + Math.random() * (yUpper - yLower);

        return new Position(x, y);
    }

    public static Position randomInsideStadium(final double semicircleRadius, final double edgeLength, final double leftSemiCentreX) {
        var xUpper = edgeLength + semicircleRadius + leftSemiCentreX * semicircleRadius;
        var xLower = -1 * semicircleRadius + leftSemiCentreX * semicircleRadius;
        var x = xLower + Math.random() * (xUpper - xLower);

        if (x >= leftSemiCentreX * semicircleRadius && x <= edgeLength + leftSemiCentreX * semicircleRadius) {
            var y = -1 * semicircleRadius + Math.random() * 2 * semicircleRadius;
            return new Position(x, y);
        }

        double yUpper;
        if (x < leftSemiCentreX * semicircleRadius) {
            yUpper = Math.sqrt(Math.pow(semicircleRadius, 2) - Math.pow(x - leftSemiCentreX, 2));
        } else {
            yUpper = Math.sqrt(Math.pow(semicircleRadius, 2) - Math.pow(x - leftSemiCentreX - edgeLength, 2));
        }
        var yLower = -1 * yUpper;
        var y = yLower + Math.random() * (yUpper - yLower);
        return new Position(x, y);
    }

    public static Position ofVector(final Vector v) {
        if (v == null) return null;
        return new Position(v.getX(), v.getY());
    }

    public static Position roundYCoord(final Position p) {
        return new Position(p.getX(), Math.round(p.getY()));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
