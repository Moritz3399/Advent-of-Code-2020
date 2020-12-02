package days;

import util.InputReader;

public class Day1A {

    // Classic solution with nested loops
    public static void main(String[] args) {
        String[] inputStr = InputReader.getLinesOfFile("input\\Day1.txt");
        int[] input = new int[inputStr.length];
        for (int i = 0; i < input.length; i++) {
            input[i] = Integer.parseInt(inputStr[i]);
        }
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (i == j) continue;
                if (input[i] + input[j] == 2020){
                    System.out.println("Answer: " + (input[i] * input[j])
                            + " with " + input[i] + " (" + i + ") and " + input[j] + " (" + j + ")");
                    return;
                }
            }
        }
    }


}
