package days;

import util.InputReader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    private final Pattern patternMask = Pattern.compile("mask = ([X10]+)");
    private final Pattern patternMemory = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");
    private final HashMap<Long, Long> memory = new HashMap<>();

    private Day14(String[] input, boolean isV2) {
        if (!isV2) { // Part A
            this.initMemory(input);
        } else { // Part B
            this.initMemoryVersion2(input);
        }
        long sum = 0L;
        for (Map.Entry<Long, Long> e : memory.entrySet()) sum += e.getValue();
        System.out.printf("Part %s: The sum of all values in memory is %s\n", isV2 ? "B" : "A", sum);
    }

    private void initMemory(String[] program) {
        String mask;
        long maskReplaceWithOne = 0L;
        long maskReplaceWithZero = 0L;
        for (String command : program) {
            Matcher matcherMask = patternMask.matcher(command);
            if (matcherMask.find()) {
                mask = matcherMask.group(1);
                maskReplaceWithOne = Long.parseUnsignedLong(mask.replace("X", "0"), 2);
                maskReplaceWithZero = Long.parseUnsignedLong(mask.replace("X", "1"), 2);
                continue;
            }
            Matcher matcherMemory = patternMemory.matcher(command);
            if (matcherMemory.find()) {
                long index = Long.parseUnsignedLong(matcherMemory.group(1));
                long initValue = Long.parseUnsignedLong(matcherMemory.group(2), 10);
                long value = (initValue & maskReplaceWithZero) | maskReplaceWithOne;
                this.memory.put(index, value);
                continue;
            }
            System.err.printf("Command '%s' did not match a mask or memory command. \n", command);
        }
    }

    private void initMemoryVersion2(String[] program) {
        String mask = "";
        for (String command : program) {
            Matcher matcherMask = patternMask.matcher(command);
            if (matcherMask.find()) {
                mask = matcherMask.group(1);
                continue;
            }
            Matcher matcherMemory = patternMemory.matcher(command);
            if (matcherMemory.find()) {
                long index = Long.parseUnsignedLong(matcherMemory.group(1), 10);
                long value = Long.parseUnsignedLong(matcherMemory.group(2), 10);
                String indexStr = Long.toBinaryString(index);
                while (indexStr.length() < mask.length()) indexStr = "0" + indexStr;
                char[] indexChars = indexStr.toCharArray();
                for (int i = 0; i < mask.length(); i++) {
                    if (mask.charAt(i) == '0') continue;
                    indexChars[i] = mask.charAt(i);
                }
                this.writeValuesToMemory(value, new String(indexChars));
                continue;
            }
            System.err.printf("Command '%s' did not match a mask or memory command. \n", command);
        }
    }

    private void writeValuesToMemory(long value, String index) {
        int x = index.indexOf("X");
        if (x == -1) { // not found => index is final
            memory.put(Long.parseUnsignedLong(index, 2), value);
        } else {
            this.writeValuesToMemory(value, index.replaceFirst("X", "0"));
            this.writeValuesToMemory(value, index.replaceFirst("X", "1"));
        }
    }

    public static void main(String[] args) {
        System.out.println("Day 14");
//        String[] example = {"mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", "mem[8] = 11", "mem[7] = 101", "mem[8] = 0"};
//        new Day14(example, false);

//        String[] example2 = {"mask = 000000000000000000000000000000X1001X", "mem[42] = 100",
//                "mask = 00000000000000000000000000000000X0XX", "mem[26] = 1"};
//        new Day14(example2, true);

        new Day14(InputReader.getLinesOfFile("input\\Day14.txt"), false);
        new Day14(InputReader.getLinesOfFile("input\\Day14.txt"), true);
//        Day 14
//        Part A: The sum of all values in memory is 15018100062885
//        Part B: The sum of all values in memory is 5724245857696
    }
}
