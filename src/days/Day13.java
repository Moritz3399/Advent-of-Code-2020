package days;

import util.InputReader;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Day13 {

    private int earliestDeparture;
    private int depature;
    private int[] busIDsReduced;

    private Day13(String earliestDeparture, String busIDs) {
        this.earliestDeparture = Integer.parseInt(earliestDeparture);
        this.depature = this.earliestDeparture;
        this.busIDsReduced = parseBusIDs(busIDs);
        int bus = findFirstBus();
        System.out.printf("Part A: The first possible bus departs at %s and is bus id %s => Answer: %s \n", depature, bus, (depature - this.earliestDeparture) * bus);

        this.findFirstTimestampBussesDepartingByOffset(busIDs);

    }

    private int[] parseBusIDs(String busIDs) {
        String reduced = busIDs.replace("x,", "").replace("x", "");
        return Arrays.stream(reduced.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private int findFirstBus() {
        while (true) {
            for (int id : busIDsReduced) if (depature % id == 0) return id;
            depature++;
        }
    }

    private void findFirstTimestampBussesDepartingByOffset(String busses) {

//        https://www.math.uni-bielefeld.de/~pwegener/ChinesischerRestsatz.pdf
//        https://de.wikipedia.org/wiki/Chinesischer_Restsatz

        int[] busIDs = Arrays.stream(busses.split(",")).mapToInt(n -> n.equals("x") ? -1 : Integer.parseInt(n)).toArray();
        long productIDs = 1L; // M
        for (int i : busIDs) if (i != -1) productIDs *= i;
        long simultaneousCongruence = 0L;
        for (int i = 1; i < busIDs.length; i++) {
            if (busIDs[i] == -1) continue;
            simultaneousCongruence += euclideanAlgorithm(productIDs, busIDs[i]) * (((long) busIDs[i]) - ((long) i));
        }
        if (simultaneousCongruence >= 0)
            System.out.printf("Part B: First timestamp this happens is %s \n", simultaneousCongruence % productIDs);
        else {
            while (simultaneousCongruence < 0) simultaneousCongruence += productIDs;
            System.out.printf("Part B: First timestamp this happens is %s \n", simultaneousCongruence);
        }

    }

    private long euclideanAlgorithm(long M, int busID) {
        // M_i = M / m_i (busID)
        // Return e_i = s_i * M_i of 1 = r_i * m_i + s_i * M_i
        long M_i = M / busID;
        long[] lastEntry = new long[]{M_i, busID, 0L, 0L};
        ArrayList<long[]> entries = new ArrayList<>();
        entries.add(lastEntry);
        while (lastEntry[0] != 1L) {
            lastEntry = new long[]{lastEntry[1], lastEntry[0] % lastEntry[1], 0L, 0L};
            entries.add(lastEntry);
        }
        lastEntry[2] = 1L;
        lastEntry[3] = 0L;
        for (int i = entries.size() - 2; i >= 0; i--) {
            lastEntry = entries.get(i);
            lastEntry[2] = entries.get(i + 1)[3];
            lastEntry[3] = (1L - lastEntry[0] * lastEntry[2]) / lastEntry[1];
        }
        return lastEntry[2] * M_i;
    }


    public static void main(String[] args) {
        System.out.println("Day 13");
        System.out.println(LocalTime.now().toString());
        long start = System.currentTimeMillis();

//        String earliestDeparture = "939";
//        String busIDs = "7,13,x,x,59,x,31,19";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 1068781");
//        System.out.println("");
//
//        earliestDeparture = "0";
//        busIDs = "17,x,13,19";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 3417");
//        System.out.println("");
//
//        earliestDeparture = "0";
//        busIDs = "67,7,59,61,x";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 754018");
//        System.out.println("");
//
//        earliestDeparture = "0";
//        busIDs = "67,x,7,59,61";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 779210");
//        System.out.println("");
//
//        earliestDeparture = "0";
//        busIDs = "67,7,x,59,61";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 1261476");
//        System.out.println("");
//
//        earliestDeparture = "0";
//        busIDs = "1789,37,47,1889,x";
//        new Day13(earliestDeparture, busIDs);
//        System.out.println("Expected 1202161486");
//        System.out.println("");
//        System.out.println(LocalTime.now().toString());


        String[] input = InputReader.getLinesOfFile("input\\Day13.txt");
        new Day13(input[0], input[1]);


        System.out.println(LocalTime.now().toString());
        System.out.println("Total runtime: " + (System.currentTimeMillis() - start));


    }
}
