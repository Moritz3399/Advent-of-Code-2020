package days;

import java.util.HashMap;

public class Day15 {


    private Day15(int[] startingNumbers, int target) {
        System.out.println(this.playMemoryGame(startingNumbers, target));
    }
    private int playMemoryGame(int[] startingNumbers, int target) {
        HashMap<Integer, Integer> turns = new HashMap<>(); // key = number & value = turn
        int t = 1; // turn number
        while (t < startingNumbers.length) {
            // ignore last of starting numbers
            turns.put(startingNumbers[t - 1], t);
            t++;
        }
        // set last starting number as last spoken value and increase t
        int last = startingNumbers[startingNumbers.length - 1];
        t++;
        while (t <= target) {
            int s = turns.getOrDefault(last, 0);
            turns.put(last, t - 1);
            if (s == 0) { // number never has been spoken
                last = 0;
            } else {
                last = (t - 1) - s;
            }
            t++;
        }
//        System.out.println(turns.toString());
        return last;
    }

    public static void main(String[] args) {
        System.out.println("Day 15");
        System.out.println("Part A");
//        //Example 1: 0,3,6 => 436
//        new Day15(new int[]{0, 3, 6}, 2020);
//
//        //Example 2: => 1
//        new Day15(new int[]{1, 3, 2},2020);
//
//        //Example 3:  => 10
//        new Day15(new int[]{2, 1, 3},2020);
//
//        //Example 4:  => 27
//        new Day15(new int[]{1, 2, 3},2020);
//
//        //Example 5: => 78
//        new Day15(new int[]{2, 3, 1},2020);
//
//        //Example 6: => 438
//        new Day15(new int[]{3, 2, 1},2020);
//
//        //Example 7: => 1836
//        new Day15(new int[]{3, 1, 2},2020);

        // Part A => 257
        new Day15(new int[]{0,14,6,20,1,4},2020);

        System.out.println("Part B");
//        //Example 1: 0,3,6 => 175594
//        new Day15(new int[]{0, 3, 6}, 30000000);
//
//        //Example 2: => 2578
//        new Day15(new int[]{1, 3, 2},30000000);
//
//        //Example 3:  => 3544142
//        new Day15(new int[]{2, 1, 3},30000000);
//
//        //Example 4:  => 261214
//        new Day15(new int[]{1, 2, 3},30000000);
//
//        //Example 5: => 6895259
//        new Day15(new int[]{2, 3, 1},30000000);
//
//        //Example 6: => 18
//        new Day15(new int[]{3, 2, 1},30000000);
//
//        //Example 7: => 362
//        new Day15(new int[]{3, 1, 2},30000000);

        // Part B => 8546398
        new Day15(new int[]{0,14,6,20,1,4},30000000);

    }

}
