package DP.Knapsack;

import java.util.Arrays;

// Unbounded: Take any item any time No restriction!!
public class A_Unbounded_Knapsack {

    static int[][] mem = new int[6][10];

    public static void main(String[] args) {
        int weight[] = new int[]{5, 3, 2, 1};
        int value[] = new int[]{9, 7, 3, 2};
        int W = 5;

        System.out.println("\nKnapsack Capacity: " + W);

        System.out.print("Weights: ");
        System.out.println(Arrays.toString(weight));
        System.out.print("Profits: ");
        System.out.println(Arrays.toString(value));
        System.out.println();

        System.out.println("\nSolving Knapsack Using Recursion Only: \n");
        int recKnapsackAns = unboundedKnapsackRec(weight, value, W, weight.length);
        System.out.println("The Maximum Profit From Recursive Knapsack Solution: " + recKnapsackAns);
        System.out.println();

        for (int[] m : mem) {
            Arrays.fill(m, -1);
        }

        System.out.println("\nSolving Knapsack Using Memoization: \n");
        int memKnapsackAns = unboundedKnapsackMem(weight, value, W, weight.length);
        System.out.println("The Maximum Profit From Recursive Knapsack With memoization Solution: " + memKnapsackAns);
        System.out.println("Memoized Matrix: ");
        for (int[] m : mem) {
            for (int i : m) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("\nSolving Knapsack Using Top-Down: \n");
        System.out.println("The Maximum Profit From Recursive Knapsack With Top-Down Approach: " + unboundedKnapsackTopDown(weight, value, W) + "\n");

        System.out.println("\nRod Cutting Problem: \n");
        int[] arr = new int[]{1, 5, 8, 9, 10, 17, 17, 20};
        int rodLength = 10;
        System.out.println("The respective profit for length 1 to n is: " + Arrays.toString(arr));
        System.out.println("Rod Length: " + rodLength + "\n");
        int maxProfitFromCuttingRod = rodCutting(arr, rodLength);
        System.out.println("\nWe Can Make Maximum Profit Of " + maxProfitFromCuttingRod + " By Cutting Rod Of Length " + rodLength+"\n");

        System.out.println("\nMaximum ways to change given amount by given coins: \n");
        int[] coins = new int[]{2, 5, 3, 6};
        int change = 10;
        System.out.println("Given Coins: " + Arrays.toString(coins));
        System.out.println("Change: " + change+"\n");
        int maxWaysOfCoinChange = maxWayCoinChange(coins, change);
        System.out.println("There Are " + maxWaysOfCoinChange + " Maximum Ways To Make Change of " + change+"\n");

        System.out.println("\nMinimum required coins to change given amount by given coins: \n");
        int minCoins = minCoinChange(coins, change);
        System.out.println("Minimum Coins Required To Change " + change + " Is: " + minCoins);

    }

    // same as our 01 but we can take any items any time
    public static int unboundedKnapsackRec(int[] weight, int[] value, int w, int n) {
        if (n == 0 || w <= 0) {
            return 0;
        }
        if (weight[n - 1] > w) {
            return unboundedKnapsackRec(weight, value, w, n - 1);
        } else {
            // So if we take current coin then we just update weight not current item
            int taken = value[n - 1] + unboundedKnapsackRec(weight, value, w - weight[n - 1], n);
            // Ofcource if we don't take current item, we will change it
            int notTaken = unboundedKnapsackRec(weight, value, w, n - 1);
            return Math.max(taken, notTaken);
        }
    }

    // same as 01 but change as above
    public static int unboundedKnapsackMem(int[] weight, int[] value, int w, int n) {
        if (n == 0 || w <= 0) {
            return 0;
        }
        if (mem[w][n] != -1) {
            return mem[w][n];
        }
        if (weight[n - 1] > w) {
            return mem[w][n] = unboundedKnapsackMem(weight, value, w, n - 1);
        } else {
            int taken = value[n - 1] + unboundedKnapsackMem(weight, value, w - weight[n - 1], n);
            int notTaken = unboundedKnapsackMem(weight, value, w, n - 1);
            return mem[w][n] = Math.max(taken, notTaken);
        }
    }

    // Same as 01 but change as above
    public static int unboundedKnapsackTopDown(int[] weight, int[] value, int w) {
        int[][] dp = new int[weight.length + 1][w + 1];
        for (int i = 1; i <= weight.length; i++) {
            for (int j = 1; j <= w; j++) {
                if (weight[i - 1] <= j) {
                    int taken = value[i - 1] + dp[i][j - weight[i - 1]];
                    int notTaken = dp[i - 1][j];
                    dp[i][j] = Math.max(taken, notTaken);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println("DP Table:");
        for (int[] i : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        return dp[weight.length][w];
    }

    // Rod Cutting: cut given rod such that the profit from each piece sum up to maximum
    // If we have n size rod then there must be n size array such that ith indexed element represent (i + 1) weight rod's profit
    // Basically it's a simple unbounded knapsack problem but we just need to find weight array ourselves
    public static int rodCutting(int[] value, int w) {
        int[] weight = new int[value.length];
        for (int i = 0; i < value.length; i++) {
            weight[i] = i + 1;
        }
        int[][] dp = new int[weight.length + 1][w + 1];
        for (int i = 1; i <= weight.length; i++) {
            for (int j = 1; j <= w; j++) {
                if (weight[i - 1] <= j) {
                    int taken = value[i - 1] + dp[i][j - weight[i - 1]];
                    int notTaken = dp[i - 1][j];
                    dp[i][j] = Math.max(taken, notTaken);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println("DP Table:");
        for (int[] i : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        return dp[weight.length][w];
    }

    // MaximumCoinChange: count maximum ways we can change using given coins
    public static int maxWayCoinChange(int[] coins, int target) {
        int[][] dp = new int[coins.length + 1][target + 1];
        // if we have 0 amount to exchange we have only one obvious way
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (coins[i - 1] <= j) {
                    // Here we consider both if we take current items then how many maximum ways are there are possible and also if we don't then how many
                    int taken = dp[i][j - coins[i - 1]];
                    int notTaken = dp[i - 1][j];
                    dp[i][j] = (taken) + (notTaken);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println("DP Table:");
        System.out.print("   ");
        for (int i = 0; i <= target; i++) {
            System.out.print(String.format("%02d ", i));
        }
        System.out.println();
        for (int i = 0; i < dp.length; i++) {
            System.out.print(String.format("%02d ", i));
            for (int j : dp[i]) {
                System.out.print(String.format("%02d ", j));
            }
            System.out.println();
        }
        System.out.println();
        return dp[coins.length][target];
    }

    // minimumCoinChange: how many minimum coins needed to change // This can be solved by two methods but here we stick to knapsack
    public static int minCoinChange(int[] coins, int target) {
        int[][] dp = new int[coins.length + 1][target + 1];
        // if amount is 0 then there's minimum 0 coins needed to change
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 0;
        }
        // Now's that's interesting case. What if we have no coins, and we need to change some amount then practically it is not seems possible but to represent this theoretically we define that situation as minimum infinite coins needed to change
        for (int i = 0; i <= target; i++) {
            dp[0][i] = Integer.MAX_VALUE;
        }
        // Now due to this special case we can't do our taken notTaken game directly because there's some point we need to use above row, and we use it like (1 + something) and the something will be INT_MAX, so we avoid this as below...
        for (int i = 1; i <= target; i++) {
            if (i % coins[0] == 0) {
                dp[1][i] = i / coins[0];
            } else {
                dp[1][i] = Integer.MAX_VALUE - 1;
            }
        }
        for (int i = 2; i <= coins.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (coins[i - 1] <= j) {
                    // Now if we take current coin then the total coin count will be increased by 1
                    int taken = 1 + dp[i][j - coins[i - 1]];
                    int notTaken = dp[i - 1][j];
                    // We take minimum coins if by either taking current coin or not
                    dp[i][j] = Integer.min(taken, notTaken);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println("DP Table:");
        System.out.print("   ");
        for (int i = 0; i <= target; i++) {
            System.out.print(String.format("%02d ", i));
        }
        System.out.println();
        for (int i = 0; i < dp.length; i++) {
            System.out.print(String.format("%02d ", i));
            for (int j : dp[i]) {
                System.out.print(String.format("%02d ", j));
            }
            System.out.println();
        }
        System.out.println();
        return dp[coins.length][target];
    }

}
