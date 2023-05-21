package com.aua.mechanics.systems;

import com.aua.mechanics.utils.circle.SemicircleUtils;
import com.aua.mechanics.utils.line.LineUtils;
import com.aua.mechanics.vector.Vector;
import com.aua.mechanics.vector.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BallInsideStadiumSystem {
    private Position currentPosition;
    private Vector currentMomentum;
    private final double circleRadius;
    private final double edgeLength;
    private final double momentumMagnitude;
    private final double leftSemiCentreX;

    public BallInsideStadiumSystem(double circleRadius, double edgeLength, double momentumMagnitude, double leftSemiCentreX) {
        this.circleRadius = circleRadius;
        this.edgeLength = edgeLength;
        this.momentumMagnitude = momentumMagnitude;
        this.leftSemiCentreX = leftSemiCentreX;
        this.currentPosition = Position.randomInsideStadium(circleRadius, edgeLength, leftSemiCentreX);
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

    private Vector calculateMomentumAfterCollision(final Vector momentum, final Position position) {
        if (position.getY() == circleRadius || position.getY() == -1 * circleRadius) {
            return new Vector(momentum.getX(), -1 * momentum.getY());
        }

        var x0 = position.getX() < 0 ? -1 * edgeLength / 2 : edgeLength / 2;
        var px = (Math.pow(position.getY(), 2) - Math.pow(position.getX() - x0, 2)) * momentum.getX() - 2 * (position.getX() - x0) * position.getY() * momentum.getY();
        var py = -2 * (position.getX() - x0) * position.getY() * momentum.getX() + (Math.pow(position.getX() - x0, 2) - Math.pow(position.getY(), 2)) * momentum.getY();
        return new Vector(px, py);
    }

    private Position findNextCollision(final Position pos, final Vector momentum) {
        Position nextPos = Position.ofVector(pos.add(momentum));
        var lineSlope = LineUtils.getSlope(pos, nextPos);
        var lineIntersectionPoint = LineUtils.getIntersection(pos, lineSlope);

        System.out.println("lineSlope=" + lineSlope);
        System.out.println("lineIntersectionPoint=" + lineIntersectionPoint);
        var upperLineIntersection =
                findIntersectionWithUpperLineSegment(lineSlope, lineIntersectionPoint);
        var lowerLineIntersection =
                findIntersectionWithLowerLineSegment(lineSlope, lineIntersectionPoint);
        var leftSemiIntersection =
                Position.ofVector(SemicircleUtils.intersectLeftSemiWithLine(circleRadius, leftSemiCentreX, lineSlope, lineIntersectionPoint, momentum));
        var rightSemiIntersection =
                Position.ofVector(SemicircleUtils.intersectRightSemiWithLine(circleRadius, leftSemiCentreX + edgeLength, lineSlope, lineIntersectionPoint, momentum));

        if (momentum.getY() > 0) {
            if (upperLineIntersection != null) return Position.roundYCoord(upperLineIntersection);
            if (leftSemiIntersection != null && momentum.getX() < 0) return leftSemiIntersection;
            return rightSemiIntersection;
        } else if (momentum.getY() < 0) {
            if (lowerLineIntersection != null) return Position.roundYCoord(lowerLineIntersection);
            if (leftSemiIntersection != null && momentum.getX() < 0) return leftSemiIntersection;
            return rightSemiIntersection;
        }

        if (momentum.getX() > 0) return rightSemiIntersection;
        return leftSemiIntersection;
    }

    private Position findIntersectionWithUpperLineSegment(
            final double m,
            final double b) {
        var intersection = LineUtils.intersect(m, b, 0, circleRadius);
        System.out.println("upper line intersection=" + intersection);
        if (intersection.getX() >= leftSemiCentreX * circleRadius && intersection.getX() <= edgeLength + leftSemiCentreX * circleRadius) {
            System.out.println("VALID");
            return Position.ofVector(intersection);
        }
        return null;
    }

    private Position findIntersectionWithLowerLineSegment(
            final double m,
            final double b) {
        var intersection = LineUtils.intersect(m, b, 0, -1 * circleRadius);
        System.out.println("lower line intersection=" + intersection);
        if (intersection.getX() >= leftSemiCentreX * circleRadius && intersection.getX() <= edgeLength + leftSemiCentreX * circleRadius) {
            System.out.println("VALID");
            return Position.ofVector(intersection);
        }
        return null;
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

    public double getEdgeLength() {
        return edgeLength;
    }

    public double getMomentumMagnitude() {
        return momentumMagnitude;
    }

    public double getLeftSemiCentreX() {
        return leftSemiCentreX;
    }
}
