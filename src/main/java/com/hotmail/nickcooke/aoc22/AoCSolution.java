package com.hotmail.nickcooke.aoc22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
}