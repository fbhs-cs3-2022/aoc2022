import java.util.*;
import java.io.*;

public class Day09 {

    private static class Knot  {
        int x;
        int y;

        public Knot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object other) {

            Knot o = (Knot)other;
            return this.x == o.x && this.y == o.y;
        }

        public int hashCode() {
            return this.x*31 + this.y;
        }

        public void moveUp() {
            this.y++;
        }

        public void moveDown() {
            this.y--;
        }

        public void moveLeft() {
            this.x--;
        }

        public void moveRight() {
            this.x++;
        }

        public void move(String d) {
            if (d.equals("U"))
                moveUp();
            else if (d.equals("D"))
                moveDown();
            else if (d.equals("R"))
                moveRight();
            else if (d.equals("L"))
                moveLeft();
            else
                System.out.println("ERROR IN DIRECTION");
        }

        public int[] distFrom(Knot other) {
            int[] result = new int[2];
            result[0] = this.x - other.x; // if positive other is to the left
            result[1] = this.y - other.y; // if positive other is below
            return result;
        }

        public String toString() {
            return x + " " + y;
        }
    }

    public static void updateTail(Knot head, Knot tail, Set<Knot> locations, boolean addLoc) {
        int[] dist = head.distFrom(tail);

        // still close enough
        if (Math.abs(dist[0]) < 2 && Math.abs(dist[1]) < 2) {
            return;
        }

        // tail is too far left
        if (dist[0] == 2 && dist[1] == 0) {
            tail.moveRight();
        }
        // tail is too far right
        else if (dist[0] == -2 && dist[1] == 0) {
            tail.moveLeft();
        }
        // tail is too far down
        else if (dist[0] == 0 && dist[1] == 2) {
            tail.moveUp();
        }
        // tail is too far up
        else if (dist[0] == 0 && dist[1] == -2) {
            tail.moveDown();
        }
        // tail is left and below
        else if (dist[0] == 2 && dist[1] == 1 || dist[0] == 1 && dist[1] == 2 || dist[0] == 2 && dist[1] == 2) {
            tail.moveRight();
            tail.moveUp();
        }
        // tail is left and above
        else if (dist[0] == 2 && dist[1] == -1 || dist[0] == 1 && dist[1] == -2 || dist[0] == 2 && dist[1] == -2) {
            tail.moveRight();
            tail.moveDown();
        }
        // tail is right and below
        else if (dist[0] == -2 && dist[1] == 1 || dist[0] == -1 && dist[1] == 2 || dist[0] == -2 && dist[1] == 2) {
            tail.moveLeft();
            tail.moveUp();
        }
        // tail is right and above
        else if (dist[0] == -2 && dist[1] == -1 || dist[0] == -1 && dist[1] == -2 || dist[0] == -2 && dist[1] == -2) {
            tail.moveLeft();
            tail.moveDown();
        }         
        else {
            throw new RuntimeException("This shouldn't happen!: " + Arrays.toString(dist));
        
        }
        
        if(addLoc)
            locations.add(new Knot(tail.x, tail.y));

    }

    public static void part1() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        Knot head = new Knot(0, 0);
        Knot tail = new Knot(0, 0);
        HashSet<Knot> locations = new HashSet<Knot>();
        locations.add(new Knot(tail.x, tail.y));
        while (in.hasNext()) {
            String[] instructions = in.nextLine().split(" ");
            String dir = instructions[0];
            int dist = Integer.parseInt(instructions[1]);
            for (int i = 0; i < dist; i++) {
                head.move(dir);
                try {
                    updateTail(head, tail, locations, true);
                }
                catch(Exception e) {
                    System.out.println(e);
                    return;
                }
            }
        }
        System.out.println(locations.size());
    }

    public static void part2() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        Knot head = new Knot(0, 0);
        ArrayList<Knot> tails = new ArrayList<Knot>();
        for(int i = 0; i < 9; i++){
            tails.add(new Knot(0,0));
        }
        HashSet<Knot> locations = new HashSet<Knot>();
        locations.add(new Knot(0,0));

        while (in.hasNext()) {
            String[] instructions = in.nextLine().split(" ");
            String dir = instructions[0];
            int dist = Integer.parseInt(instructions[1]);
            for (int i = 0; i < dist; i++) {
                head.move(dir);
                for(int j = 0; j < 9; j++) {
                    boolean addLoc = (j == 8);
                    if(j==0) {
                        try {
                            updateTail(head,tails.get(0),locations,false);
                        } catch(Exception e) {
                            System.out.println(e);
                            return;
                        }
                    } else {
                        try {
                            updateTail(tails.get(j-1), tails.get(j), locations, addLoc);
                        } catch(Exception e) {
                            System.out.println(e);
                            return;
                        }
                    }
                }
                
            }
        }
        //System.out.println(locations);
        System.out.println(locations.size());
    }

    public static void main(String... args) {
        try {
            part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}