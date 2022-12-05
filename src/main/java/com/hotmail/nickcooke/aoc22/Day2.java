package com.hotmail.nickcooke.aoc22;

import java.util.HashMap;
import java.util.Map;

public class Day2 extends AoCSolution {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        day2.getInput();
        day2.part1();
        day2.part2();
    }

    @Override
    void part1() {
        System.out.println("hello day 2");
        Map<String,Integer> scoreMap = new HashMap<>();
        scoreMap.put("A X",4);//D
        scoreMap.put("A Y",8);//W
        scoreMap.put("A Z",3);//L
        scoreMap.put("B X",1);//L
        scoreMap.put("B Y",5);//D
        scoreMap.put("B Z",9);//W
        scoreMap.put("C X",7);//W
        scoreMap.put("C Y",2);//L
        scoreMap.put("C Z",6);//D

        int totalScore=0;
        for (String line:inputLines){
            totalScore+= scoreMap.get(line.strip());
        }
        System.out.println("Total score: " + totalScore);
    }

    @Override
    void part2() {
        Map<String,Integer> scoreMap = new HashMap<>();
        scoreMap.put("A X",3);//0 + 3
        scoreMap.put("A Y",4);//3 + 1
        scoreMap.put("A Z",8);//6 + 2
        scoreMap.put("B X",1);//0 + 1
        scoreMap.put("B Y",5);//3 + 2
        scoreMap.put("B Z",9);//6 + 3
        scoreMap.put("C X",2);//0 + 2
        scoreMap.put("C Y",6);//3 + 3
        scoreMap.put("C Z",7);//6 + 1

        int totalScore=0;
        for (String line:inputLines){
            totalScore+= scoreMap.get(line.strip());
        }
        System.out.println("Total score: " + totalScore);
    }
}