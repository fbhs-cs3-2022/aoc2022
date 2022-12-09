import java.util.*;
import java.io.*;


public class Day06 {

    public static void parts(int numChars) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        String dataStream = in.nextLine();

        outerLoop:
        for(int i = 0; i < dataStream.length()-numChars-1; i++) {
            String numWeAreChecking = dataStream.substring(i,i+numChars);
            char[] chars = numWeAreChecking.toCharArray();
            for(int p = 0; p < chars.length-1; p++) {
                for(int q = p+1; q < chars.length; q++) {
                    if (chars[p] == chars[q]) {
                        continue outerLoop;
                    }
                }
            }
            System.out.println(i+numChars);
            break;
        }
        
    }

       public static void main(String...args) {
        try {
            parts(14);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}