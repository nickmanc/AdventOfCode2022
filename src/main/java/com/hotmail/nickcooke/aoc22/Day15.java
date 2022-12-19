package com.hotmail.nickcooke.aoc22;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 extends AoCSolution {

    public static void main(String[] args) {
        Day15 day15 = new Day15();
        day15.getInput();
        day15.part1Timed();
        day15.part2Timed();
    }

    @Override
    void part1() {

        Set<Point> beaconPoints = new HashSet<>();
        Set<Point> notBeaconPoints = new HashSet<>();
        List<BeaconSensor> beaconSensors = getBeaconSensors();

//        long LINE_OF_INTEREST = 2000000;
        long LINE_OF_INTEREST = 10;
        for (BeaconSensor beaconSensor : beaconSensors) {
            Point beacon = beaconSensor.nearestBeacon;
            Point sensor = beaconSensor.sensor;
            if (beaconSensor.nearestBeacon.y == LINE_OF_INTEREST) {
                beaconPoints.add(beaconSensor.nearestBeacon);
            }
            if (sensor.y < LINE_OF_INTEREST && sensor.y + beaconSensor.manhattanDistanceFromNearestBeacon >= LINE_OF_INTEREST) {
                long widthOfScanAtLineOfInterest = sensor.y + sensor.manhattanDistanceBetween(beacon) - LINE_OF_INTEREST;
                for (long x = sensor.x - widthOfScanAtLineOfInterest; x <= sensor.x + widthOfScanAtLineOfInterest; x++) {
                    notBeaconPoints.add(new Point(x, LINE_OF_INTEREST));
                }
            } else if (sensor.y >= LINE_OF_INTEREST && sensor.y - sensor.manhattanDistanceBetween(beacon) <= LINE_OF_INTEREST) {
                long widthOfScanAtLineOfInterest = Math.abs(sensor.y - sensor.manhattanDistanceBetween(beacon) - LINE_OF_INTEREST);
                for (long x = sensor.x - widthOfScanAtLineOfInterest; x <= sensor.x + widthOfScanAtLineOfInterest; x++) {
                    notBeaconPoints.add(new Point(x, LINE_OF_INTEREST));
                }
            }
        }
        notBeaconPoints.removeAll(beaconPoints);
        System.out.println("Part 1: " + notBeaconPoints.size());
    }

    void part2() {
        List<BeaconSensor> beaconSensors = getBeaconSensors();
        Collections.sort(beaconSensors);
        Point distressSignalLocation = findDistressSignalLocation(beaconSensors);
        long tuningFrequency = getTuningFrequency(distressSignalLocation);
        System.out.println("Part 2: " + tuningFrequency);
    }

    private static long getTuningFrequency(Point distressSignalLocation) {
        return (distressSignalLocation.x * 4000000) + distressSignalLocation.y;
    }

    private Point findDistressSignalLocation(List<BeaconSensor> beaconSensors) {
        for (BeaconSensor beaconSensor : beaconSensors) {
            Set<Point> potentialLocations = beaconSensor.getPerimeterPoints(beaconSensor.manhattanDistanceFromNearestBeacon + 1);
            potentialLocations.removeAll((beaconSensors.stream().map(bs -> bs.nearestBeacon).collect(Collectors.toSet())));
            for (BeaconSensor other : beaconSensors) {
                if (!other.equals(beaconSensor)) {
                    Set<Point> locationsRuledOut = new HashSet<>();
                    for (Point potentialLocation : potentialLocations) {
                        if (other.isWithinRadius(potentialLocation)) {
                            locationsRuledOut.add(potentialLocation);
                        }
                    }
                    potentialLocations.removeAll(locationsRuledOut);
                    if (potentialLocations.size() == 1) {
                        return potentialLocations.iterator().next();
                    }
                }
            }
        }
        throw new RuntimeException("Failed to find distress signal");
    }

    private List<BeaconSensor> getBeaconSensors() {
        Pattern sensorReportPattern = Pattern.compile("Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)");
        List<BeaconSensor> beaconSensors = new ArrayList<>();
        for (String line : inputLines) {
            Matcher m = sensorReportPattern.matcher(line);
            if (m.matches()) {
                Point sensor = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Point beacon = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                BeaconSensor beaconSensor = new BeaconSensor(sensor, beacon);
                beaconSensors.add(beaconSensor);
            }
        }
        return beaconSensors;
    }

    static class Point {

        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public long manhattanDistanceBetween(Point other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }
    }

    static class BeaconSensor implements Comparable<BeaconSensor> {
        Point sensor;
        Point nearestBeacon;

        long manhattanDistanceFromNearestBeacon;

        public BeaconSensor(Point sensor, Point beacon) {
            this.nearestBeacon = beacon;
            this.sensor = sensor;
            this.manhattanDistanceFromNearestBeacon = getManhattanDistanceFromSensor(beacon);
        }

        private long getManhattanDistanceFromSensor(Point otherPoint) {
            return Math.abs(otherPoint.x - sensor.x) + Math.abs(otherPoint.y - sensor.y);
        }

        public boolean isWithinRadius(Point otherPoint) {
            return getManhattanDistanceFromSensor(otherPoint) < manhattanDistanceFromNearestBeacon;
        }

        public Set<Point> getPerimeterPoints(long radius) {
            Set<Point> perimeterPoints = new HashSet<>();
            //get bottom left of diamond
            Point currentPoint = new Point(sensor.x, sensor.y + radius);
            //get top left side
            for (int i = 0; i < radius; i++) {
                currentPoint = new Point(currentPoint.x - 1, currentPoint.y - 1);
                perimeterPoints.add(currentPoint);
            }
            //get top left side
            for (int i = 0; i < radius; i++) {
                currentPoint = new Point(currentPoint.x + 1, currentPoint.y - 1);
                perimeterPoints.add(currentPoint);
            }
            //get top right side
            for (int i = 0; i < radius; i++) {
                currentPoint = new Point(currentPoint.x + 1, currentPoint.y + 1);
                perimeterPoints.add(currentPoint);
            }
            //get bottom right side
            for (int i = 0; i < radius; i++) {
                currentPoint = new Point(currentPoint.x - 1, currentPoint.y + 1);
                perimeterPoints.add(currentPoint);
            }
            return perimeterPoints;
        }

        @Override
        public int compareTo(BeaconSensor other) {
            if (this.manhattanDistanceFromNearestBeacon > other.manhattanDistanceFromNearestBeacon) {
                return 1;
            } else if (this.manhattanDistanceFromNearestBeacon < other.manhattanDistanceFromNearestBeacon) {
                return -1;
            }
            return 0;
        }
    }
}