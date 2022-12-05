package com.hotmail.nickcooke.aoc22;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends AoCSolution {

    public static void main(String[] args) {
        Day4 day4 = new Day4();
        day4.getInput();
        day4.part1();
        day4.part2();
    }

    @Override
    void part1() {
        int totalOverlaps = 0;
        for (String line: inputLines){
            Pattern p = Pattern.compile("(\\d*)-(\\d*),(\\d*)-(\\d*)");
            Matcher m = p.matcher(line);
            if(m.matches()) {
                if ((Integer.parseInt(m.group(1)) >= Integer.parseInt(m.group(3)) && Integer.parseInt(m.group(2)) <= Integer.parseInt(m.group(4)))
                ||(Integer.parseInt(m.group(3)) >= Integer.parseInt(m.group(1)) && Integer.parseInt(m.group(4)) <= Integer.parseInt(m.group(2)))){
                    totalOverlaps++;
                }
            }
        }
        System.out.println("Part 1: " + totalOverlaps);
    }

    @Override
    void part2() {
        int totalOverlaps = 0;
        for (String line: inputLines){
            Pattern p = Pattern.compile("(\\d*)-(\\d*),(\\d*)-(\\d*)");
            Matcher m = p.matcher(line);
            if(m.matches()) {
                if (!(Integer.parseInt(m.group(2))< Integer.parseInt(m.group(3))||Integer.parseInt(m.group(1)) > Integer.parseInt(m.group(4)))){
                    totalOverlaps++;
                }
            }
        }
        System.out.println("Part 2: " + totalOverlaps);
    }
}