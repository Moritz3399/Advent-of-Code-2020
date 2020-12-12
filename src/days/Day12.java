package days;

import util.InputReader;

public class Day12 {

    private int north = 0;
    private int east = 0;
    private Direction currentDirection = Direction.EAST;
    private int wayPointNorth = 1;
    private int wayPointEast = 10;

    private Day12(String[] input, boolean withWayPoints) {
        if (!withWayPoints) {
            for (String command : input) {
                steerTheBoat(command);
            }
        } else {
            for (String command : input) {
                steerTheBoatByWayPoints(command);
            }
        }
        System.out.printf("The boat is at location %s%s %s%s and the Manhattan distance is %s.\n",
                Math.abs(north), north < 0 ? "S" : "N",
                Math.abs(east), east < 0 ? "W" : "E",
                Math.abs(north) + Math.abs(east));
    }

    private void steerTheBoatByWayPoints(String command) {
        String key = command.substring(0, 1);
        int value = Integer.parseInt(command.substring(1));
        switch (key) {
            case "R":
                for(int i = 0; i<value/90; i++){
                    int tmp = wayPointEast;
                    wayPointEast = wayPointNorth;
                    wayPointNorth = - tmp;
                }
                break;
            case "L":
                for(int i = 0; i<value/90; i++){
                    int tmp = wayPointNorth;
                    wayPointNorth = wayPointEast;
                    wayPointEast = - tmp;
                }
                break;
            case "F":
                north+=value*wayPointNorth;
                east+=value*wayPointEast;
                break;
            case "N":
                wayPointNorth+=value;
                break;
            case "E":
                wayPointEast+=value;
                break;
            case "S":
                wayPointNorth-=value;
                break;
            case "W":
                wayPointEast-=value;
                break;
        }
    }

    private void steerTheBoat(String command) {
        String key = command.substring(0, 1);
        int value = Integer.parseInt(command.substring(1));
        switch (key) {
            case "R":
                rotateBoat(value);
                break;
            case "L":
                rotateBoat(360 - value);
                break;
            case "F":
                if (currentDirection == Direction.NORTH) north += value;
                else if (currentDirection == Direction.EAST) east += value;
                else if (currentDirection == Direction.SOUTH) north -= value;
                else if (currentDirection == Direction.WEST) east -= value;
                break;
            case "N":
                north += value;
                break;
            case "E":
                east += value;
                break;
            case "S":
                north -= value;
                break;
            case "W":
                east -= value;
                break;
        }
    }

    private void rotateBoat(int degrees) {
        currentDirection = Direction.values()[(currentDirection.ordinal() + (degrees / 90)) % 4];
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    public static void main(String[] args) {
        System.out.println("Day 12");
//        new Day12(new String[]{"F10", "N3", "F7", "R90", "F11"},false);
//        new Day12(new String[]{"F10", "N3", "F7", "R90", "F11"},true);
        System.out.print("Part A: ");
        new Day12(InputReader.getLinesOfFile("input\\Day12.txt"), false);
        System.out.print("Part B: ");
        new Day12(InputReader.getLinesOfFile("input\\Day12.txt"), true);
//        Day 12
//        Part A: The boat is at location 386S 29W and the Manhattan distance is 415.
//        Part B: The boat is at location 4084S 25317E and the Manhattan distance is 29401.
    }

}
