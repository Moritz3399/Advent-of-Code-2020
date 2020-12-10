package days;

import util.InputReader;

import java.util.Arrays;
import java.util.OptionalLong;

public class Day9 {

    private long[] data;
    private int preambleLength;

    private Day9(String[] input, int preamleLength) {
        this.data = Arrays.stream(input).mapToLong(Long::parseLong).toArray();
        this.preambleLength = preamleLength;
        long firstInvalidNumber = firstInvalidNumber();
        System.out.printf("The first invalid number is %s\n", firstInvalidNumber);
        this.findEncryptionWeakness(firstInvalidNumber);
    }

    private void findEncryptionWeakness(long invalidNumber) {
        int rangeStart = 0;
        int rangeEnd = 0; // exclusive
        for (int i = 0; i < data.length; i++) {
            long sum = 0L;
            int j = i;
            while (sum < invalidNumber && j < data.length) {
                sum += data[j++];
            }
            if (sum == invalidNumber) {
                rangeEnd = j;
                rangeStart = i;
                break;
            }
        }
        System.out.printf("RangeStart %s RangeEnd %s\n", rangeStart, rangeEnd);
        if (rangeStart != rangeEnd) {
            long[] range = Arrays.copyOfRange(data, rangeStart, rangeEnd);
            System.out.println(Arrays.toString(range));
            OptionalLong min = Arrays.stream(range).min();
            OptionalLong max = Arrays.stream(range).max();
            System.out.printf("Min %s Max %s sum %s", min, max, min.getAsLong() + max.getAsLong());
        }
    }

    private Long firstInvalidNumber() {
        for (int i = preambleLength; i < data.length; i++) {
            if (validatePosition(i) == false) return data[i];
        }
        return null;
    }

    private boolean validatePosition(int position) {
        for (int i = position - preambleLength; i < position; i++) {
            for (int j = i + 1; j < position; j++) {
                if (i != j && data[i] + data[j] == data[position]) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Day 9");
//        new Day9(new String[]{"35","20","15","25","47","40","62","55","65","95","102","117","150","182","127","219","299","277","309","576"}, 5);
        new Day9(InputReader.getLinesOfFile("input\\Day9.txt"), 25);
//        Day 9
//        The first invalid number is 104054607
//        RangeStart 444 RangeEnd 461
//                [4574403, 4816749, 4623109, 4682251, 4826310, 5807050, 4960808, 5056850, 5250440, 8422030, 5908297, 6629113, 6764845, 7219312, 9361394, 7897849, 7253797]
//        Min OptionalLong[4574403] Max OptionalLong[9361394] sum 13935797
    }

}
