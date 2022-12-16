package com.hotmail.nickcooke.aoc22;

import java.util.HashSet;
import java.util.Set;

public class Day6 extends AoCSolution {

    public static void main(String[] args) {
        Day6 day6 = new Day6();
        day6.getInput();
        day6.part1Timed();
        day6.part2Timed();
    }

    @Override
    void part1() {
        int charactersBeforeStartOfPacketMarker = getCharactersProcessedBeforeFirstMarker(inputLines.get(0), 4);
        System.out.println("Part 1: " + charactersBeforeStartOfPacketMarker);
    }

    @Override
    void part2() {
        int charactersBeforeStartOfMessageMarker = getCharactersProcessedBeforeFirstMarker(inputLines.get(0), 14);
        System.out.println("Part 2: " + charactersBeforeStartOfMessageMarker);
    }

    private int getCharactersProcessedBeforeFirstMarker(String signal, int uniqueCharactersInMarker) {
        char[] characters = signal.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            Set<Character> potentialMarker = new HashSet<>();
            for (int j = 0; j < uniqueCharactersInMarker; j++) {
                potentialMarker.add(characters[i + j]);
            }
            if (potentialMarker.size() == uniqueCharactersInMarker) {
                return i + uniqueCharactersInMarker;
            }
        }
        throw new RuntimeException("Failed to detect marker");
    }
}