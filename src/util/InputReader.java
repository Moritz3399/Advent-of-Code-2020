package util;

import java.io.*;
import java.util.ArrayList;

public class InputReader {
    public static String[] getLinesOfFile(String path){
        ArrayList<String> lines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = br.readLine())!= null ){
                lines.add(line);
            }
            return lines.toArray(new String[0]);

        } catch (IOException e) {
            System.err.println("File does not exist: " + path);
        }
        return new String[0];
    }
}
