package days;

import util.InputReader;

public class Day3 {

    private Day3(String[] input, int[][] path) { // path {{right, down},...}
        int result = 1;
        for (int[] ints : path) {
            result *= countTreesInPath(input, ints[0], ints[1]);
        }
        System.out.printf("The product of all hits is %s. \n", result);
    }

    private int countTreesInPath(String[] input, int right, int down) {
        int posY = 0; // down
        int posX = 0; // right
        int hits = 0;
        int mapWidth = input[0].length();
        while (posY < input.length) {
            if (input[posY].charAt(posX % mapWidth) == '#') hits++;
            posX += right;
            posY += down;
        }
        System.out.printf("It hit %s trees going %s right and %s down. \n", hits , right, down);
        return hits;
    }


    public static void main(String[] args) {
        String[] input = InputReader.getLinesOfFile("input\\Day3.txt");
        System.out.println("Day 3");
        System.out.println("Part A");
        new Day3(input, new int[][]{{3,1}});

        System.out.println("Part B");
        new Day3(input, new int[][]{{1,1},{3,1},{5,1},{7,1},{1,2}});
        System.out.println("Not correct answer 2124702224 (by system)");

//        System.out.println("Tests:");
//        String[] testInput = {
//                "..##.......",
//                "#...#...#..",
//                ".#....#..#.",
//                "..#.#...#.#",
//                ".#...##..#.",
//                "..#.##.....",
//                ".#.#.#....#",
//                ".#........#",
//                "#.##...#...",
//                "#...##....#",
//                ".#..#...#.#"};
//        new Day3(testInput, new int[][]{{3,1}});
//        new Day3(testInput, new int[][]{{1,1},{3,1},{5,1},{7,1},{1,2}});

    }

}
