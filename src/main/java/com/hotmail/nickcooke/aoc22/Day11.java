package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 extends AoCSolution {

    public static void main(String[] args) {
        Day11 day11 = new Day11();
        day11.getInput();
        day11.part1Timed();
        day11.part2Timed();
    }

    @Override
    void part1() {
        long monkeyBusiness = solve(20);
        System.out.println("Part 1: " + monkeyBusiness);
    }

    @Override
    void part2() {
        long monkeyBusiness = solve(10_000, true);
        System.out.println("Part 2: " + monkeyBusiness);
    }

    long solve(int numberOfRounds) {
        return solve(numberOfRounds, false);
    }

    long solve(int numberOfRounds, boolean isPart2) {
        List<Monkey> monkeys = getMonkeys();
        int productOfTestValues = 1;
        for (Monkey monkey : monkeys) {
            productOfTestValues *= monkey.testValue;
        }
        for (int i = 0; i < numberOfRounds; i++) {
            for (Monkey monkey : monkeys) {
                monkey.inspectItems(productOfTestValues, isPart2);
            }
        }
        List<Long> monkeyActivity = new ArrayList<>();
        for (Monkey monkey : monkeys) {
            monkeyActivity.add(monkey.inspectedItemsCount);
        }
        Collections.sort(monkeyActivity);
        return monkeyActivity.get(monkeyActivity.size() - 1) * monkeyActivity.get(monkeyActivity.size() - 2);
    }

    private List<Monkey> getMonkeys() {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < (inputLines.size() + 1) / 7; i++) {
            monkeys.add(new Monkey());
        }
        for (int i = 0; i < inputLines.size(); i += 7) {
            int monkeyIndex = Character.getNumericValue(inputLines.get(i).charAt(7));
            Monkey monkey = monkeys.get(monkeyIndex);
            monkey.index = monkeyIndex;
            String items = inputLines.get(i + 1).replace("Starting items: ", "");
            monkey.itemWorryLevels = Arrays.stream(items.split(", ")).map(String::trim).map(Long::parseLong).collect(Collectors.toList());
            String operation = inputLines.get(i + 2);
            if (operation.contains("old * old")) {
                monkey.operationType = OperationType.SQUARE;
            } else if (operation.contains("+")) {
                monkey.operationType = OperationType.PLUS;
                monkey.operationValue = Long.parseLong(operation.replaceAll("[^0-9]", ""));
            } else {
                monkey.operationType = OperationType.MULTIPLY;
                monkey.operationValue = Long.parseLong(operation.replaceAll("[^0-9]", ""));
            }
            monkey.testValue = Long.parseLong(inputLines.get(i + 3).replaceAll("[^0-9]", ""));
            int trueMonkeyIndex = Integer.parseInt(inputLines.get(i + 4).replaceAll("[^0-9]", ""));
            monkey.trueMonkey = monkeys.get(trueMonkeyIndex);
            int falseMonkeyIndex = Integer.parseInt(inputLines.get(i + 5).replaceAll("[^0-9]", ""));
            monkey.falseMonkey = monkeys.get(falseMonkeyIndex);
        }
        return monkeys;
    }

    static class Monkey {
        List<Long> itemWorryLevels = new ArrayList<>();
        OperationType operationType;
        long operationValue;
        long testValue;
        int index;
        long inspectedItemsCount = 0;
        Monkey trueMonkey;
        Monkey falseMonkey;

        public void inspectItems(int productOfTestValues, boolean isPart2) {
            for (long itemWorryLevel : itemWorryLevels) {
                itemWorryLevel = inspectItem(itemWorryLevel, productOfTestValues, isPart2);
                if (isMultipleOf(itemWorryLevel, testValue)) {
                    trueMonkey.itemWorryLevels.add(itemWorryLevel);
                } else {
                    falseMonkey.itemWorryLevels.add(itemWorryLevel);
                }
            }
            itemWorryLevels.clear();
        }

        private boolean isMultipleOf(long itemWorryLevel, long testValue) {
            return itemWorryLevel % (testValue) == 0;
        }

        private long inspectItem(long itemWorryLevel, long productOfTestValues, boolean isPart2) {
            inspectedItemsCount++;
            if (operationType == OperationType.SQUARE) {
                itemWorryLevel *= itemWorryLevel;
            } else if (operationType == OperationType.PLUS) {
                itemWorryLevel += operationValue;
            } else if (operationType == OperationType.MULTIPLY) {
                itemWorryLevel *= operationValue;
            }
            if (isPart2) {
                if (itemWorryLevel > productOfTestValues) {
                    itemWorryLevel %= productOfTestValues;
                }
            } else {
                itemWorryLevel /= 3;
            }
            return itemWorryLevel;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "index=" + index +
                    ", inspectedItemsCount=" + inspectedItemsCount +
                    ", items=" + itemWorryLevels +
                    ", operationType=" + operationType +
                    ", operationValue=" + operationValue +
                    ", testValue=" + testValue +
                    ", trueMonkey=" + trueMonkey.index +
                    ", falseMonkey=" + falseMonkey.index +
                    '}';
        }
    }

    enum OperationType {
        PLUS, MULTIPLY, SQUARE
    }
}

