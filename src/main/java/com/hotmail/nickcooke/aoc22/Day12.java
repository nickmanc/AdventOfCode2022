package com.hotmail.nickcooke.aoc22;


import java.util.*;

public class Day12 extends AoCSolution {

    public static void main(String[] args) {
        Day12 day12 = new Day12();
        day12.getInput();
        day12.part1Timed();
        day12.part2Timed();
    }

    @Override
    void part1() {
        HeightMap heightMap = new HeightMap(inputLines);
        System.out.println("Part 1: " + getMinimumDistanceBetweenSquares(heightMap.goal, heightMap, false));
    }

    @Override
    void part2() {
        HeightMap heightMap = new HeightMap(inputLines);
        System.out.println("Part 2: " + getMinimumDistanceBetweenSquares(heightMap.goal, heightMap, true));
    }

    public int getMinimumDistanceBetweenSquares(Square fromSquare, HeightMap heightMap, boolean isPart2) {
        fromSquare.distanceFromStart = 0;
        Queue<Square> unvisitedSquares = new LinkedList<>(List.of(fromSquare));
        Set<Square> visitedSquares = new HashSet<>();
        while (!unvisitedSquares.isEmpty()) {
            Square squareToCheck = unvisitedSquares.poll();
            if (!visitedSquares.contains(squareToCheck)) {
                List<Square> neighbours = heightMap.getAdjacentTraversableSquares(squareToCheck);
                for (Square neighbour : neighbours) {
                    neighbour.distanceFromStart = squareToCheck.distanceFromStart + 1;
                    if (!isPart2 && neighbour.isStart()) {
                        return neighbour.distanceFromStart;
                    }
                    else if (isPart2 && neighbour.isPotentialStart()) {
                        return neighbour.distanceFromStart;
                    }
                }
                unvisitedSquares.addAll(neighbours);
            }
            visitedSquares.add(squareToCheck);
        }
        return Integer.MAX_VALUE; //in case target can't be found
    }

    static class HeightMap {
        final Square[][] map;
        Square start;
        Square goal;

        public HeightMap(List<String> inputLines) {
            map = new Square[inputLines.size()][inputLines.get(0).length()];
            for (int i = 0; i < inputLines.size(); i++) {
                for (int j = 0; j < inputLines.get(0).length(); j++) {
                    Square square = new Square(i, j, inputLines.get(i).charAt(j));
                    map[i][j] = square;
                    if (square.isStart()) {
                        start = square;
                    } else if (square.isGoal()) {
                        goal = square;
                    }
                }
            }
        }

        public List<Square> getAdjacentSquares(Square square) {
            List<Square> adjacentSquares = new ArrayList<>();
            if (square.x > 0) adjacentSquares.add(map[square.x - 1][square.y]);
            if (square.x < map.length - 1) adjacentSquares.add(map[square.x + 1][square.y]);
            if (square.y > 0) adjacentSquares.add(map[square.x][square.y - 1]);
            if (square.y < map[0].length - 1) adjacentSquares.add(map[square.x][square.y + 1]);
            return adjacentSquares;
        }

        public List<Square> getAdjacentTraversableSquares(Square square) {
            List<Square> adjacentTraversableSquares = new ArrayList<>();
            for (Square adjacentSquare : getAdjacentSquares(square)) {
               if ( square.elevation - 1 <= adjacentSquare.elevation) {
                    adjacentTraversableSquares.add(adjacentSquare);
                }
            }
            return adjacentTraversableSquares;
        }
    }

    static class Square {
        final char elevation;
        final int x;
        final int y;
        int distanceFromStart = Integer.MAX_VALUE;
        final boolean isStart;
        final boolean isGoal;

        public Square(int x, int y, char elevation) {
            this.x = x;
            this.y = y;
            this.isStart = elevation == 'S';
            this.isGoal = elevation == 'E';
            if (isStart) {
                this.elevation = 'a';
            } else if (isGoal) {
                this.elevation = 'z';
            } else {
                this.elevation = elevation;
            }
        }

        public boolean isPotentialStart() {
            return elevation == 'a';
        }

        public boolean isStart() {
            return isStart;
        }

        public boolean isGoal() {
            return isGoal;
        }

        @Override
        public String toString() {
            if (isStart) {
                return "S";
            } else if (isGoal) {
                return "E";
            } else {
                return elevation + "";
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Square square = (Square) o;
            return x == square.x && y == square.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}