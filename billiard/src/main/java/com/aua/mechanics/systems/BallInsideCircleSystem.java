package com.aua.mechanics.systems;

import com.aua.mechanics.utils.circle.CircleUtils;
import com.aua.mechanics.utils.line.LineUtils;
import com.aua.mechanics.vector.Vector;
import com.aua.mechanics.vector.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BallInsideCircleSystem {
    private Position currentPosition;
    private Vector currentMomentum;
    private final double circleRadius;
    private final double momentumMagnitude;

    public BallInsideCircleSystem(final double circleRadius, final double momentumMagnitude) {
        this.circleRadius = circleRadius;
        this.momentumMagnitude = momentumMagnitude;
        this.currentPosition = Position.randomInsideCircle(circleRadius);
        this.currentMomentum = Vector.randomWithMagnitude(momentumMagnitude);
    }

    public List<Position> calculateCollisionsAndReturn(final int n) {
        List<Position> positions = new ArrayList<>();
        positions.add(findNextCollision(currentPosition, currentMomentum));

        currentMomentum = calculateMomentumAfterCollision(currentMomentum, positions.get(0));
        for (int i = 1; i < n; i++) {
            var collision = findNextCollision(positions.get(i - 1), currentMomentum);
            currentPosition = collision;
            positions.add(collision);

            currentMomentum = calculateMomentumAfterCollision(currentMomentum, collision);
        }

        return positions;
    }

    public void reset() {
        this.currentPosition = Position.randomInsideCircle(circleRadius);
        this.currentMomentum = Vector.randomWithMagnitude(momentumMagnitude);
    }

    public void reverseCurrentMomentum() {
        this.currentMomentum = new Vector(currentMomentum.getX() * -1, currentMomentum.getY() * -1);
    }

    private Position findNextCollision(final Position pos, final Vector momentum) {
        Position nextPos = Position.ofVector(pos.add(momentum));
        var lineSlope = LineUtils.getSlope(pos, nextPos);
        var lineIntersectionPoint = LineUtils.getIntersection(pos, lineSlope);

        var circleIntersections = CircleUtils.intersectWithLine(circleRadius, 0, lineSlope, lineIntersectionPoint);
        return determineCollisionPoint(circleIntersections, momentum);
    }

    private Position determineCollisionPoint(final Vector[] possiblePoints, final Vector momentum) {
        var p1 = possiblePoints[0];
        var p2 = possiblePoints[1];

        Vector nextCollision;
        if (p1.getY() - p2.getY() != 0) {
            if (momentum.getY() < 0) {
                nextCollision = p1.getY() < p2.getY() ? p1 : p2;
            } else {
                nextCollision = p1.getY() > p2.getY() ? p1 : p2;
            }
        } else {
            if (momentum.getX() < 0) {
                nextCollision = p1.getX() < p2.getX() ? p1 : p2;
            } else {
                nextCollision = p1.getX() > p2.getX() ? p1 : p2;
            }
        }
        return Position.ofVector(nextCollision);
    }

    private Vector calculateMomentumAfterCollision(final Vector momentum, final Position collisionPoint) {
        var xSquared = Math.pow(collisionPoint.getX(), 2);
        var ySquared = Math.pow(collisionPoint.getY(), 2);

        var pX = (ySquared - xSquared) * momentum.getX() - 2 * collisionPoint.getX() * collisionPoint.getY() * momentum.getY();
        var pY = -2 * collisionPoint.getX() * collisionPoint.getY() * momentum.getX() + (xSquared - ySquared) * momentum.getY();

        return new Vector(pX, pY);
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

    public double getMomentumMagnitude() {
        return momentumMagnitude;
    }
}
