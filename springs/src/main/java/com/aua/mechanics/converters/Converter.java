package com.aua.mechanics.converters;

import com.aua.mechanics.fourier.FT;
import com.aua.mechanics.springs.Spring;

import java.util.List;

public abstract class Converter {
    protected int numTimePoints;
    protected double startTime;
    protected double dt;
    protected double period;

    public Converter(int numTimePoints, double startTime, double dt, double period) {
        this.numTimePoints = numTimePoints;
        this.startTime = startTime;
        this.dt = dt;
        this.period = period;
    }

    public abstract Spring convert(String bits);

    public double[] attachAndOscillate(Spring spring, double x0, double v0) {
        var t1 = startTime + numTimePoints * dt;
        return spring.move(startTime, t1, dt, x0, v0);
    }

    public double[] getFrequencyAmplitudes(double[] coords) {
        double[] timePoints = new double[numTimePoints];
        int i = 0;
        while (i < numTimePoints) {
            timePoints[i] = startTime + i * dt;
            i++;
        }

        return FT.transform(timePoints, coords, period);
    }

    protected int convertFromBinary(String bits) {
        int dec = 0, i = 0, rem;
        int n = Integer.parseInt(bits);

        while (n != 0) {
            rem = n % 10;
            n /= 10;
            dec += rem * Math.pow(2, i);
            ++i;
        }

        return dec;
    }

    public int getNumTimePoints() {
        return numTimePoints;
    }

    public void setNumTimePoints(int numTimePoints) {
        this.numTimePoints = numTimePoints;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }
}
