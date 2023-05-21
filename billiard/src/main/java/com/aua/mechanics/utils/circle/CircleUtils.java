package com.aua.mechanics.utils.circle;

import com.aua.mechanics.vector.Vector;

import java.util.function.Function;

public class CircleUtils {
    /*
        y = mx + b

        y^2 = rad^2 - x^2
        y = +-sqrt(rad^2 - x^2)

        mx + b = sqrt(rad^2 - (x - x0)^2)
        (mx + b)^2 = rad^2 - (x^2 - 2xx0 + x0^2)
        m^2*x^2 + 2mxb + b^2 + x^2 - 2xx0 + x0^2 - rad^2 = 0
        x^2(m^2 + 1) + x(2mb - 2x0) + b^2 + x0^2 - rad^2 = 0
        D = 4*m^2*b^2 - 8mbx0 + 4*x0^2 - 4*(m^2 + 1)(b^2 + x0^2 - rad^2)
        x1 = (-(2mb - 2x0) + sqrt(D)) / 2(m^2 + 1)
        x2 = (-(2mb - 2x0) - sqrt(D)) / 2(m^2 + 1)
    */
    public static Vector[] intersectWithLine(final double radius, final double x0, final double m, final double b) {
        var mSquared = Math.pow(m, 2);
        var bSquared = Math.pow(b, 2);
        var radSquared = Math.pow(radius, 2);
        var x0Squared = Math.pow(x0, 2);

        var det = 4 * mSquared * bSquared - 8 * m * b * x0 + 4 * x0Squared - 4 * (mSquared + 1) * (bSquared + x0Squared - radSquared);
        if (det < 0) return new Vector[]{};

        var x1 = (-1 * (2 * m * b - 2 * x0) + Math.sqrt(det)) / (2 * (mSquared + 1));
        var x2 = (-1 * (2 * m * b - 2 * x0) - Math.sqrt(det)) / (2 * (mSquared + 1));

        var y1 = m * x1 + b;
        var y2 = m * x2 + b;

        return new Vector[]{new Vector(x1, y1), new Vector(x2, y2)};
    }

    /*
        y = ax^2 + bx
        y = +-sqrt(rad^2 - x^2)

        ax^2 + bx = sqrt(rad^2 - x^2)
        a^2x^4 + 2abx^3 + b^2x^2 = rad^2 - x^2
        a^2x^4 + 2abx^3 + x^2(b^2 + 1) - rad^2 = 0
        Solve with Newton-Raphson method. Stopping condition |f(xn)| < eps
    */
    public static Vector intersectWithParabola(
            final double radius,
            final double[] parabolaConstants,
            final Vector momentum) {
        var quadConst = parabolaConstants[0];
        var linConst = parabolaConstants[1];
        var freeTerm = parabolaConstants[2];

        Function<Double, Double> f = (x) -> Math.pow(quadConst, 2) * Math.pow(x, 4) + 2 * quadConst * linConst * Math.pow(x, 3)
                + 2 * quadConst * freeTerm * Math.pow(x, 2) + Math.pow(linConst, 2) * Math.pow(x, 2) + 2 * linConst * freeTerm * x
                + Math.pow(freeTerm, 2) + Math.pow(x, 2) - Math.pow(radius, 2);
        Function<Double, Double> fPrime = (x) -> 4 * Math.pow(quadConst, 2) * Math.pow(x, 3) + 6 * quadConst * linConst * Math.pow(x, 2)
                + 4 * quadConst * freeTerm * x + 2 * Math.pow(linConst, 2) * x + 2 * linConst * freeTerm + 2 * x;
        final double eps = 0.001;

        double x = momentum.getX() < 0 ? -1 : 1;
        while (Math.abs(f.apply(x)) > eps) {
            x = x - (f.apply(x) / fPrime.apply(x));
        }
        var y = quadConst * Math.pow(x, 2) + linConst * x + freeTerm;

        return new Vector(x, y);
    }
}
