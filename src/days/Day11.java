package days;

import util.InputReader;

public class Day11 {

    private SeatLayout[][] grid;
    private boolean changed = true;
    private final int tolerance;
    private final boolean onlyDirectNeighbours;

    private Day11(String[] input, boolean onlyDirectNeighbours, int tolerance) {
        this.tolerance = tolerance;
        this.onlyDirectNeighbours = onlyDirectNeighbours;
        this.parseInput(input);
        this.simulate();
        System.out.printf("When there are no more changes, %s seats are occupied.\n", countOcupiedSeatsTotal());
    }

    void simulate() {
        int cnt = 0;
        while (changed) {
            cnt++;
            changed = false;
//            this.printGrid();

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].isFloor) {
                        continue;
                    }
                    if (grid[i][j].emptySeat && countOcupiedSeatsAroundSeat(i, j) == 0) {
                        grid[i][j].nextRoundFree = false;
                        changed = true;
                        continue;
                    }
                    if (!grid[i][j].emptySeat && countOcupiedSeatsAroundSeat(i, j) >= tolerance) {
                        grid[i][j].nextRoundFree = true;
                        changed = true;
                    }
                }
            }

            if (changed) {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j].isFloor) continue;
                        grid[i][j].emptySeat = grid[i][j].nextRoundFree;
                    }
                }
            }

        }
        System.out.printf("Stopped after %s rounds\n", cnt - 1);

    }

    private int countOcupiedSeatsTotal() {
        int cnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isOcupied()) cnt++;
            }
        }
        return cnt;
    }

    private int countOcupiedSeatsAroundSeat(int i, int j) {
        int cnt = 0;
        if (isNighbourOccupied(i, j, -1, -1)) cnt++;
        if (isNighbourOccupied(i, j, -1, 0)) cnt++;
        if (isNighbourOccupied(i, j, -1, 1)) cnt++;
        if (isNighbourOccupied(i, j, 0, -1)) cnt++;
        if (isNighbourOccupied(i, j, 0, 1)) cnt++;
        if (isNighbourOccupied(i, j, 1, -1)) cnt++;
        if (isNighbourOccupied(i, j, 1, 0)) cnt++;
        if (isNighbourOccupied(i, j, 1, 1)) cnt++;
        return cnt;
    }

    private boolean isNighbourOccupied(int iStart, int jStart, int iDirection, int jDirection) {
        if (onlyDirectNeighbours) {
            if (iStart + iDirection >= 0 && iStart + iDirection < grid.length && jStart + jDirection >= 0 && jStart + jDirection < grid[iStart + iDirection].length)
                return grid[iStart + iDirection][jStart + jDirection].isOcupied();
            else return false;
        } else {
            int i = iStart + iDirection;
            int j = jStart + jDirection;
            while (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) {
                if (!grid[i][j].isFloor) {
                    return !grid[i][j].emptySeat;
                }
                i += iDirection;
                j += jDirection;
            }
            return false;
        }
    }

    private void parseInput(String[] input) {
        grid = new SeatLayout[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length(); j++) {
                grid[i][j] = new SeatLayout(input[i].charAt(j));
            }
        }
    }

//    private void printGrid() {
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j].toString());
//            }
//            System.out.print("\n");
//        }
//        System.out.print("\n\n");
//    }

    private static class SeatLayout {

        boolean isFloor = false;
        boolean emptySeat = false;
        boolean nextRoundFree = false;

        SeatLayout(char c) {
            if (c == '.') isFloor = true;
            if (c == 'L') {
                emptySeat = true;
                nextRoundFree = true;
            }
        }

        boolean isOcupied() {
            if (isFloor) return false;
            return !emptySeat;
        }

        @Override
        public String toString() {
            if (isFloor) return ".";
            return emptySeat ? "L" : "#";
        }

    }

    public static void main(String[] args) {
        System.out.println("Day 11");
//        new Day11(new String[]{"L.LL.LL.LL", "LLLLLLL.LL", "L.L.L..L..", "LLLL.LL.LL", "L.LL.LL.LL", "L.LLLLL.LL", "..L.L.....", "LLLLLLLLLL", "L.LLLLLL.L", "L.LLLLL.LL"},true,4);
//        new Day11(new String[]{"L.LL.LL.LL", "LLLLLLL.LL", "L.L.L..L..", "LLLL.LL.LL", "L.LL.LL.LL", "L.LLLLL.LL", "..L.L.....", "LLLLLLLLLL", "L.LLLLLL.L", "L.LLLLL.LL"},false,5);
        System.out.println("Part A:");
        new Day11(InputReader.getLinesOfFile("input\\Day11.txt"), true, 4);
        System.out.println("Part B:");
        new Day11(InputReader.getLinesOfFile("input\\Day11.txt"), false, 5);

//        Day 11
//        Part A:
//        Stopped after 77 rounds
//        When there are no more changes, 2263 seats are occupied.
//                Part B:
//        Stopped after 82 rounds
//        When there are no more changes, 2002 seats are occupied.

    }

}
