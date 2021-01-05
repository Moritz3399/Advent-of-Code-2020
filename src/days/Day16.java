package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day16 {

    TicketRule[] rules;
    int[] yourTicket;
    int[][] nearbyTickets;
    int[][] validTickets;
    HashMap<String, Integer> mapping;

    private Day16(String[] input) {
        this.parseInput(input);
        int error = this.ticketScanningErrorRate();
        System.out.println("The ticket scanning error rate is " + error);
        this.filterForValidTickets();
        // print valid tickets
//        for (int[] ticket : validTickets) System.out.println(Arrays.toString(ticket));
        this.mapping = this.mapRulesToColumns();
        System.out.println("Column Mapping: " + mapping.toString());
        System.out.println("The product of all departure fields is: " + this.multiplyDepartureValues());
    }

    private long multiplyDepartureValues() {
        long product = 1L;
        for (Map.Entry<String, Integer> e : mapping.entrySet()) {
            if (e.getKey().startsWith("departure")) product *= yourTicket[e.getValue()];
        }
        return product;
    }

    private HashMap<String, Integer> mapRulesToColumns() {
        String[] columnsPossibleRules = new String[yourTicket.length];

        for (int column = 0; column < yourTicket.length; column++) {
            StringBuilder possibleRules = new StringBuilder();
            looprules:
            for (TicketRule rule : rules) {
                for (int[] ticket : validTickets) {
                    if (!rule.validate(ticket[column])) continue looprules;
                }
                possibleRules.append(rule.name);
                possibleRules.append(",");
            }
            // trim last comma
            columnsPossibleRules[column] = possibleRules.substring(0, possibleRules.length() - 1);
        }

//        for (String s : columnsPossibleRules) System.out.println(s);
        HashMap<String, Integer> columnMapping = new HashMap<>();
        while (columnMapping.size() != yourTicket.length) {
            for (int i = 0; i < columnsPossibleRules.length; i++) {
                if (columnsPossibleRules[i].length() != 0 && !columnsPossibleRules[i].contains(",")) {
                    columnMapping.put(columnsPossibleRules[i], i);
                    String toRemove = columnsPossibleRules[i];
                    for (int j = 0; j < columnsPossibleRules.length; j++) {
                        columnsPossibleRules[j] = columnsPossibleRules[j].replace(toRemove, "");
                        columnsPossibleRules[j] = columnsPossibleRules[j].replace(",,", ",");
                        if(columnsPossibleRules[j].length() == 0) continue;
                        if (columnsPossibleRules[j].charAt(0) == ',')
                            columnsPossibleRules[j] = columnsPossibleRules[j].substring(1);
                        if (columnsPossibleRules[j].endsWith(","))
                            columnsPossibleRules[j] = columnsPossibleRules[j].substring(0, columnsPossibleRules[j].length() - 1);
                    }
                }
            }
        }
        return columnMapping;
    }

    private void filterForValidTickets() {
        ArrayList<int[]> validTickets = new ArrayList<>();
        outer:
        for (int[] ticket : nearbyTickets) {
            for (int value : ticket) if (!anyValidRule(value)) continue outer;
            validTickets.add(ticket);
        }
        this.validTickets = validTickets.toArray(new int[0][0]);
    }


    private int ticketScanningErrorRate() {
        int error = 0;
        for (int[] ticket : nearbyTickets) {
            for (int n : ticket) if (!anyValidRule(n)) error += n;
        }
        return error;
    }

    private boolean anyValidRule(int value) {
        for (TicketRule rule : rules) {
            if (rule.validate(value)) return true;
        }
        return false;
    }


    private void parseInput(String[] input) {
        int i = 0;
        ArrayList<TicketRule> rules = new ArrayList<>();
        while (i < input.length) {
            if (input[i].equals("")) break;
            rules.add(new TicketRule(input[i]));
            i++;
        }
        this.rules = rules.toArray(new TicketRule[0]);
        i += 2;
        this.yourTicket = Arrays.stream(input[i].split(",")).mapToInt(Integer::parseInt).toArray();
//        System.out.println(Arrays.toString(yourTicket));
        i += 3;
        ArrayList<int[]> nearbyTickets = new ArrayList<>();
        while (i < input.length) {
            nearbyTickets.add(Arrays.stream(input[i].split(",")).mapToInt(Integer::parseInt).toArray());
//            System.out.println(Arrays.toString(nearbyTickets.get(nearbyTickets.size()-1)));
            i++;
        }
        this.nearbyTickets = nearbyTickets.toArray(new int[0][0]);


    }

    private static class TicketRule {
        String name;
        int min1;
        int max1;
        int min2;
        int max2;

        TicketRule(String rule) {
            String[] nameAndBoundaries = rule.split(": ");
            name = nameAndBoundaries[0];
            String[] boundaries = nameAndBoundaries[1].split(" or ");
            String[] one = boundaries[0].split("-");
            min1 = Integer.parseInt(one[0]);
            max1 = Integer.parseInt(one[1]);
            String[] two = boundaries[1].split("-");
            min2 = Integer.parseInt(two[0]);
            max2 = Integer.parseInt(two[1]);
//            System.out.println(toString());
        }

        boolean validate(int value) {
            return (min1 <= value && value <= max1) || (min2 <= value && value <= max2);
        }

        public String toString() {
            return String.format("%s: %s-%s or %s-%s", name, min1, max1, min2, max2);
        }
    }


    public static void main(String[] args) {
        System.out.println("Day 16");
//        new Day16(InputReader.getLinesOfFile("input\\Day16.Example.txt")); // => 71
        new Day16(InputReader.getLinesOfFile("input\\Day16.txt"));
//        new Day16(InputReader.getLinesOfFile("input\\Day16.Example2.txt"));

//        Day 16
//        The ticket scanning error rate is 23954
//        Column Mapping: {arrival location=12, wagon=5, arrival station=6, type=17, arrival track=18, duration=13,
//        seat=4, route=19, departure track=7, departure time=0, zone=2, price=15, departure location=1,
//        arrival platform=16, departure platform=8, departure station=3, row=9, departure date=14, class=11, train=10}
//        The product of all departure fields is: 453459307723

    }
}
