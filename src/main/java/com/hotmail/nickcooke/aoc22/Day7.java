package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.List;

public class Day7 extends AoCSolution {
    public static final int LARGE_DIRECTORY_SIZE = 100_000;
    private static final int REQUIRED_UNUSED_SPACE = 30_000_000;
    private static final int DISK_SPACE = 70_000_000;

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        day7.getInput();
        day7.part1Timed();
        day7.part2Timed();
    }

    @Override
    void part1() {
        List<File> directories = parseInput(inputLines);
        int totalLargeDirectoriesSize = 0;
        for (File directory : directories) {
            if (directory.size <= LARGE_DIRECTORY_SIZE) {
                totalLargeDirectoriesSize += directory.size;
            }
        }
        System.out.println("Part 1: " + totalLargeDirectoriesSize);
    }

    @Override
    void part2() {
        List<File> directories = parseInput(inputLines);
        int rootDirectorySize = directories.stream().filter(d -> d.name.equals("/")).map(File::calculateSize).findFirst().orElseThrow();
        int smallestBigEnoughDirectorySize = Integer.MAX_VALUE;
        for (File directory : directories) {
            if (DISK_SPACE - rootDirectorySize + directory.size >= REQUIRED_UNUSED_SPACE) {
                smallestBigEnoughDirectorySize = Math.min(smallestBigEnoughDirectorySize, directory.size);
            }
        }
        System.out.println("Part 2: " + smallestBigEnoughDirectorySize);
    }

    private List<File> parseInput(List<String> inputLines) {
        File currentDirectory = null;
        List<File> directories = new ArrayList<>();
        for (String line : inputLines) {
            if (line.startsWith("$ cd ..")) {
                currentDirectory = currentDirectory.parent;
            } else if (line.startsWith("$ cd ")) {
                String changeDirectoryName = line.replace("$ cd ", "");
                File newDirectory = new File(changeDirectoryName, currentDirectory);
                directories.add(newDirectory);
                if (null != currentDirectory) {
                    currentDirectory.addChild(newDirectory);
                }
                currentDirectory = newDirectory;
            } else if (line.matches("\\d+ .*")) {
                File newFile = new File(line.split(" ")[1], Integer.parseInt(line.split(" ")[0]), currentDirectory);
                currentDirectory.addChild(newFile);
            }
        }
        while (null != currentDirectory.parent) {
            currentDirectory = currentDirectory.parent;
        }
        currentDirectory.calculateSize();//will calculate subdirectory sizes
        return directories;
    }

    static class File {
        int size;
        String name;
        List<File> children = new ArrayList<>();
        File parent;
        boolean isDirectory = false;

        public File(String name, File parent) {
            this.name = name;
            this.parent = parent;
            this.isDirectory = true;
        }

        public File(String name, int size, File parent) {
            this.name = name;
            this.parent = parent;
            this.size = size;
        }

        public void addChild(File child) {
            children.add(child);
        }

        public int calculateSize() {
            if (isDirectory) {
                int directorySize = 0;
                for (File child : children) {
                    directorySize += child.calculateSize();
                }
                size = directorySize;
            }
            return size;
        }
    }
}

