package com.aua.mechanics.utils.circle;

import com.aua.mechanics.vector.Vector;

import java.util.Arrays;
import java.util.List;

public class SemicircleUtils {
    public static Vector intersectRightSemiWithLine(
            final double radius,
            final double x0,
            final double m,
            final double b,
            final Vector mom) {
        var circleIntersections = CircleUtils.intersectWithLine(radius, x0, m, b);
        if (circleIntersections.length == 0) return null;

        System.out.println("circleIntersections=" + Arrays.toString(circleIntersections));
        if (mom.getY() > 0) {
            return circleIntersections[0].getY() > circleIntersections[1].getY() ? circleIntersections[0] : circleIntersections[1];
        } else if (mom.getY() < 0) {
            return circleIntersections[0].getY() < circleIntersections[1].getY() ? circleIntersections[0] : circleIntersections[1];
        }

        if (mom.getX() > 0) {
            return circleIntersections[0].getX() > circleIntersections[1].getX() ? circleIntersections[0] : circleIntersections[1];
        }
        return circleIntersections[0].getX() < circleIntersections[1].getX() ? circleIntersections[0] : circleIntersections[1];
    }

    public static Vector intersectLeftSemiWithLine(
            final double radius,
            final double x0,
            final double m,
            final double b,
            final Vector mom) {
        var circleIntersections = CircleUtils.intersectWithLine(radius, x0, m, b);
        if (circleIntersections.length == 0) return null;
        System.out.println("circleIntersections=" + Arrays.toString(circleIntersections));

        if (mom.getY() > 0) {
            return circleIntersections[0].getY() > circleIntersections[1].getY() ? circleIntersections[0] : circleIntersections[1];
        } else if (mom.getY() < 0) {
            return circleIntersections[0].getY() < circleIntersections[1].getY() ? circleIntersections[0] : circleIntersections[1];
        }

        if (mom.getX() > 0) {
            return circleIntersections[0].getX() > circleIntersections[1].getX() ? circleIntersections[0] : circleIntersections[1];
        }
        return circleIntersections[0].getX() < circleIntersections[1].getX() ? circleIntersections[0] : circleIntersections[1];
    }

    private static double determineY(final List<Double> possible, final Vector momentum) {
        double y;
        if (possible.size() == 1) {
            y = possible.get(0);
        } else {
            y = momentum.getY() > 0
                    ? Math.max(possible.get(0), possible.get(1))
                    : Math.min(possible.get(0), possible.get(1));
        }
        return y;
    }
}
