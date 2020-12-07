package days;

import util.InputReader;

import java.util.*;

public class Day7 {

    private final Bag[] bags;

    private Day7(String[] rules, String color) {
        this.bags = parseRules(rules);
        checkIfItCouldHoldBag(color);
        System.out.printf("The %s bag can be directly or indirectly stored in %s bags.\n",
                color, Arrays.stream(bags).mapToInt(b -> b.couldHoldXColoredBag ? 1 : 0).sum());
        System.out.printf("The %s bag can contain %s bags.\n", color, howManyOtherBagsCanBeInsideAXColoredBag(color));
    }

    private int howManyOtherBagsCanBeInsideAXColoredBag(String color){
        int cnt = 0;
        for(Bag b : bags){
            if(b.color.equals(color)){
                for (Map.Entry<String, Integer> e : b.holds.entrySet()) {
                    cnt+= howManyOtherBagsCanBeInsideAXColoredBag(e.getKey()) * e.getValue() + e.getValue();
                }
            }
        }
        return cnt;
    }

    private void checkIfItCouldHoldBag(String color) {
        for (Bag b : bags) {
            if (!b.couldHoldXColoredBag && b.howManyColoredBagsCanBeContained(color) > 0) {
                b.couldHoldXColoredBag = true;
                checkIfItCouldHoldBag(b.color);
            }
        }
    }


    private Bag[] parseRules(String[] rules) {
        ArrayList<Bag> bags = new ArrayList<>();
        for (String r : rules) bags.add(new Bag(r));
        return bags.toArray(new Bag[0]);
    }

    private static class Bag {

        final String color;
        Map<String, Integer> holds = new HashMap<>();
        boolean couldHoldXColoredBag = false;

        Bag(String rule) {
            this.color = rule.substring(0, rule.indexOf(" bags contain"));
            String contain = rule.substring(rule.indexOf("contain ") + 8);
            if (!"no other bags.".equals(contain)) {
                for (String b : contain.split(", ")) {
                    String s = b.substring(0, b.lastIndexOf(" "));
                    int n = Integer.parseInt(s.substring(0, s.indexOf(" ")));
                    String c = s.substring(s.indexOf(" ") + 1);
                    holds.put(c, n);
                }
            }
        }

        int howManyColoredBagsCanBeContained(String color) {
            return holds.getOrDefault(color, 0);
        }
    }

    public static void main(String[] args) {
        System.out.println("Day 7");
//        new Day7(InputReader.getLinesOfFile("input\\Day7.example.txt"), "shiny gold");
//        new Day7(InputReader.getLinesOfFile("input\\Day7.example2.txt"), "shiny gold");
        new Day7(InputReader.getLinesOfFile("input\\Day7.txt"), "shiny gold");
//        The shiny gold bag can be directly or indirectly stored in 332 bags.
//        The shiny gold bag can contain 10875 bags.
    }

}
