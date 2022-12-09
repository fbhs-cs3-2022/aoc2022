import java.util.*;
import java.io.*;


public class Day07 {

    private static class Directory {
        private String name;
        private Directory parent;
        private Map<String, Integer> contents;
        private Set<Directory> subDirectories;



        public Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
            contents = new HashMap<String,Integer>();
            subDirectories = new HashSet<Directory>();
        }

        public int getSize() {
            int size = 0;
            for(String c : contents.keySet()) {
                size += contents.get(c);
            }

            for(Directory sd : subDirectories) {
                size += sd.getSize();
            }
            return size;
        }

        public void addContent(String content) {
            String[] stuff = content.split(" ");
            contents.put(stuff[1], Integer.parseInt(stuff[0]));
        }

        public Directory addSub(String dirName, Directory parent) {
            for(Directory d : subDirectories) {
                if(d.name.equals(dirName)) {
                    return d;
                }
            }
            
            Directory newDir = new Directory(dirName, parent);
            subDirectories.add(newDir);
            return newDir;
            
        }

        public String toString() {
            String result = name;
            result += ": " + contents + " " + subDirectories;
            return result;
        }
    }



    public static void part1() throws IOException {

        Directory current = new Directory("/", null);

        Scanner in = new Scanner(new File("input.txt"));
        
        
        while(in.hasNext()) {
            String line = in.nextLine();
            //System.out.println(current);
            if(line.substring(0,1).equals("$")) {
                if(line.substring(2,3).equals("c")) {
                    String dir = line.substring(5);
                    if(dir.equals("..")) {
                        current = current.parent==null?current:current.parent;
                    } else {
                        current = current.addSub(dir, current);
                    }
                } else {
                    //System.out.println(line);
                }
                
            } else {
                if(line.substring(0,1).equals("d")) {
                    current.addSub(line.substring(4),current);
                } else {
                    current.addContent(line);
                }
            }

        }
        while(current.parent != null) {
            current = current.parent;
        }

        Set<Directory> over = new HashSet<Directory>();

        over.addAll(getOver(over,current));
        int total = 0;
        for(Directory d : over) {
            total += d.getSize();
            System.out.println(d.name);
        }
        System.out.println(total);
    }

    public static Set<Directory> getOver(Set<Directory> over, Directory d) {
        if (d == null) {
            return over;
        }

        if(d.getSize() <= 100000) {
            over.add(d);
        }

        for(Directory sd : d.subDirectories) {
            over.addAll(getOver(over, sd));
        }
        
        return over;
    }

    public static void getAllDirectories(Set<Directory>all, Directory current) {
        
        all.add(current);

        for(Directory sd : current.subDirectories) {
            getAllDirectories(all, sd);
        }
 
    }


    public static void part2() throws IOException {
        Directory current = new Directory("/", null);

        Scanner in = new Scanner(new File("input.txt"));
        
        
        while(in.hasNext()) {
            String line = in.nextLine();
            //System.out.println(current);
            if(line.substring(0,1).equals("$")) {
                if(line.substring(2,3).equals("c")) {
                    String dir = line.substring(5);
                    if(dir.equals("..")) {
                        current = current.parent==null?current:current.parent;
                    } else {
                        current = current.addSub(dir, current);
                    }
                } else {
                    //System.out.println(line);
                }
                
            } else {
                if(line.substring(0,1).equals("d")) {
                    current.addSub(line.substring(4),current);
                } else {
                    current.addContent(line);
                }
            }

        }
        while(current.parent != null) {
            current = current.parent;
        }

        Set<Directory> allDirectories = new HashSet<Directory>();

        getAllDirectories(allDirectories, current);
        int remaining = 70000000 - current.getSize();
        int closest = current.getSize();
        for(Directory d: allDirectories) {
            int size = d.getSize();
            
            if(remaining + size >= 30000000 && size < closest) {
                closest = size;
            }
        }
        System.out.println(closest);

    }

    public static void main(String...args) {
        try {
            part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}