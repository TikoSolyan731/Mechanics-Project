package com.aua.mechanics;

import com.aua.mechanics.systems.BallInsideStadiumSystem;

import java.util.Arrays;

public class Task3Solver {
    public static void main(String[] args) {
        var edgeLength = 100;
        var system = new BallInsideStadiumSystem(1, edgeLength, 1, 0);
        System.out.println("pos=" + system.getCurrentPosition());
        System.out.println("mom=" + system.getCurrentMomentum());

        var collisions = system.calculateCollisionsAndReturn(100);
        var numbers = collisions
                .stream()
                .filter(position -> position.getY() == system.getCircleRadius() || position.getY() == -1 * system.getCircleRadius())
                .map(position -> position.getX() / edgeLength)
                .toList();

        int M = 50;
        int[] binCounts = new int[M];
        numbers.forEach(num -> {
            var index = (int) Math.floor(num * M);
            binCounts[index] = binCounts[index] + 1;
        });
        System.out.println("numbers=" + numbers);
        System.out.println("binCounts=" + Arrays.toString(binCounts));
    }
}
