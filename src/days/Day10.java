package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.Arrays;

public class Day10 {

    private int[] adapters;
    private long validArrangements = 1L;


    private Day10(String[] input) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (String s : input) tmp.add(Integer.parseInt(s));
        tmp.add(0);
        tmp.sort(Integer::compareTo);
        tmp.add(tmp.get(tmp.size() - 1) + 3);
        adapters = Arrays.stream(tmp.toArray(new Integer[input.length + 2])).mapToInt(Integer::intValue).toArray();
//        System.out.println(Arrays.toString(adapters));
        this.countDifferences();
        this.countArrangements();
        System.out.printf("Possible valid arangements are %s", validArrangements);
    }

    private void countDifferences() {
        int one = 0;
        int two = 0;
        int three = 0;
        for (int i = 1; i < adapters.length; i++) {
            switch (adapters[i] - adapters[i - 1]) {
                case 1:
                    one++;
                    break;
                case 2:
                    two++;
                    break;
                case 3:
                    three++;
                    break;
                default:
                    System.err.printf("Invalid difference at i = %s \n", i);
            }
        }
        System.out.printf("Differences: 1 %s, 2: %s, 3: 3 %s\n", one, two, three);
        System.out.printf("Answer %s\n", one * three);
    }

    private Integer[] findOptionsForAdatapter(int adapter) {
        ArrayList<Integer> options = new ArrayList<>();
        int i = adapter + 1;
        while (i < adapters.length) {
            if (adapters[i] - adapters[adapter] <= 3) {
                options.add(i);
                i++;
            } else break;
        }
        return options.toArray(new Integer[0]);
    }

    private void countArrangements() {
        int partRangeStart = -1;
        for(int i = 0; i< adapters.length - 1; i++){
            Integer[] op = findOptionsForAdatapter(i);
            if(op.length > 1 && partRangeStart == -1) partRangeStart = i;
            if(op.length == 1 && partRangeStart != -1) {
                validArrangements*=countPaths(partRangeStart, i+1);
                partRangeStart = -1;
            }
        }
    }

    private int countPaths(int adapter, int rangeEnd){
        if(adapter == rangeEnd) return 1;
        int p = 0;
        for (int a : findOptionsForAdatapter(adapter)) p+= countPaths(a, rangeEnd);
        return p;
    }


    public static void main(String[] args) {
        System.out.println("Day 10");
//        new Day10(new String[]{"16", "10", "15", "5", "1", "11", "7", "19", "6", "12", "4"});
//        new Day10(new String[]{"28","33","18","42","31","14","46","20","48","47","24","23","49","45","19","38","39","11","1","32","25","35","8","17","7","9","4","2","34","10","3"});
        new Day10(InputReader.getLinesOfFile("input\\Day10.txt"));
    }

}
