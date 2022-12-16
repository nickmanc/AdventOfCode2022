package com.hotmail.nickcooke.aoc22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends AoCSolution {

    public static void main(String[] args) {
        Day13 day13 = new Day13();
        day13.getInput();
        day13.part1Timed();
        day13.part2Timed();
    }

    @Override
    void part1() {
        int indicesSum = 0;
        for (int i = 1; i <= inputLines.size(); i += 3) {
            Packet left = new Packet(inputLines.get(i - 1));
            Packet right = new Packet(inputLines.get(i));
            if (left.compareTo(right) <= 0) {
                indicesSum += (i / 3) + 1;
            }
        }
        System.out.println("Part 1: " + indicesSum);
    }

    @Override
    void part2() {
        List<Packet> receivedPackets = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i += 3) {
            receivedPackets.add(new Packet(inputLines.get(i)));
            receivedPackets.add(new Packet(inputLines.get(i + 1)));
        }
        Packet dividerPacket2 = new Packet("[[2]]");
        receivedPackets.add(dividerPacket2);
        Packet dividerPacket6 = new Packet("[[6]]");
        receivedPackets.add(dividerPacket6);
        Collections.sort(receivedPackets);
        int decoderKey = (receivedPackets.indexOf(dividerPacket2) + 1) * (receivedPackets.indexOf(dividerPacket6) + 1);
        System.out.println("Part 2: " + decoderKey);
    }

    static class Packet implements Comparable<Packet> {
        List<Packet> subPackets = new ArrayList<>();
        int value = -1;

        public Packet(String packetString) {
            if (!packetString.equals("[]")) {//do nothing in this case.  Value is -1 and subPackets is empty
                if (doesNotContain(packetString, ',')) {//not a list
                    if (doesNotContain(packetString, '[')) {//just a single value
                        value = Integer.parseInt(packetString);
                    } else {
                        subPackets.add(new Packet(packetString.substring(1, packetString.length() - 1)));
                    }
                } else {
                    packetString = packetString.substring(1, packetString.length() - 1);//strip surrounding brackets
                    int subPacketStart = 0;
                    for (int i = 0; i < packetString.length(); i++) {
                        if (packetString.charAt(i) == '[') {
                            int closingBracketPosition = i + getClosingBracketPosition(packetString.substring(i));
                            subPackets.add(new Packet(packetString.substring(i, closingBracketPosition)));
                            subPacketStart = closingBracketPosition + 1;
                            i = closingBracketPosition;
                        } else if (packetString.charAt(i) == ',') {
                            String subPacketString = packetString.substring(subPacketStart, i);
                            subPackets.add(new Packet(subPacketString));
                            subPacketStart = i + 1;
                        } else if (i == packetString.length() - 1) {//at end of subpacket
                            String subPacketString = packetString.substring(subPacketStart);
                            subPackets.add(new Packet(subPacketString));
                        }
                    }
                }
            }
        }

        private boolean doesNotContain(String string, char c) {
            return string.indexOf(c) < 0;
        }

        private int getClosingBracketPosition(String string) {
            int bracketCount = 0;
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '[') {
                    bracketCount++;
                } else if (string.charAt(i) == ']') {
                    if (bracketCount > 1) {
                        bracketCount--;
                    } else {
                        return i + 1;
                    }
                }
            }
            throw new RuntimeException("Failed to find a closing bracket in " + string);
        }

        @Override
        public String toString() {
            if (value > -1) return value + "";
            return "[" + subPackets.stream().map(Packet::toString).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public int compareTo(Packet other) {
            if (this.value > -1 && other.value > -1) {
                return this.value - other.value;
            } else {
                if (this.value > -1) {
                    this.subPackets.add(new Packet(this.value + ""));
                    this.value = -1;
                } else if (other.value > -1) {
                    other.subPackets.add(new Packet(other.value + ""));
                    other.value = -1;
                }
                for (int i = 0; i < Math.max(subPackets.size(), other.subPackets.size()); i++) {
                    if (subPackets.size() <= i) {
                        return -1;
                    } else if (other.subPackets.size() <= i) {
                        return 1;
                    }
                    int result = subPackets.get(i).compareTo(other.subPackets.get(i));
                    if (result != 0) {
                        return result;
                    }
                }
            }
            return 0;
        }
    }
}