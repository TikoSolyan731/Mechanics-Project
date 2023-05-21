package com.aua.mechanics.vector;

import java.util.Objects;

public class Vector {
    protected final double x;
    protected final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Vector randomWithMagnitude(final double magnitude) {
        var x = Math.random() * magnitude * 2 - magnitude;
        var ySquared = Math.pow(magnitude, 2) - Math.pow(x, 2);

        double y;
        if (Math.random() < 0.5) {
            y = -1 * Math.sqrt(ySquared);
        } else {
            y = Math.sqrt(ySquared);
        }

        return new Vector(x, y);
    }

    /*
        min <= sqrt(x^2 + y^2) <= max
        min^2 <= x^2 + y^2 <= max^2
    */
    public static Vector randomWithMagnitudeIn(final double min, final double max) {
        var x = Math.random() * max * 2 - max;
        var yUpper = Math.sqrt(Math.pow(max, 2) - Math.pow(x, 2));
        var yLower = -1 * yUpper;

        if (Math.pow(x, 2) >= Math.pow(min, 2)) {
            var y = yLower + Math.random() * (yUpper - yLower);
            return new Vector(x, y);
        }

        var yUpperMin = Math.sqrt(Math.pow(min, 2) - Math.pow(x, 2));
        var yLowerMax = -1 * yUpperMin;
        if (Math.random() < 0.5) {
            var y = yUpperMin + Math.random() * (yUpper - yUpperMin);
            return new Vector(x, y);
        }
        var y = yLower + Math.random() * (yLowerMax - yLower);
        return new Vector(x, y);
    }

    public Vector add(Vector other) {
        return new Vector(this.getX() + other.getX(), this.getY() + other.getY());
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
