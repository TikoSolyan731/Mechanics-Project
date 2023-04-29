package com.aua.mechanics.fourier;

public class FT {
    public static double[] transform(double[] timePoints, double[] values, double period) {
        var dt = timePoints[0] - timePoints[1];
        double baseOmega = Math.PI * 2 / period;
        double[] results = new double[timePoints.length];

        for (int i = 0; i < timePoints.length; i++) {
            var omegaK = baseOmega * (i + 1);
            var f = values[0] / 2;
            for (int j = 0; j < values.length; j++) {
                var sineCoef = computeSineCoefficient(timePoints[0], dt, values, omegaK, period);
                f += sineCoef * Math.sin(baseOmega * (i + 1) * timePoints[i]);
            }
            for (int j = 0; j < values.length; j++) {
                var cosCoef = computeCosineCoefficient(timePoints[0], dt, values, omegaK, period);
                f += cosCoef * Math.sin(baseOmega * (i + 1) * timePoints[i]);
            }

            results[i] = f;
        }

        return results;
    }

    private static double computeSineCoefficient(double t1, double dt, double[] x, double omegaK, double period) {
        double sum = 0;
        double ti = t1;
        for (double v : x) {
            sum += v * Math.sin(ti * omegaK);
            ti += dt;
        }

        return sum * dt / period;
    }

    private static double computeCosineCoefficient(double t1, double dt, double[] x, double omegaK, double period) {
        double sum = 0;
        double ti = t1;
        for (double v : x) {
            sum += v * Math.cos(ti * omegaK);
            ti += dt;
        }

        return sum * dt / period;
    }
}
