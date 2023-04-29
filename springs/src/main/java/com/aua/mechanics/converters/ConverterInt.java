package com.aua.mechanics.converters;

import com.aua.mechanics.springs.Spring;

public class ConverterInt extends Converter {
    public ConverterInt(int numTimePoints, double startTime, double dt, double period) {
        super(numTimePoints, startTime, dt, period);
    }

    @Override
    public Spring convert(String bits) {
        var stiffness = convertFromBinary(bits);
        return new Spring(stiffness);
    }
}
