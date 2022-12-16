package com.hotmail.nickcooke.aoc22;

import java.util.List;

public class Day8 extends AoCSolution {

    public static void main(String[] args) {
        Day8 day8 = new Day8();
        day8.getInput();
        day8.part1Timed();
        day8.part2Timed();
    }

    @Override
    void part1() {
        int[][] treeGrid = parseGrid(inputLines);
        int visibleTrees = calculateVisibleTrees(treeGrid);
        System.out.println("Part 1: " + (visibleTrees));
    }


    @Override
    void part2() {
        int[][] treeGrid = parseGrid(inputLines);
        int mostViewableTrees = calculateMostViewableTrees(treeGrid);
        System.out.println("Part 2: " + (mostViewableTrees));
    }

    private int calculateMostViewableTrees(int[][] treeGrid) {
        int mostViewableTrees = 0;
        for (int i = 0; i < treeGrid.length; i++) {
            for (int j = 0; j < treeGrid[0].length; j++) {
                mostViewableTrees = Math.max(mostViewableTrees, countViewableTrees(treeGrid, i, j));
            }
        }
        return mostViewableTrees;
    }

    private int countViewableTrees(int[][] treeGrid, int x, int y) {
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        int treeHeight = treeGrid[x][y];
        int heightToCompare;
        for (int i = x + 1; i < treeGrid.length; i++) {
            heightToCompare = treeGrid[i][y];
            down++;
            if (heightToCompare >= treeHeight) break;
        }
        for (int i = x - 1; i >= 0; i--) {
            heightToCompare = treeGrid[i][y];
            up++;
            if (heightToCompare >= treeHeight) break;
        }
        for (int j = y + 1; j < treeGrid.length; j++) {
            heightToCompare = treeGrid[x][j];
            right++;
            if (heightToCompare >= treeHeight) break;
        }
        for (int j = y - 1; j >= 0; j--) {
            heightToCompare = treeGrid[x][j];
            left++;
            if (heightToCompare >= treeHeight) break;
        }
        return up * down * left * right;
    }

    private int calculateVisibleTrees(int[][] treeGrid) {
        int count = 0;
        for (int i = 0; i < treeGrid.length; i++) {
            for (int j = 0; j < treeGrid[0].length; j++) {
                count += isTreeVisible(treeGrid, i, j) ? 1 : 0;
            }
        }
        return count;
    }

    private boolean isTreeVisible(int[][] treeGrid, int x, int y) {
        boolean visible = true;
        int treeHeight = treeGrid[x][y];
        for (int i = x + 1; i < treeGrid.length; i++) {
            int heightToCompare = treeGrid[i][y];
            if (heightToCompare >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int i = x - 1; i >= 0; i--) {
            int heightToCompare = treeGrid[i][y];
            if (heightToCompare >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int j = y + 1; j < treeGrid.length; j
                ++) {
            int heightToCompare = treeGrid[x][j];
            if (heightToCompare >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        visible = true;
        for (int j = y - 1; j >= 0; j--) {
            int heightToCompare = treeGrid[x][j];
            if (heightToCompare >= treeHeight) {
                visible = false;
                break;
            }
        }
        return visible;
    }

    private void printGrid(int[][] treeGrid) {
        for (int[] ints : treeGrid) {
            for (int j = 0; j < treeGrid[0].length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }
    }

    private int[][] parseGrid(List<String> inputLines) {
        int[][] treeGrid = new int[inputLines.size()][inputLines.get(0).length()];
        for (int i = 0; i < inputLines.size(); i++) {
            for (int j = 0; j < inputLines.get(0).length(); j++) {
                treeGrid[i][j] = Character.getNumericValue(inputLines.get(i).charAt(j));
            }
        }
        return treeGrid;
    }
}