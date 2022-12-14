package com.hotmail.nickcooke.aoc22;

import java.util.*;

public class Day9 extends AoCSolution {

    public static void main(String[] args) {
        Day9 day9 = new Day9();
        day9.getInput();
        day9.part1Timed();
        day9.part2Timed();
    }

    @Override
    void part1() {
        int tailPositionCount = calculateTailPositions(2);
        System.out.println("Part 1: " + tailPositionCount);
    }

    @Override
    void part2() {
        int tailPositionCount = calculateTailPositions(10);
        System.out.println("Part 2: " + tailPositionCount);
    }

    private int calculateTailPositions(int numberOfKnots) {
        List<Knot> rope = createRope(numberOfKnots);
        Knot head = rope.get(0);
        Knot tail = rope.get(rope.size() - 1);
        for (String line : inputLines) {
            processMove(line, head);
        }
        return tail.visitedPositions.size();
    }

    private List<Knot> createRope(int numberOfKnots) {
        List<Knot> rope = new ArrayList<>();
        for (int i = 0; i < numberOfKnots; i++) {
            if (rope.isEmpty()) {
                rope.add(new Knot());
            } else {
                rope.add(new Knot(rope.get(rope.size() - 1)));
            }
        }
        return rope;
    }

    private void processMove(String line, Knot head) {
        String move = line.split(" ")[0];
        int steps = Integer.parseInt(line.split(" ")[1]);
        for (int stepCount = 0; stepCount < steps; stepCount++) {
            switch (move) {
                case "U" -> head.moveUp();
                case "R" -> head.moveRight();
                case "L" -> head.moveLeft();
                case "D" -> head.moveDown();
                default -> throw new RuntimeException("Unexpected Move");
            }
        }
    }


    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isAdjacent(Position other) {
            return this.x <= other.x + 1 && this.x >= other.x - 1 && this.y <= other.y + 1 && this.y >= other.y - 1 && !this.equals(other);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    class Knot {
        private Position position;
        private Knot previousKnot;
        private Knot followingKnot;

        Set<Position> visitedPositions = new HashSet<>();

        public Knot() {
            this(new Position(0, 0));
        }

        public Knot(Position position) {
            this.position = position;
            visitedPositions.add(position);
        }

        public Knot(Knot previous) {
            this();
            this.previousKnot = previous;
            previous.followingKnot = this;
        }

        public boolean shouldMove() {
            return !(position.isAdjacent(previousKnot.position) || position.equals(previousKnot.getPosition()));
        }

        public void moveTowards(Knot otherKnot) {
            if (position.x == otherKnot.getPosition().x + 2 && position.y == otherKnot.getPosition().y) {
                moveLeft();
            } else if (position.x == otherKnot.getPosition().x + 2 && position.y < otherKnot.getPosition().y) {
                moveUpAndLeft();
            } else if (position.x == otherKnot.getPosition().x + 2 && position.y > otherKnot.getPosition().y) {
                moveDownAndLeft();
            } else if (position.x == otherKnot.getPosition().x - 2 && position.y == otherKnot.getPosition().y) {
                moveRight();
            } else if (position.x == otherKnot.getPosition().x - 2 && position.y < otherKnot.getPosition().y) {
                moveUpAndRight();
            } else if (position.x == otherKnot.getPosition().x - 2 && position.y > otherKnot.getPosition().y) {
                moveDownAndRight();
            } else if (position.y == otherKnot.getPosition().y + 2 && position.x == otherKnot.getPosition().x) {
                moveDown();
            } else if (position.y == otherKnot.getPosition().y + 2 && position.x < otherKnot.getPosition().x) {
                moveDownAndRight();
            } else if (position.y == otherKnot.getPosition().y + 2 && position.x > otherKnot.getPosition().x) {
                moveDownAndLeft();
            } else if (position.y == otherKnot.getPosition().y - 2 && position.x == otherKnot.getPosition().x) {
                moveUp();
            } else if (position.y == otherKnot.getPosition().y - 2 && position.x < otherKnot.getPosition().x) {
                moveUpAndRight();
            } else if (position.y == otherKnot.getPosition().y - 2 && position.x > otherKnot.getPosition().x) {
                moveUpAndLeft();
            }
            visitedPositions.add(position);
        }

        private boolean isTail() {
            return null == followingKnot;
        }

        private void moveFollowingKnot() {
            if (!isTail() && followingKnot.shouldMove()) {
                followingKnot.moveTowards(this);
            }
        }

        public void moveUp() {
            position = new Position(position.x, position.y + 1);
            moveFollowingKnot();
        }

        public void moveRight() {
            position = new Position(position.x + 1, position.y);
            moveFollowingKnot();
        }

        public void moveDown() {
            position = new Position(position.x, position.y - 1);
            moveFollowingKnot();
        }

        public void moveLeft() {
            position = new Position(position.x - 1, position.y);
            moveFollowingKnot();
        }

        public void moveUpAndLeft() {
            position = new Position(position.x - 1, position.y + 1);
            moveFollowingKnot();
        }

        public void moveUpAndRight() {
            position = new Position(position.x + 1, position.y + 1);
            moveFollowingKnot();
        }

        public void moveDownAndLeft() {
            position = new Position(position.x - 1, position.y - 1);
            moveFollowingKnot();
        }

        public void moveDownAndRight() {
            position = new Position(position.x + 1, position.y - 1);
            moveFollowingKnot();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Knot knot = (Knot) o;
            return Objects.equals(position, knot.getPosition());
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }

        public Position getPosition() {
            return position;
        }
    }
}

