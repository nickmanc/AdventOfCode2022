package com.hotmail.nickcooke.aoc22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class Day10 {

    public static void main(String[] args) throws IOException {
        Day10 day10 = new Day10();
        day10.solve();
    }

    void solve() throws IOException {
        Queue<String> instructions = getInstructions();
        String runningInstruction = "";
        int x = 1;
        int cycle = 1;
        int signalStrength = 0;
        char[][] crt = new char[6][40];
        while (!instructions.isEmpty() || runningInstruction != "") {
            crt[(cycle - 1) / 40][(cycle - 1) % 40] = pixelIsLit(cycle, x) ? '#' : '.';
            if (shouldMeasureSignalStrenth(cycle)) {
                signalStrength += cycle * x;
            }
            if (runningInstruction.equals("") && !instructions.isEmpty()) {
                runningInstruction = instructions.poll();
                runningInstruction = runningInstruction.equals("noop") ? "" : runningInstruction;
            } else if (!instructions.isEmpty()) {
                x += Integer.parseInt(runningInstruction.split(" ")[1]);
                runningInstruction = "";
            }
            cycle++;
        }
        System.out.println("Part 1: " + signalStrength);
        System.out.println();
        System.out.println("Part 2:");
        printCrt(crt);
    }

    private static boolean shouldMeasureSignalStrenth(int cycle) {
        return (cycle - 20) % 40 == 0;
    }

    private boolean pixelIsLit(int cycle, int x) {
        return (cycle - 1) % 40 >= x - 1 && (cycle - 1) % 40 <= x + 1;
    }

    private Queue<String> getInstructions() throws IOException {
        String inputFileName = "src\\main\\resources\\com\\hotmail\\nickcooke\\aoc22\\Day10Input";
        return new LinkedList<>(Files.readAllLines(Paths.get(inputFileName)));
    }

    public void printCrt(char[][] crt) {
        for (char[] row : crt) {
            for (char pixel : row) {
                System.out.print(pixel);
            }
            System.out.println();
        }
    }
}