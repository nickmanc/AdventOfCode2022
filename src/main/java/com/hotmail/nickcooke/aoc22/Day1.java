package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 extends AoCSolution {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        day1.getInput();
        day1.part1();
        day1.part2();
    }

    @Override
    void part1() {
        int maxCalories = 0;
        int currentCalories = 0;
        for (String line : inputLines) {
            if ("".equals(line)) {
                maxCalories = Math.max(currentCalories, maxCalories);
                currentCalories=0;
            } else {
                currentCalories += Integer.parseInt(line);
            }
        }
        maxCalories = Math.max(currentCalories, maxCalories);
        System.out.println("Most calories carried is " + maxCalories);
        assert maxCalories==68467;
    }

    @Override
    void part2() {
        List<Integer> elfCalories = new ArrayList<>();
        int currentCalories = 0;
        for (String line : inputLines) {
            if ("".equals(line)) {
                elfCalories.add(currentCalories);
                currentCalories=0;
            } else {
                currentCalories += Integer.parseInt(line);
            }
        }
        elfCalories.add(currentCalories);//don't forget that the last line might not be blank
        Collections.sort(elfCalories);
        Collections.reverse(elfCalories);
        int topThree = elfCalories.get(0) + elfCalories.get(1) + elfCalories.get(2);
        System.out.println("Top three: " + topThree);
        assert topThree==203420;
    }
}