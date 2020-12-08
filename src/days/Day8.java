package days;

import util.InputReader;

public class Day8 {

    private int accumulator = 0;
    private int pointer = 0;
    private final String[] instructions;
    private final boolean[] executedInstructions;
    private String terminatedBy = "";

    private Day8(String[] input) {
        this.instructions = input;
        this.executedInstructions = new boolean[input.length];
        this.executeInstruction();
        System.out.printf("The accumulator value on halt is %s and the program was halted by %s \n", accumulator, terminatedBy);
    }

    private void executeInstruction() {
        if (executedInstructions[pointer]) {
            this.terminatedBy = "duplicate";
            return;
        }
        executedInstructions[pointer] = true;
        String in = instructions[pointer];

        switch (in.substring(0, 3)) {
            case "acc":
                accumulator += Integer.parseInt(in.substring(4));
                pointer++;
                break;
            case "jmp":
                pointer += Integer.parseInt(in.substring(4));
                break;
            case "nop":
                pointer++;
                break;
        }
        if (pointer >= instructions.length) {
            this.terminatedBy = "default";
            return;
        }
        this.executeInstruction();
    }

    public String getTerminatedBy() {
        return terminatedBy;
    }

    public static void main(String[] args) {
        System.out.println("Day 8");
//        new Day8(new String[]{"nop +0", "acc +1", "jmp +4", "acc +3", "jmp -3", "acc -99", "acc +1", "jmp -4", "acc +6"});
        String[] input = InputReader.getLinesOfFile("input\\day8.txt");
//        String[] input = new String[]{"nop +0", "acc +1", "jmp +4", "acc +3", "jmp -3", "acc -99", "acc +1", "jmp -4", "acc +6"};
        System.out.println("Part A:");
        new Day8(input);
        System.out.println("Part B:");
        loop:
        for (int i = 0; i < input.length; i++) {
            String[] tmp = new String[input.length];
            System.arraycopy(input, 0, tmp, 0, input.length);
            switch (input[i].substring(0, 3)) {
                case "acc":
                    continue;
                case "jmp":
                    tmp[i] = tmp[i].replace("jmp", "nop");
                    Day8 d = new Day8(tmp);
                    if(d.getTerminatedBy().equals("default")){
                        System.out.printf("The program terminated normally when you change position %s and the accumulator value is %s \n", i, d.accumulator);
                        break loop;
                    }
                    break;
                case "nop":
                    tmp[i] = tmp[i].replace("nop", "jmp");
                    Day8 e = new Day8(tmp);
                    if(e.getTerminatedBy().equals("default")){
                        System.out.printf("The program terminated normally when you change position %s and the accumulator value is %s \n", i, e.accumulator);
                        break loop;
                    }
                    break;
            }
        }
    }
}
