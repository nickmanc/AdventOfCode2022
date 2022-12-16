package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 extends AoCSolution {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        day1.getInput();
        day1.part1Timed();
        day1.part2Timed();
    }

    @Override
    void part1() {
        int maxCalories = 0;
        int currentCalories = 0;
        for (String line : inputLines) {
            if (!line.isEmpty()) {
                currentCalories += Integer.parseInt(line);
            } else {
                maxCalories = Math.max(currentCalories, maxCalories);
                currentCalories = 0;
            }
        }
        maxCalories = Math.max(currentCalories, maxCalories);
        System.out.println("Part 1: " + maxCalories);
    }

    @Override
    void part2() {
        List<Integer> elfCalories = new ArrayList<>();
        int currentCalories = 0;
        for (String line : inputLines) {
            if (!line.isEmpty()) {
                currentCalories += Integer.parseInt(line);
            } else {
                elfCalories.add(currentCalories);
                currentCalories = 0;
            }
        }
        elfCalories.add(currentCalories);//don't forget that the last line might not be blank
        Collections.sort(elfCalories);
        Collections.reverse(elfCalories);
        int topThree = elfCalories.get(0) + elfCalories.get(1) + elfCalories.get(2);
        System.out.println("Part 2: " + topThree);
    }
}