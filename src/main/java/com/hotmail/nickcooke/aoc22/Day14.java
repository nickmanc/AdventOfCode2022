package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day14 extends AoCSolution {

    public static void main(String[] args) {
        Day14 day14 = new Day14();
        day14.getInput();
        day14.part1Timed();
        day14.part2Timed();
    }

    @Override
    void part1() {
        char[][] cave = parseInput();
        int sandUnitCount = simulateSand(cave);
        System.out.println("Part 1: " + sandUnitCount);
    }

    @Override
    void part2() {
        char[][] cave = parseInput();
        char[][] biggerCave = createBiggerCave(cave);
        int sandUnitCount = simulateSand(biggerCave);
        System.out.println("Part 2: " + (sandUnitCount));
    }

    private char[][] createBiggerCave(char[][] cave) {
        char[][] biggerCave = new char[cave.length][cave[0].length + 2];
        for (int i = 0; i < cave.length; i++) {
            System.arraycopy(cave[i], 0, biggerCave[i], 0, cave[0].length);
        }
        for (int j = 0; j < cave.length; j++) {
            biggerCave[j][biggerCave[0].length - 2] = '.';
            biggerCave[j][biggerCave[0].length - 1] = '#';
        }
        return biggerCave;
    }

    private int simulateSand(char[][] cave) {
        Point start = new Point(500, 0);
        SandUnit sandUnit;
        int sandUnitCount = 0;
        try {
            while (true) {
                sandUnit = new SandUnit(start);
                sandUnitCount++;
                sandUnit.drop(cave);
            }
        } catch (FreeFlowingSandException ffse) {
            sandUnitCount--; //we have one too many as it's started free flowing
            return sandUnitCount;
        } catch (BlockedSandException bse) {
            return sandUnitCount;
        }
    }

    private char[][] parseInput() {
        int maxY = 0;
        List<RockPath> paths = new ArrayList<>();
        for (String line : inputLines) {
            RockPath rockPath = new RockPath();
            for (String pathDefinition : line.split(" -> ")) {
                int x = Integer.parseInt(pathDefinition.split(",")[0]);
                int y = Integer.parseInt(pathDefinition.split(",")[1]);
                maxY = (Math.max(maxY, y));
                rockPath.add(new Point(x, y));
            }
            paths.add(rockPath);
        }
        char[][] cave = new char[1000][maxY + 1];
        for (char[] row : cave) {
            Arrays.fill(row, '.');
        }
        for (RockPath rockPath : paths) {
            for (int i = 0; i < rockPath.path.size() - 1; i++) {
                Point from = rockPath.path.get(i);
                Point to = rockPath.path.get(i + 1);
                for (int x = Math.min(from.x, to.x); x <= Math.max(from.x, to.x); x++) {
                    for (int y = Math.min(from.y, to.y); y <= Math.max(from.y, to.y); y++) {
                        cave[x][y] = '#';
                    }
                }
            }
        }
        return cave;
    }

    private void printCave(char[][] cave) {
        for (int i = 0; i < cave[0].length; i++) {
            for (char[] chars : cave) {
                System.out.print(chars[i]);
            }
            System.out.println();
        }
    }

    static class SandUnit {
        Point currentPoint;
        public SandUnit(Point currentPoint) {
            this.currentPoint = currentPoint;
        }

        public void drop(char[][] cave) throws FreeFlowingSandException, BlockedSandException {
            boolean notBlocked = true;
            while (notBlocked) {
                if (voidBelow(cave)) {
                    throw new FreeFlowingSandException();
                } else if (isEmptyBelow(cave)) {
                    moveDown(cave);
                } else if (isEmptyBelowLeft(cave)) {
                    moveDownLeft(cave);
                } else if (isEmptyBelowRight(cave)) {
                    moveDownRight(cave);
                } else {
                    notBlocked = false;
                    if (currentPoint.x == 500 && currentPoint.y == 0) {
                        throw new BlockedSandException();
                    }
                }
            }
        }

        private boolean voidBelow(char[][] cave) {
            for (int y = currentPoint.y + 1; y < cave[0].length; y++) {
                if (cave[currentPoint.x][y] == '#' || cave[currentPoint.x][y] == 'o') {
                    return false;
                }
            }
            return true;
        }

        private boolean isEmptyBelow(char[][] cave) {
            char spaceBelow = cave[currentPoint.x][currentPoint.y + 1];
            return spaceBelow != '#' && spaceBelow != 'o';
        }

        private void moveDown(char[][] cave) {
            cave[currentPoint.x][currentPoint.y] = '.';
            currentPoint = new Point(currentPoint.x, currentPoint.y + 1);
            cave[currentPoint.x][currentPoint.y] = 'o';
        }

        private boolean isEmptyBelowRight(char[][] cave) {
            char spaceBelowRight = cave[currentPoint.x + 1][currentPoint.y + 1];
            return spaceBelowRight != '#' && spaceBelowRight != 'o';
        }

        private void moveDownRight(char[][] cave) {
            cave[currentPoint.x][currentPoint.y] = '.';
            currentPoint = new Point(currentPoint.x + 1, currentPoint.y + 1);
            cave[currentPoint.x][currentPoint.y] = 'o';
        }

        private boolean isEmptyBelowLeft(char[][] cave) {
            char spaceBelowLeft = cave[currentPoint.x - 1][currentPoint.y + 1];
            return spaceBelowLeft != '#' && spaceBelowLeft != 'o';
        }

        private void moveDownLeft(char[][] cave) {
            cave[currentPoint.x][currentPoint.y] = '.';
            currentPoint = new Point(currentPoint.x - 1, currentPoint.y + 1);
            cave[currentPoint.x][currentPoint.y] = 'o';
        }
    }

    static class FreeFlowingSandException extends Exception {}

    static class BlockedSandException extends Exception {}

    static class RockPath {
        List<Point> path;

        public RockPath() {
            path = new ArrayList<>();
        }

        public void add(Point pathPoint) {
            path.add(pathPoint);
        }
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}