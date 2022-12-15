import java.util.*;
import java.io.*;


public class Day12 {

    public static class Spot {
        int row;
        int col;
        boolean visited;
        boolean isStart;
        boolean isEnd;
        char height;
        ArrayList<Spot> neighbors;
        Spot prev;
        int g = 0;
        int f = Integer.MAX_VALUE;

        public Spot(int r, int c, char h) {
            row = r;
            col = c;
            if(h == 'S') {
                isStart = true;
                height = 'a';
            } else if(h == 'E') {
                isEnd = true;
                height = 'z';
            } else {
                height = h;
            }
            visited = false;
            neighbors = new ArrayList<Spot>();
            prev = null;
        }

        public String toString() {
            return "" + height;
        }

        public void getNeighbors(Spot[][] map) {
            if(row > 0) {
                Spot up = map[row-1][col];
                if(up.height - this.height <= 1)
                    neighbors.add(up);
            }
            if(row < map.length-1) {
                Spot down = map[row+1][col];
                if(down.height - this.height <= 1)
                    neighbors.add(down);
            }

            if(col > 0) {
                Spot left = map[row][col-1];
                if(left.height - this.height <= 1) 
                    neighbors.add(left);
            }
                

            if(col < map[0].length-1) {
                Spot right = map[row][col+1];
                if(right.height - this.height <= 1)
                    neighbors.add(right);
            }
        }


    }

    public static void printMap(Spot[][] map) {
        for(Spot[] row : map) {
            for(Spot s : row) {
                System.out.println(s.neighbors);
            }
            System.out.println();
        }
    }

    public static int findDist(Spot c, Spot end) {
        if(c == end)
            return 0;
        return (c.height - end.height) ;
    }

    public static void part1() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        
        ArrayList<String> rawMap = new ArrayList<String>();
        Spot end = null;
        while(in.hasNext()) {
            rawMap.add(in.nextLine());
        }

        Spot[][] map = new Spot[rawMap.size()][rawMap.get(0).length()];
        for(int i = 0; i < rawMap.size(); i++) {
            for(int j = 0; j < rawMap.get(i).length(); j++) {
                map[i][j] = new Spot(i, j, rawMap.get(i).charAt(j));
            }
        }

        

        for(Spot[] spots : map) {
            for(Spot spot : spots) {
                spot.getNeighbors(map);
            }
        }

        //printMap(map);

        HashSet<Spot> open = new HashSet<Spot>();
        HashSet<Spot> closed = new HashSet<Spot>();
        

        // find start
        for(int r = 0; r < map.length; r++) {
            for(int c = 0; c < map[0].length; c++) {
                if(map[r][c].isStart) {
                    System.out.println("Starting at: " + r + " " + c);
                    open.add(map[r][c]);
                }

                if(map[r][c].isEnd) {
                    end = map[r][c];
                }
            }
        }

        while(!open.isEmpty()) {
            //System.out.println(open);
            Spot closest = null;
            for(Spot s: open) {
                if(closest == null) {
                    closest = s;
                } else if(findDist(s, end) < findDist(closest, end)) {
                    closest = s;
                }
            }

            open.remove(closest);

            for(Spot n : closest.neighbors) {
                if(closed.contains(n))
                    continue;
                n.prev = closest;

                if(n.isEnd) {
                    System.out.println("FOUND SOLUTION");
                    retrace(n);
                    //return;
                }

                n.g = closest.g - findDist(closest,n);
                n.f = findDist(n, end) + n.g;

                boolean skip = false;

                for(Spot m : open) {
                    if(m.row == n.row && m.col == n.col && m.f < n.f) {
                        skip = true;
                        break;
                    }
                }

                if(!skip) {
                    for(Spot m: closed) {
                        if(m.row == n.row && m.col == n.col && m.f < n.f) {
                            skip = true;
                            break;
                        }
                    }
                }

                if(!skip) {
                    open.add(n);
                }
            }

            closed.add(closest);
            
        }
    }

    public static void retrace(Spot end) {
        Spot curr = end;
        int steps = 0;
        while(!curr.isStart) {
            steps++;
            curr = curr.prev;
        }
        System.out.println("Steps: " + steps);
    }

    public static void part2() throws IOException {

    }

    public static void main(String...args) {
        try {
            part1();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}