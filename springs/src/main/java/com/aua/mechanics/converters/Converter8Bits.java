package com.aua.mechanics.converters;

import com.aua.mechanics.springs.Spring;

public class Converter8Bits extends Converter {
    public Converter8Bits(int numTimePoints, double startTime, double dt, double period) {
        super(numTimePoints, startTime, dt, period);
    }

    @Override
    public Spring convert(String bits) {
        if (bits.length() != 8) {
            throw new IllegalArgumentException("There should be 8 bits");
        }

        var stiffness = convertFromBinary(bits);
        return new Spring(stiffness);
    }
}
