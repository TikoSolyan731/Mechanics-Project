package com.aua.mechanics.springs;

public class Spring {
    private double stiffness;

    public Spring() {
        this(1);
    }

    public Spring(double stiffness) {
        this.stiffness = stiffness;
    }

    public double[] move(double t, double dt, double x0) {
        return this.move(0, t, dt, x0, 0);
    }

    public double[] move(double t, double dt, double x0, double v0) {
        return this.move(0, t, dt, x0, v0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        return this.move(t0, t1, dt, x0, v0, 1);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        var omega = getAngularFrequency(this.stiffness, m);
        var A = v0 / omega;
        var B = x0;

        double[] positions = new double[(int)((t1 - t0) / dt)];
        int i = 0;
        while (t0 < t1) {
            positions[i] = getPosition(t0, omega, A, B);

            t0 += dt;
            i++;
        }
        return positions;
    }

    Spring inSeries(Spring that) {
        return new Spring(1 / ((1 / this.getStiffness()) + (1 / that.getStiffness())));
    }

    Spring inParallel(Spring that) {
        return new Spring(this.getStiffness() + that.getStiffness());
    }

    private static double getAngularFrequency(double stiffness, double mass) {
        return Math.sqrt(stiffness / mass);
    }

    private static double getPosition(double t, double omega, double A, double B) {
        return (A * Math.sin(omega * t)) + (B * Math.cos(omega * t));
    }

    public double getStiffness() {
        return stiffness;
    }

    private void setStiffness(double stiffness) {
        this.stiffness = stiffness;
    }
}
