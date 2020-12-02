package days;

import util.InputReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    // https://adventofcode.com/2020/day/2
    private Day2(String[] input) {
        ParsedEntry[] entries = parseInput(input);

        int answerA = 0;
        for (ParsedEntry e : entries) if (validateOldPolicy(e)) answerA++;
        int answerB = 0;
        for (ParsedEntry e : entries) if (validateNewPolicy(e)) answerB++;

        System.out.println(String.format("Answer for first part is: %s\n" +
                "Answer for second part is: %s", answerA, answerB));

    }

    private ParsedEntry[] parseInput(String[] input) {
        ParsedEntry[] parsedEntries = new ParsedEntry[input.length];
        for (int i = 0; i < parsedEntries.length; i++) {
            parsedEntries[i] = new ParsedEntry(input[i]);
        }
        return parsedEntries;
    }

    private boolean validateOldPolicy(ParsedEntry entry) { // Part A
        int count = entry.password.length() - entry.password.replace(entry.character, "").length();
        return entry.min <= count && count <= entry.max;
    }

    private boolean validateNewPolicy(ParsedEntry entry) { // Part B
        return Character.toString(entry.password.charAt(entry.min - 1)).equals(entry.character)
                ^ Character.toString(entry.password.charAt(entry.max - 1)).equals(entry.character);
    }

    private class ParsedEntry {
        int min;
        int max;
        String character;
        String password;

        public ParsedEntry(String entry) throws IllegalArgumentException {
            Matcher m = Pattern.compile("^([0-9]+)-([0-9]+)\\s([a-z]): ([a-z]*)$").matcher(entry);
            if (m.find()) {
                this.min = Integer.parseInt(m.group(1));
                this.max = Integer.parseInt(m.group(2));
                ;
                this.character = m.group(3);
                this.password = m.group(4);
            } else {
                throw new IllegalArgumentException("Entry does not match policy and password pattern!");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Day 2:");
        new Day2(InputReader.getLinesOfFile("input\\Day2.txt"));
    }
}
