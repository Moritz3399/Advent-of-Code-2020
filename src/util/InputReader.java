package util;

import java.io.*;
import java.util.ArrayList;

public class InputReader {
    public static String[] getLinesOfFile(String path){
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File((path))));
            String line;
            while ((line = br.readLine())!= null ){
                lines.add(line);
            }
            return lines.toArray(new String[lines.size()]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
