import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Day11 {

    public static long lcm = 1;

    private static class Monkey {
        ArrayList<Long> items;
        String[] operation; // operation[0] is operation, operation[1] is operand
        int divisibleBy;
        int throwToTrue;
        int throwToFalse;
        long inspected;
        int monkeyNum;

        public Monkey(int monkeyNum, String[] items, String[] operation, int divisibleBy, int throwToTrue, int throwToFalse) {
            this.monkeyNum = monkeyNum;
            this.items = new ArrayList<Long>();
            for(String item : items) {
                this.items.add(Long.parseLong(item));
            }

            this.operation = operation;
            this.divisibleBy = divisibleBy;
            this.throwToTrue = throwToTrue;
            this.throwToFalse = throwToFalse;

            inspected = 0;
        }

        public void performOperation(ArrayList<Monkey> monkeys, int part) {
            //System.out.println(Arrays.toString(operation));
            //System.out.println(monkeys);
            for(int i = 0; i < items.size(); i++) {
                
                inspected++;
                long worry = items.get(i);
                long oldWorry = worry;
               
                if(operation[0].equals("*")) {
                    if(operation[1].equals("old")) {
                        worry *= oldWorry;
                    } else {
                        worry *= Integer.parseInt(operation[1]);
                    }
                } else {
                    if(operation[1].equals("old")) {
                        worry += oldWorry;
                    } else {
                        worry += Integer.parseInt(operation[1]);
                    }
                }

                if(part == 1)
                    worry /= 3;

                if(part == 2)
                    worry %= lcm;
                
                

                if(worry % divisibleBy == 0) {
                    int newDivBy = monkeys.get(throwToTrue).divisibleBy;
                    
                    monkeys.get(throwToTrue).addItem(worry);
                } else {
                    int newDivBy = monkeys.get(throwToFalse).divisibleBy;
                    
                    monkeys.get(throwToFalse).addItem(worry);
                }

            }
            items = new ArrayList<Long>();
        }

        public void addItem(long worry) {
            items.add(worry);
        }

        public String toString() {
            String result = "";
            result += "Monkey " + monkeyNum;
            result += " Items: " + this.items;
            result += " Inspected: " + this.inspected;
            return result;
        }

        public long getInspected() {
            return inspected;
        }

    }

    public static void part1() throws IOException {
        ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
        Scanner in = new Scanner(new File("sample.txt"));
        while(in.hasNext()) {
            String line = in.nextLine(); // Monkey 0:
            line = line.split(" ")[1];
            
            int num = Integer.parseInt(line.substring(0,line.length()-1));
            
            line = in.nextLine(); // Starting items: 79, 60, 97
            String[] items = line.substring(line.indexOf(":")+2).split(", ");
            
            line = in.nextLine(); // Operation: new = old * 16
            String[] operation = line.substring(line.indexOf("old")+4).split(" ");
            
            line = in.nextLine(); // Test: divisible by 13
            int divisibleBy = Integer.parseInt(line.split(" ")[5]);

            line = in.nextLine(); // If true: throw to monkey 1
            int throwToTrue = Integer.parseInt(line.split(" ")[9]);

            line = in.nextLine(); // If false: throw to monkey 3
            int throwToFalse = Integer.parseInt(line.split(" ")[9]);

            monkeys.add(new Monkey(num, items, operation, divisibleBy, throwToTrue, throwToFalse));
            if(in.hasNext())
                in.nextLine();

        }


        for(int round = 0; round < 1000; round++) {
            for(Monkey monkey:monkeys) {
                System.out.println(monkey);
                monkey.performOperation(monkeys, 1);
            }
        }

        ArrayList<Long> inspections = new ArrayList<Long>();
        for(Monkey monkey : monkeys) {
            //System.out.println(monkey);
            inspections.add(monkey.getInspected());
        }

        Collections.sort(inspections);

        System.out.println(inspections);

        System.out.println(inspections.get(inspections.size()-1) * inspections.get(inspections.size()-2));



    }

    public static void part2() throws IOException {
        ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
        Scanner in = new Scanner(new File("sample.txt"));
        while(in.hasNext()) {
            String line = in.nextLine(); // Monkey 0:
            line = line.split(" ")[1];
            
            int num = Integer.parseInt(line.substring(0,line.length()-1));
            
            line = in.nextLine(); // Starting items: 79, 60, 97
            String[] items = line.substring(line.indexOf(":")+2).split(", ");
            
            line = in.nextLine(); // Operation: new = old * 16
            String[] operation = line.substring(line.indexOf("old")+4).split(" ");
            
            line = in.nextLine(); // Test: divisible by 13
            int divisibleBy = Integer.parseInt(line.split(" ")[5]);

            line = in.nextLine(); // If true: throw to monkey 1
            int throwToTrue = Integer.parseInt(line.split(" ")[9]);

            line = in.nextLine(); // If false: throw to monkey 3
            int throwToFalse = Integer.parseInt(line.split(" ")[9]);

            monkeys.add(new Monkey(num, items, operation, divisibleBy, throwToTrue, throwToFalse));
            if(in.hasNext())
                in.nextLine();

        }

        for(Monkey m : monkeys) {
            lcm *= m.divisibleBy;
        }

        for(int round = 0; round < 10000; round++) {
            //System.out.println("ROUND #" + round);
            for(Monkey monkey:monkeys) {
                //System.out.println(monkey);
                monkey.performOperation(monkeys, 2);
            }
        }

        

        ArrayList<Long> inspections = new ArrayList<Long>();
        for(Monkey monkey : monkeys) {
            //System.out.println(monkey);
            inspections.add(monkey.getInspected());
        }

        Collections.sort(inspections);

        System.out.println(inspections);

        System.out.println(inspections.get(inspections.size()-1) * inspections.get(inspections.size()-2));

    }

    public static void main(String...args) {
        try {
            part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}