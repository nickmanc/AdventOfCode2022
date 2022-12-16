package com.hotmail.nickcooke.aoc22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AoCSolution {

    List<String> inputLines;

    protected void getInput() {
        String inputFileName = "src\\main\\resources\\com\\hotmail\\nickcooke\\aoc22\\" + this.getClass().getSimpleName() + "Input";
        try {
            inputLines = Files.readAllLines( Paths.get( inputFileName ) );
//            inputLines = inputLines.stream().filter(s -> !s.startsWith( "#" )).collect( Collectors.toList() );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    abstract void part1();
    abstract void part2();

    void part1Timed(){
        long startTime = System.currentTimeMillis();
        part1();
        System.out.println("Part 1 took " + (System.currentTimeMillis() - startTime) + " ms");
    }
    void part2Timed(){
        long startTime = System.currentTimeMillis();
        part2();
        System.out.println("Part 2 took " + (System.currentTimeMillis() - startTime) + " ms");
    }
}