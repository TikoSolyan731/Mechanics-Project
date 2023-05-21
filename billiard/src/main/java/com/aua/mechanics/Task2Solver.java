package com.aua.mechanics;

import com.aua.mechanics.systems.BallInsideCircleWithGravitySystem;

public class Task2Solver {
    public static void main(String[] args) {
        var system = new BallInsideCircleWithGravitySystem(1, 5, 10);
        System.out.println("initial pos=" + system.getCurrentPosition());
        System.out.println("initial momentum=" + system.getCurrentMomentum());

        double delta = 0.1;
        int n = 10;
        for (int i = 2; i < n; i++) {
            System.out.println("------Starting Test for n=" + i + "-------");
            var positions = system.calculateCollisionsAndReturn(i);
            system.reverseCurrentMomentum();
            var reversePositions = system.calculateCollisionsAndReturn(i);

            int equalPositionsCounter = 0;
            int deltaDeviationPositionsCounter = 0;
            int largerThanDeltaDeviationPositionsCounter = 0;
            for (int j = 0; j < reversePositions.size(); j++) {
                var reversePos = reversePositions.get(j);
                var pos = positions.get(i - j - 1);

                if (reversePos == pos) equalPositionsCounter++;
                else if (pos.distanceFrom(reversePos) < delta) deltaDeviationPositionsCounter++;
                else largerThanDeltaDeviationPositionsCounter++;
            }

            System.out.println("equalPositionsCounter = " + equalPositionsCounter);
            System.out.println("deltaDeviationPositionsCounter = " + deltaDeviationPositionsCounter);
            System.out.println("largerThanDeltaDeviationPositionsCounter = " + largerThanDeltaDeviationPositionsCounter);

            System.out.println("-----End of Test for n=" + i + "------");
            system.reset();
        }
    }
}
