package com.hotmail.nickcooke.aoc22;

public class Day3 extends AoCSolution {

    public static void main(String[] args) {
        Day3 day3 = new Day3();
        day3.getInput();
        day3.part1();
        day3.part2();
    }

    @Override
    void part1() {
        int totalPriority = 0;
        for (String bag : inputLines) {
            totalPriority += getPriorityOfCommonItem(bag);
        }
        System.out.println("Part 1: " + totalPriority);
    }

    private int getPriorityOfItem(int character) {
        if (character > 96) {
            return character - 96;
        }
        return character - 38;
    }

    private int getPriorityOfCommonItem(String bag) {
        int asciiValue = (int) getCommonItem(bag);
        return getPriorityOfItem(asciiValue);
    }

    private char getCommonItem(String line) {
        String bag1 = line.substring(0, (line.length() / 2));
        String bag2 = line.substring(line.length() / 2);
        return getCommonLetter(bag1, bag2);
    }

    private char getCommonLetter(String bag1, String bag2) {
        for (char letter : bag1.toCharArray()) {
            if (bag2.indexOf(letter) > -1) {
                return letter;
            }
        }
        throw new RuntimeException();
    }

    @Override
    void part2() {
        int badgeTotals = 0;
        for (int i = 0; i < inputLines.size() - 1; i += 3) {
            String bag1 = inputLines.get(i);
            String bag2 = inputLines.get(i + 1);
            String bag3 = inputLines.get(i + 2);
            for (char letter : bag1.toCharArray()) {
                if (bag2.indexOf(letter) > -1 && bag3.indexOf(letter) > -1) {
                    System.out.println("letter: " + letter);
                    badgeTotals += getPriorityOfItem((int) letter);
                    break;
                }
            }
        }
        System.out.println("Part 2: " + badgeTotals);
    }
}