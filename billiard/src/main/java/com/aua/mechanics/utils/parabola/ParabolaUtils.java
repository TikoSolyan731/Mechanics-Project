package com.aua.mechanics.utils.parabola;

import com.aua.mechanics.vector.position.Position;

public class ParabolaUtils {
    public static double[] getQuadraticConstants(
            final double launchAngle,
            final double v0,
            final double gravity,
            final Position pos) {
        var a = -1 * gravity / (2 * Math.pow(v0, 2) * Math.pow(Math.cos(launchAngle), 2));
        var b = Math.tan(launchAngle);

        double[] constants = new double[3];
        constants[0] = a;
        constants[1] = b - 2 * a * pos.getX();
        constants[2] = a * Math.pow(pos.getX(), 2) - b * pos.getX() + pos.getY();

        return constants;
    }
}
