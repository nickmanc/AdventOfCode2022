package com.hotmail.nickcooke.aoc22;

import com.hotmail.nickcooke.aoc22.Day13.Packet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day13Test {

    @Test
    public void compareSimplePacket() {
        Packet left = new Packet("[1,1,3,1,1]");
        Packet right = new Packet("[1,1,5,1,1]");
        System.out.println(left);
        System.out.println(right);
        Assertions.assertTrue(left.compareTo(left) == 0);
        Assertions.assertTrue(right.compareTo(right) == 0);
        Assertions.assertTrue(inRightOrder(left,left));
        Assertions.assertTrue(inRightOrder(right,right));
        Assertions.assertTrue(left.compareTo(right) < 0);
        Assertions.assertTrue(right.compareTo(left) > 0);
    }

    @Test
    public void compareWhenPacketsDifferentLengths() {
        //If the left list runs out of items first, the inputs are in the right order
        Packet left = new Packet("[1,1,3,1,1]");
        Packet right = new Packet("[1,1,3,1]");
        System.out.println(left);
        System.out.println(right);
        Assertions.assertTrue(left.compareTo(right) > 0);
        Assertions.assertTrue(right.compareTo(left) < 0);
    }

    @Test
    public void compareWhenPacketsContainLists() {
        //If the left list runs out of items first, the inputs are in the right order
        Packet left = new Packet("[5,[4,3],2,1]");
        Packet right = new Packet("[10,[9,8],7,6]");
        System.out.println(left);
        System.out.println(right);
        Assertions.assertTrue(left.compareTo(right) < 0);
        Assertions.assertTrue(right.compareTo(left) > 0);
    }

    @Test
    public void testExamples() {
        Packet left;
        Packet right;
        left = new Packet("[1,1,3,1,1]");
        right = new Packet("[1,1,5,1,1]");
        Assertions.assertTrue(inRightOrder(left,right));
        Assertions.assertTrue(!inRightOrder(right,left));
        left = new Packet("[[1],[2,3,4]]");
        right = new Packet("[[1],4]");
        Assertions.assertTrue(inRightOrder(left,right));
        Assertions.assertTrue(!inRightOrder(right,left));
        left = new Packet("[9]");
        right = new Packet("[[8,7,6]]");
        Assertions.assertTrue(inRightOrder(right,left));
        Assertions.assertTrue(!inRightOrder(left,right));
        left = new Packet("[7,7,7,7]");
        right = new Packet("[7,7,7]");
        Assertions.assertTrue(inRightOrder(right,left));
        Assertions.assertTrue(!inRightOrder(left,right));
        left = new Packet("[[4,4],4,4]");
        right = new Packet("[[4,4],4,4,4]");
        Assertions.assertTrue(inRightOrder(left,right));
        Assertions.assertTrue(!inRightOrder(right,left));
        left = new Packet("[]");
        right = new Packet("[3]");
        Assertions.assertTrue(inRightOrder(left,right));
        Assertions.assertTrue(!inRightOrder(right,left));
        left = new Packet("[[[]]]");
        right = new Packet("[[]]");
        Assertions.assertTrue(inRightOrder(right,left));
        Assertions.assertTrue(!inRightOrder(left,right));
        left = new Packet("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        right = new Packet("[1,[2,[3,[4,[5,6,0]]]],8,9]");
        Assertions.assertTrue(inRightOrder(right,left));
        Assertions.assertTrue(!inRightOrder(left,right));
    }

    private boolean inRightOrder(Packet left, Packet right) {
        return left.compareTo(right)<=0;
    }
}