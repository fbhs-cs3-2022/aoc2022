import java.util.*;
import java.io.*;

public class Day08 {

    private static class Tree {
        boolean canSee;
        int height;
        int score;

        public Tree(int h, boolean canSee) {
            height = h;
            this.canSee = canSee;
            score = 0;
        }

        public void getScore(Tree[][] trees, int r, int c) {

            if (r == 0 || c == 0 || r == trees.length - 1 || c == trees[0].length - 1) {
                score = 0;
                return;
            }

            int up = 1;
            int left = 1;
            int right = 1;
            int down = 1;

            // up
            for (int row = r - 1; row > 0; row--) {
                if (trees[row][c].height < height) {
                    up++;
                } else {
                    break;
                }
            }

            // down
            for (int row = r + 1; row < trees.length - 1; row++) {
                if (trees[row][c].height < height) {
                    down++;
                } else {
                    break;
                }
            }

            // right
            for (int col = c + 1; col < trees[0].length - 1; col++) {
                if (trees[r][col].height < height) {
                    right++;
                } else {
                    break;
                }
            }

            // left
            for (int col = c - 1; col > 0; col--) {
                if (trees[r][col].height < height) {
                    left++;
                } else {
                    break;
                }
            }

            score = up * left * right * down;
        }

        public String toString() {
            return "" + height;
        }
    }

    public static void checkVisible(Tree[][] trees) {
        // System.out.println("Checking...");
        for (int r = 0; r < trees.length; r++) {
            for (int c = 0; c < trees[0].length; c++) {
                // System.out.println(r + " " + c);
                if (trees[r][c].canSee)
                    continue;

                Tree thisTree = trees[r][c];

                // check from top
                boolean isVisible = true;
                for (int row = 0; row < r; row++) {

                    if (trees[row][c].height >= thisTree.height) {

                        isVisible = false;
                        break;
                    }
                }
                if (isVisible) {
                    thisTree.canSee = true;
                    continue;
                }

                // check from bottom
                isVisible = true;
                for (int row = trees.length - 1; row > r; row--) {

                    if (trees[row][c].height >= thisTree.height) {
                        isVisible = false;
                        break;
                    }
                }
                if (isVisible) {
                    thisTree.canSee = true;
                    continue;
                }

                // check from left
                isVisible = true;
                for (int col = 0; col < c; col++) {
                    if (trees[r][col].height >= thisTree.height) {
                        isVisible = false;
                        break;
                    }
                }
                if (isVisible) {
                    thisTree.canSee = true;
                    continue;
                }

                // check from right
                isVisible = true;
                for (int col = trees[r].length - 1; col > c; col--) {

                    if (trees[r][col].height >= thisTree.height) {
                        isVisible = false;
                        break;
                    }
                }
                if (isVisible) {
                    thisTree.canSee = true;
                    continue;
                }

            }

        }
        System.out.println("Done");

    }

    public static void part1() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<String> rawTrees = new ArrayList<String>();

        while (in.hasNext()) {
            rawTrees.add(in.nextLine());
        }
        System.out.println(rawTrees);

        int width = rawTrees.get(0).length();
        int height = rawTrees.size();

        Tree[][] trees = new Tree[height][width];

        for (int i = 0; i < rawTrees.size(); i++) {
            for (int j = 0; j < rawTrees.get(i).length(); j++) {
                if (i == 0 || j == 0 || i == rawTrees.size() - 1 || j == rawTrees.get(i).length() - 1)
                    trees[i][j] = new Tree(Integer.parseInt(rawTrees.get(i).substring(j, j + 1)), true);
                else
                    trees[i][j] = new Tree(Integer.parseInt(rawTrees.get(i).substring(j, j + 1)), false);
            }
        }

        // edges
        for (Tree[] row : trees) {
            for (Tree t : row) {
                System.out.print(t);
            }
            System.out.println();
        }

        checkVisible(trees);

        int numVisible = 0;

        for (Tree[] row : trees) {
            for (Tree t : row) {
                if (t.canSee)
                    numVisible++;
            }
        }

        System.out.println(numVisible);
    }

    public static void part2() throws IOException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<String> rawTrees = new ArrayList<String>();

        while (in.hasNext()) {
            rawTrees.add(in.nextLine());
        }

        int width = rawTrees.get(0).length();
        int height = rawTrees.size();

        Tree[][] trees = new Tree[height][width];

        for (int i = 0; i < rawTrees.size(); i++) {
            for (int j = 0; j < rawTrees.get(i).length(); j++) {
                if (i == 0 || j == 0 || i == rawTrees.size() - 1 || j == rawTrees.get(i).length() - 1)
                    trees[i][j] = new Tree(Integer.parseInt(rawTrees.get(i).substring(j, j + 1)), true);
                else
                    trees[i][j] = new Tree(Integer.parseInt(rawTrees.get(i).substring(j, j + 1)), false);
            }
        }

        // edges
        for (Tree[] row : trees) {
            for (Tree t : row) {
                System.out.print(t);
            }
            System.out.println();
        }

        for (int r = 0; r < trees.length; r++) {
            for (int c = 0; c < trees[0].length; c++) {
                trees[r][c].getScore(trees, r, c);
            }
        }

        int maxScore = 0;

        for (Tree[] row : trees) {
            for (Tree t : row) {
                if (t.score > maxScore) {
                    maxScore = t.score;
                }
            }
        }
        System.out.println(maxScore);

        int largest = 0;
        for (int r = 0; r < trees.length; r++) {
            for (int c = 0; c < trees[0].length; c++) {
                int up = r;
                int down = trees.length - r - 1;
                int left = c;
                int right = trees[0].length - c - 1;
                if (largest < up * down * left * right) {
                    largest = up * down * left * right;
                }
            }
        }
        System.out.println(largest);

    }

    public static void main(String... args) {
        try {
            part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}