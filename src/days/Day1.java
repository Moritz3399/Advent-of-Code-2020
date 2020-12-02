package days;

import util.InputReader;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day1 {
    private int[] expenses;

    private Day1(String[] input) {
        this.expenses = Stream.of(input).mapToInt(Integer::parseInt).toArray();

        int[] partA = findEntries(2,0, new int[0], -1);
        System.out.println(String.format("First Answer: %s with %s",Arrays.stream(partA).reduce(1, (a,b) -> a*b),Arrays.toString(partA)));

        int[] partB = findEntries(3,0, new int[0], -1);
        System.out.println(String.format("Second Answer: %s with %s",Arrays.stream(partB).reduce(1, (a,b) -> a*b),Arrays.toString(partB)));
    }

    private int[] findEntries(int howManyEntriesToFind, int level, int[] previousLevels, int previousIndex) {
        int[] levels = Arrays.copyOf(previousLevels, previousLevels.length + 1);
        for (int i = 0; i < expenses.length; i++) {
            if(i == previousIndex) continue;
            levels[level] = expenses[i];
            if (levels.length != howManyEntriesToFind) {
                int [] tmp = findEntries(howManyEntriesToFind, level + 1, levels, i);
                if(tmp.length == howManyEntriesToFind) return tmp;
            } else {
              int sum = Arrays.stream(levels).sum();
              if(sum == 2020) return levels;
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println("Day 1 - rekursive");
        new Day1(InputReader.getLinesOfFile("input\\Day1.txt"));
    }

}
