package Problem5;

import java.io.*;
import java.util.Arrays;

public class Problem5 {
    public static void main(String[] args) {
        File inputSmall = new File("D:\\raoinfotech\\src\\Problem5\\TOF_small.txt");
        File inputLarge = new File("D:\\raoinfotech\\src\\Problem5\\TOF_large.txt");

        File outputSmall = new File("D:\\raoinfotech\\src\\Problem5\\P5OutputSmall.txt");
        File outputLarge = new File("D:\\raoinfotech\\src\\Problem5\\P5OutputLarge.txt");

        orchardFarm(inputSmall, outputSmall);
        orchardFarm(inputLarge, outputLarge);
    }

    static void orchardFarm(File inputFile, File outputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();

            int noOfCases = Integer.parseInt(line);

            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            PrintWriter pw = new PrintWriter(bw);

            for (int i = 0; i < noOfCases; i++) {
                line = br.readLine();
                String[] input = line.split("\\s+");
                int ans = calculate(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                pw.printf("Case #%d: %d\n", (i+1), ans);
            }
            pw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int calculate(int totalTrees, int totalDays) {
        Fruit apple = new Fruit(400, 10, 5, 150);
        Fruit orange = new Fruit(280, 6, 7, 250);
        Fruit mango = new Fruit(2200, 15, 8, 100);
        Fruit lemon = new Fruit(1500, 5, 10, 50);
        Fruit coconut = new Fruit(75, 15, 15, 1600);
        Fruit[] trees = {apple, orange, mango, lemon, coconut};

        // For first 5 trees
        int ans = 0;
        for (Fruit tree: trees) {
            ans += (tree.perTreeQty/tree.perKgQty) * tree.perKgPrice * (totalDays/tree.daysToGrow);
        }

        int maxTimesTaken = ((totalTrees * 40) / 100);

        int[] dp = new int[totalTrees];
        Arrays.fill(dp, -1);

        ans += calculateMax(totalTrees-5, totalDays, trees, maxTimesTaken, dp);

        return ans;
    }

    static int calculateMax(int totalTrees, int totalDays, Fruit[] trees, int maxTimesTaken, int[] dp) {
        if (totalTrees == 0) {
            return 0;
        }
        if (dp[totalTrees] != -1) {
            return dp[totalTrees];
        }

        int max = 0;
        for (Fruit tree : trees) {
            if (tree.timesTaken >= maxTimesTaken) {
                continue;
            }
            tree.timesTaken++;
            int takeItself = (tree.perTreeQty/tree.perKgQty) * tree.perKgPrice * (totalDays/tree.daysToGrow)
                    + calculateMax(totalTrees - 1, totalDays, trees, maxTimesTaken, dp);
            tree.timesTaken--;
            int takeNext = calculateMax(totalTrees - 1, totalDays, trees, maxTimesTaken, dp);
            max = Math.max(max, Math.max(takeNext, takeItself));
        }

        return dp[totalTrees] = max;
    }

    static class Fruit {
        int perTreeQty, daysToGrow, perKgQty, perKgPrice;
        int timesTaken = 1;
        Fruit(int perTreeQty, int daysToGrow, int perKgQty, int perKgPrice) {
            this.perTreeQty = perTreeQty;
            this.daysToGrow = daysToGrow;
            this.perKgQty = perKgQty;
            this.perKgPrice = perKgPrice;
        }
    }
}
