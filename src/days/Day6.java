package days;

import util.InputReader;

import java.util.Arrays;

public class Day6 {

    private Day6(String[] input) {
        int totalA = Arrays.stream(toGroups(input)).mapToInt(this::anyoneInGroupAnsweredYes).sum();
        int totalB = Arrays.stream(toGroups(input)).mapToInt(this::everyoneInGroupAnsweredYes).sum();
        System.out.printf("Part A: The sum of anyone answers yes of each group is %s \n", totalA);
        System.out.printf("Part B: The sum of everyone answers yes of each group is %s \n", totalB);
    }

    private int anyoneInGroupAnsweredYes(String group) {
        int cnt = 0;
        for (int i = 'a'; i <= 'z'; i++) if (group.contains(String.valueOf(((char) i)))) cnt++;
//        System.out.printf("group: %s has %s anyone yes \n", group, cnt);
        return cnt;
    }

    private int everyoneInGroupAnsweredYes(String group) {
        int cnt = 0;
        int groupSize = group.split(",").length;
        for (int i = 'a'; i <= 'z'; i++) {
            if ((group.length() - group.replace(String.valueOf((char) i), "").length()) == groupSize) cnt++;
        }
//        System.out.printf("group: %s has %s everyone yes. groupsize %s \n", group, cnt, groupSize);
        return cnt;
    }

    private String[] toGroups(String[] input) {
        StringBuilder sb = new StringBuilder();
        for (String s : input) sb.append(s.equals("") ? ";" : s).append(",");
        return sb.append(";").toString().replace(";,", ";").replace(",;", ";").split(";");
    }

    public static void main(String[] args) {
        System.out.println("Day 6");
//        new Day6(new String[]{"abc", "", "a", "b", "c", "", "ab", "ac", "", "a", "a", "a", "a", "", "b"});
        new Day6(InputReader.getLinesOfFile("input\\Day6.txt"));
//        Part A: The sum of anyone answers yes of each group is 6443
//        Part B: The sum of everyone answers yes of each group is 3232
    }

}
