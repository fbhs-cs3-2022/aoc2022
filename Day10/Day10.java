import java.util.*;
import java.io.*;

public class Day10 {

    public static int checkCycle(int cycle, int x) {
        if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
            System.out.println("Cycle: " + cycle + ", x: " + x + " Strength: " + (cycle * x));
            return cycle * x;
        }
        return 0;
    }

    public static void part1() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        int x = 1;
        int cycle = 1;
        int strength = 0;
        while (in.hasNext()) {
            String[] line = in.nextLine().split(" ");
            if (line[0].equals("noop")) {
                cycle++;
                strength += checkCycle(cycle, x);
            } else {
                int addAmt = Integer.parseInt(line[1]);
                cycle++;
                strength += checkCycle(cycle, x);
                cycle++;
                x += addAmt;
                strength += checkCycle(cycle, x);

            }

        }
        System.out.println(strength);
    }

    public static void part2() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        int x = 1;
        String row = "";
    
        int crtPos = row.length();
        if (crtPos == x - 1 || crtPos == x || crtPos == x + 1) {
            row += "#";
        } else {
            row += ".";
        }
        

        while (in.hasNext()) {

            String[] line = in.nextLine().split(" ");

            if (line[0].equals("noop")) {
                crtPos = row.length();
                if (crtPos == x - 1 || crtPos == x || crtPos == x + 1) {
                    row += "#";
                } else {
                    row += ".";
                }
                crtPos = row.length();
                if (crtPos == 40) {
                    System.out.println(row);
                    row = "";
                    crtPos = 0;
                }
            } else {
                crtPos = row.length();
                int addAmt = Integer.parseInt(line[1]);
                if (crtPos == x - 1 || crtPos == x || crtPos == x + 1) {
                    row += "#";
                } else {
                    row += ".";
                }
                crtPos = row.length();
                if (crtPos == 40) {
                    System.out.println(row);
                    row = "";
                    crtPos = 0;
                }
                x += addAmt;
                if (crtPos == x - 1 || crtPos == x || crtPos == x + 1) {
                    row += "#";
                } else {
                    row += ".";
                }
                crtPos = row.length();
                if (crtPos == 40) {
                    System.out.println(row);
                    row = "";
                    crtPos = 0;
                }

            }

        }
    }

    public static void main(String... args) {
        try {
            part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}