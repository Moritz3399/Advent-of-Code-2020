package util;

import java.util.ArrayList;
import java.util.Arrays;

public class EuclideanAlgorithmTest {


    private static long euclideanAlgorithm(long M, int busID){
        // M_i = M / m_i (busID)
        // Return e_i = s_i * M_i of 1 = r_i * m_i + s_i * M_i
        long M_i = M / busID;
        long[] lastEntry = new long[]{M_i, busID, 0L, 0L};
        ArrayList<long[]> entries = new ArrayList<>();
        entries.add(lastEntry);
        while (lastEntry[0] != 1L){
            lastEntry = new long[]{lastEntry[1], lastEntry[0] % lastEntry[1], 0L, 0L};
            entries.add(lastEntry);
        }
        lastEntry[2] = 1L;
        lastEntry[3] = 0L;
        for(int i = entries.size() - 2; i >= 0; i--){
            lastEntry = entries.get(i);
            lastEntry[2] = entries.get(i + 1)[3];
            lastEntry[3] = (1L - lastEntry[0] * lastEntry[2]) / lastEntry[1];
        }
        System.out.println(Arrays.toString(lastEntry));
        return lastEntry[2] * M_i;
    }

    public static void main(String[] args) {
//        System.out.print("expexted 494: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(4199L, 17));
//        System.out.print("expexted 1938: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(4199L, 13));
//        System.out.print("expexted 1768: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(4199L, 19));
//
//        // 7,13,x,x,59,x,31,19
//        // M = 3162341
//        System.out.print("expexted 903526: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(3162341, 7));
//        System.out.print("expexted 243257: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(3162341, 13));
        System.out.print("expexted : ");
        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(3162341, 59));

//        System.out.print("expexted 306033: ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(3162341, 31));
//
//        System.out.print("expexted : ");
//        System.out.println(EuclideanAlgorithmTest.euclideanAlgorithm(3162341, 19));

    }

}
