package com.aua.mechanics.converters;

import com.aua.mechanics.springs.Spring;

public class ConverterFloat extends Converter {
    public ConverterFloat(int numTimePoints, double startTime, double dt, double period) {
        super(numTimePoints, startTime, dt, period);
    }

    @Override
    public Spring convert(String bits) {
        var intAndFrac = bits.split(",");
        var integer = convertFromBinary(intAndFrac[0]);
        var fraction = convertFromBinary(intAndFrac[1]);
        var numDigitsInFraction = (int) (Math.log10(fraction) + 1);

        var stiffness = integer + (fraction / 10 * numDigitsInFraction);
        return new Spring(stiffness);
    }
}
