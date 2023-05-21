package com.aua.mechanics.systems;

import com.aua.mechanics.utils.circle.CircleUtils;
import com.aua.mechanics.utils.parabola.ParabolaUtils;
import com.aua.mechanics.vector.Vector;
import com.aua.mechanics.vector.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BallInsideCircleWithGravitySystem {
    private Position currentPosition;
    private Vector currentMomentum;
    private final double circleRadius;
    private final double momentumMagnitudeMin;
    private final double momentumMagnitudeMax;

    private static final double GRAVITY = 10;

    public BallInsideCircleWithGravitySystem(
            final double circleRadius,
            final double momentumMagnitudeMin,
            final double momentumMagnitudeMax) {
        this.circleRadius = circleRadius;
        this.momentumMagnitudeMin = momentumMagnitudeMin;
        this.momentumMagnitudeMax = momentumMagnitudeMax;
        this.currentPosition = Position.randomInsideCircle(circleRadius);
        this.currentMomentum = Vector.randomWithMagnitudeIn(momentumMagnitudeMax, momentumMagnitudeMax);
    }

    public List<Position> calculateCollisionsAndReturn(final int n) {
        List<Position> positions = new ArrayList<>();
        positions.add(findNextCollision(currentPosition, currentMomentum));
        currentPosition = positions.get(0);
        currentMomentum = calculateMomentumAfterCollision(currentMomentum, currentPosition);
        for (int i = 1; i < n; i++) {
            var collision = findNextCollision(positions.get(i - 1), currentMomentum);
            currentPosition = collision;
            positions.add(collision);

            currentMomentum = calculateMomentumAfterCollision(currentMomentum, collision);
        }

        return positions;
    }

    public void reverseCurrentMomentum() {
        currentMomentum = new Vector(-1 * currentMomentum.getX(), -1 * currentMomentum.getY());
    }

    public void reset() {
        this.currentPosition = Position.randomInsideCircle(circleRadius);
        this.currentMomentum = Vector.randomWithMagnitudeIn(momentumMagnitudeMax, momentumMagnitudeMax);
    }

    private Vector calculateMomentumAfterCollision(final Vector momentum, final Position collisionPoint) {
        var xSquared = Math.pow(collisionPoint.getX(), 2);
        var ySquared = Math.pow(collisionPoint.getY(), 2);

        var pX = (ySquared - xSquared) * momentum.getX() - 2 * collisionPoint.getX() * collisionPoint.getY() * momentum.getY();
        var pY = -2 * collisionPoint.getX() * collisionPoint.getY() * momentum.getX() + (xSquared - ySquared) * momentum.getY();

        return new Vector(pX, pY);
    }

    private Position findNextCollision(final Position pos, final Vector momentum) {
        var launchAngle = calculateLaunchAngle(momentum);
        var vZero = vZero(momentum, launchAngle);
        var parabolaConstants = ParabolaUtils.getQuadraticConstants(launchAngle, vZero, GRAVITY, pos);

        return Position.ofVector(CircleUtils.intersectWithParabola(circleRadius, parabolaConstants, momentum));
    }

    private static double vZero(final Vector momentum, final double launchAngle) {
        return momentum.getX() / Math.cos(launchAngle);
    }

    private static double calculateLaunchAngle(final Vector momentum) {
        if (momentum.getX() == 0) {
            if (momentum.getY() > 0) return Math.PI / 2;
            else return -1 * Math.PI / 2;
        }

        var tanTheta = momentum.getY() / momentum.getX();
        return Math.atan(tanTheta);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Vector getCurrentMomentum() {
        return currentMomentum;
    }

    public double getCircleRadius() {
        return circleRadius;
    }

    public double getMomentumMagnitudeMin() {
        return momentumMagnitudeMin;
    }

    public double getMomentumMagnitudeMax() {
        return momentumMagnitudeMax;
    }
}
