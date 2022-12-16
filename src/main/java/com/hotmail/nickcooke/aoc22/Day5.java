package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends AoCSolution {

    public static void main(String[] args) {
        Day5 day5 = new Day5();
        day5.getInput();
        day5.part1Timed();
        day5.part2Timed();
    }

    Pattern crateMovePattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    @Override
    void part1() {
        int stackLabelLine = getIndexOfStackLabelLine();
        List<Stack<Character>> stacks = buildInitialStacks(inputLines, stackLabelLine);

        for (String inputLine : inputLines) {
            if (inputLine.matches(crateMovePattern.pattern())) {
                Matcher m = crateMovePattern.matcher(inputLine);
                if (m.matches()) {
                    int numberOfCratesToMove = Integer.parseInt(m.group(1));
                    Stack<Character> moveFromStack = stacks.get(Integer.parseInt(m.group(2)) - 1);
                    Stack<Character> moveToStack = stacks.get(Integer.parseInt(m.group(3)) - 1);
                    for (int i = 1; i <= numberOfCratesToMove; i++) {
                        char crate = moveFromStack.pop();
                        moveToStack.push(crate);
                    }
                }
            }
        }
        System.out.println("Part 1: " + getTopCrates(stacks));
    }

    @Override
    void part2() {
        int stackLabelLine = getIndexOfStackLabelLine();
        List<Stack<Character>> stacks = buildInitialStacks(inputLines, stackLabelLine);
        for (String inputLine : inputLines) {
            if (inputLine.matches(crateMovePattern.pattern())) {
                Matcher m = crateMovePattern.matcher(inputLine);
                if (m.matches()) {
                    int numberOfCratesToMove = Integer.parseInt(m.group(1));
                    Stack<Character> moveFromStack = stacks.get(Integer.parseInt(m.group(2)) - 1);
                    Stack<Character> moveToStack = stacks.get(Integer.parseInt(m.group(3)) - 1);
                    Stack<Character> tempStack = new Stack<>();
                    for (int i = 1; i <= numberOfCratesToMove; i++) {
                        char crate = moveFromStack.pop();
                        tempStack.push(crate);
                    }
                    while (!tempStack.isEmpty()) {
                        moveToStack.push(tempStack.pop());
                    }
                }
            }
        }
        System.out.println("Part 2: " + getTopCrates(stacks));
    }

    private List<Stack<Character>> buildInitialStacks(List<String> inputLines, int stackLabelLine) {
        int numberOfStacks = (inputLines.get(0).length() + 1) / 4;
        List<Stack<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < numberOfStacks; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = stackLabelLine - 1; i >= 0; i--) {
            for (int j = 0; j < numberOfStacks; j++) {
                if (inputLines.get(i).toCharArray()[(4 * j) + 1] != ' ') {
                    stacks.get(j).add(inputLines.get(i).toCharArray()[(4 * j) + 1]);
                }
            }
        }
        return stacks;
    }

    private int getIndexOfStackLabelLine() {
        for (int i = 0; i < inputLines.size(); i++) {
            if (inputLines.get(i).matches("(\\s+\\d\\s*)+")) {
                return i;
            }
        }
        return -1;
    }

    private String getTopCrates(List<Stack<Character>> stacks) {
        StringBuilder topStacks = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            topStacks.append(stack.pop());
        }
        return topStacks.toString();
    }

    private void printStacks(List<Stack<Character>> stacks) {
        for (Stack<Character> stack : stacks) {
            System.out.println(stack);
        }
        System.out.println("================");
    }
}