package days;

import util.InputReader;

import java.util.Arrays;

public class Day5 {

    private Day5(String[] input) {
        System.out.printf("Part A: The highest seat ID is %s \n", findHighestSeatId(input));
        System.out.printf("Part B: Your seat is %s \n", findYourSeat(input));
    }

    private int findYourSeat(String[] tickets) {
        boolean[] seats = new boolean[findHighestSeatId(tickets)];
        for (String t : tickets) seats[calculateSeatId(calculateSeat(t)) - 1] = true;
        for (int i = 1; i < seats.length - 1; i++) if (!seats[i] && seats[i - 1] && seats[i + 1]) return i + 1;
        return -1;
    }

    private int findHighestSeatId(String[] tickets) {
//        int max = -1;
//        int tmp;
//        for (String t : tickets) if ((tmp = calculateSeatId(calculateSeat(t))) > max) max = tmp;
//        return max;
        return Arrays.stream(tickets).mapToInt(t -> calculateSeatId(calculateSeat(t))).max().getAsInt();
    }

    private int calculateSeatId(int[] seat) {
        return seat[0] * 8 + seat[1];
    }

    private int[] calculateSeat(String code) {
        int rowMin = 0;
        int rowMax = 127;
        int columnMin = 0;
        int columnMax = 7;
        for (int i = 0; i < 10; i++) {
            switch (code.charAt(i)) {
                case 'F':
                    rowMax -= (rowMax - rowMin) / 2 + 1;
                    break;
                case 'B':
                    rowMin += (rowMax - rowMin) / 2 + 1;
                    break;
                case 'L':
                    columnMax -= (columnMax - columnMin) / 2 + 1;
                    break;
                case 'R':
                    columnMin += (columnMax - columnMin) / 2 + 1;
                    break;
            }
//            System.out.printf("rowMin %s, rowMax %s, columnMin %s, columnMax %s \n", rowMin, rowMax, columnMin, columnMax);
        }
//        System.out.printf("Code %s is row %s column %s \n", code, rowMin, columnMin);
        return new int[]{rowMin, columnMin};
    }

    public static void main(String[] args) {
        System.out.println("Day 5");
//        new Day5(new String[]{"FBFBBFFRLR"});
//        new Day5(new String[]{"FBFBBFFRLR","BFFFBBFRRR", "FFFBBBFRRR","BBFFBBFRLL"});
        new Day5(InputReader.getLinesOfFile("input\\Day5.txt"));
//        Day 5
//        Part A: The highest seat ID is 965
//        Part B: Your seat is 524
    }
}