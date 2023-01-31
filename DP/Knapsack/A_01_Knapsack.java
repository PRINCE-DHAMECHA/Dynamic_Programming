package DP.Knapsack;

import java.util.Arrays;

// 01 meaning either take it 1 time or 0 times
public class A_01_Knapsack {

    static int[][] mem = new int[6][10];

    public static void main(String[] args) {
        int weight[] = new int[]{5, 3, 2, 1};
        int value[] = new int[]{9, 7, 3, 2};
        int W = 5;
        System.out.println("\nGiven Values: \n");
        System.out.println("Knapsack Capacity: " + W);

        System.out.print("Weights: ");
        System.out.println(Arrays.toString(weight));
        System.out.println();

        System.out.print("Profits: ");
        System.out.println(Arrays.toString(value));
        System.out.println();

        System.out.println("\nSolving Knapsack Using Recursion Only: \n");
        int recKnapsackAns = A01_knapsackRec(weight, value, W, weight.length);
        System.out.println("The Maximum Profit From Recursive Knapsack Solution: " + recKnapsackAns);
        System.out.println();

        for (int[] m : mem) {
            Arrays.fill(m, -1);
        }

        System.out.println("\nSolving Knapsack Using Memoization: \n");
        int memKnapsackAns = A01_knapsackMem(weight, value, W, weight.length);
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
        System.out.println("The Maximum Profit From Recursive Knapsack With Top-Down Approach: " + A01_knapsackTopDown(weight, value, W) + "\n");

        System.out.println("\nTarget Sum: \n");
        int[] numbers = new int[]{4, 6, 8, 10, 3, 7};
        int target = 5;
        if (subsetSum(numbers, target)) {
            System.out.println("We Can Make " + target + " From Given Numbers " + Arrays.toString(numbers) + "\n");
        } else {
            System.out.println("We Cannot Make " + target + " From Given Numbers " + Arrays.toString(numbers) + "\n");
        }

        System.out.println("\nEqual Sum: \n");
        if (equalSum(numbers)) {
            System.out.println("We can Make Equal Sum Partition From Given Numbers " + Arrays.toString(numbers) + "\n");
        } else {
            System.out.println("We cannot Make Equal Sum Partition From Given Numbers " + Arrays.toString(numbers) + "\n");
        }

        System.out.println("\nSubset Count Given Sum: \n");
        target = 14;
        int numberOfSubsetsGivenSum = countSubsetsGivenSum(numbers, target);
        System.out.println("We can make " + target + " By " + numberOfSubsetsGivenSum + " Ways\n");

        System.out.println("\nTarget Sum: \n");
        int[] targetSumNumbers = new int[]{1, 1, 2, 3};
        target = 1;
        System.out.println("Given Number: " + Arrays.toString(targetSumNumbers));
        System.out.println("Target: " + target + "\n");
        int targetSumCount = TargetSum(targetSumNumbers, target);
        System.out.println("There's " + targetSumCount + " Ways We can Make " + target + " By assigning '+' or '-'");

    }

    // Solving Knapsack Using Recursion
    public static int A01_knapsackRec(int[] weight, int[] value, int w, int n) {
        // Base Condition, If we don't have any items or the knapsack is full because we start from w=knapsack capacity
        if (n == 0 || w <= 0) {
            return 0;
        }
        // If knapsack capacity in less than the given item weight we can't add that item
        if (weight[n - 1] > w) {
            // Here we'll change current item but won't change current knapsack capacity because we didn't add any item in this recursion call
            return A01_knapsackRec(weight, value, w, n - 1);
        } else {
            // Now we have two choices, either take current item or not

            // We add profit of current item and decrease knapsack capacity
            int taken = value[n - 1] + A01_knapsackRec(weight, value, w - weight[n - 1], n - 1);
            // We will not change knapsack capacity but change the current item
            int notTaken = A01_knapsackRec(weight, value, w, n - 1);

            // We return maximum profit by either taking or not taking
            return Math.max(taken, notTaken);
        }
    }

    // Solving Knapsack Using Memoization
    public static int A01_knapsackMem(int[] weight, int[] value, int w, int n) {

        // Here we take 2d array of size n+1(0 to n) X w+1(0 to w)  because only changing variables are knapsack capacity and number of items

        // If already calculated we won't waste our valuable stack
        if (mem[w][n] != -1) {
            return mem[w][n];
        }

        // Same as recursion but we check table before calculating
        if (n == 0 || w == 0) {
            return mem[w][n] = 0;
        }

        // After Solving current recursion we store values in memoization table
        if (weight[n - 1] > w) {
            return mem[w][n] = A01_knapsackMem(weight, value, w, n - 1);
        } else {
            int taken = value[n - 1] + A01_knapsackMem(weight, value, w - weight[n - 1], n - 1);
            int notTaken = A01_knapsackMem(weight, value, w, n - 1);
            return mem[w][n] = Math.max(taken, notTaken);
        }
    }

    // Solving Knapsack Using Top-Down:
    public static int A01_knapsackTopDown(int[] weight, int[] value, int w) {
        // Here we declare dp table of size n+1(0 to n) X w+1(0 to w)
        // We fill first row and first column with 0 because if knapsack capacity is zero we can't take any profit and if we don't have any items we can't make any profit
        // Fortunately java initialize all array values to zero by default, so we don't need to explicitly initialize first row and column
        int[][] dp = new int[weight.length + 1][w + 1];

        // Now we start filling dp table in row manner and also ignore already filled blocks
        for (int i = 1; i <= weight.length; i++) {
            for (int j = 1; j <= w; j++) {
                // if current knapsack weight i.e. j must be greater than current item
                if (weight[i - 1] <= j) {
                    // We have two choices weight either take it or leave it

                    // If we take we add that value and decrement j by current item weight and current item index
                    // Notice our i,k is one index ahead than actual array
                    // i.e. i==0 means there's no item in array and i==1 means there's one item, and it's index will be i-1
                    int taken = value[i - 1] + dp[i - 1][j - weight[i - 1]];
                    // if we don't take current item, we change i to i-1 and take weight as it is
                    int notTaken = dp[i - 1][j];
                    // Return maximum value
                    dp[i][j] = Math.max(taken, notTaken);
                } else {
                    // We don't have any option simpy ignore current item and move on
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        // dp[i][j] indicates profit when we have first i number of items and knapsack capacity of j
        System.out.println("DP Table:");
        for (int[] i : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        // return dp[weight.length][w] because it will represent profit with whole knapsack capacity and all the items
        return dp[weight.length][w];
    }

    // SubSetSum : We're given array of values, and we need to find if we can find any subset which sum up to target
    public static boolean subsetSum(int[] numbers, int target) {

        // Here the answer will be in boolean, so we take 2d array accordingly
        boolean[][] dp = new boolean[numbers.length + 1][target + 1];

        // If we need to find sum zero then we always have 1 way by not taking any values so we initialize first column with true
        for (int i = 0; i <= numbers.length; i++) {
            dp[i][0] = true;
        }
        // This part is very similar to our beloved top down 01-knapsack so we just discuss the difference between two
        for (int i = 1; i <= numbers.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (numbers[i - 1] <= j) {
                    boolean taken = dp[i - 1][j - numbers[i - 1]];
                    boolean notTaken = dp[i - 1][j];
                    // We just need to find either possibility gives true so we use OR operator
                    dp[i][j] = (taken) || (notTaken);
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
            for (boolean j : dp[i]) {
                System.out.print((j ? "01" : "00") + " ");
            }
            System.out.println();
        }
        System.out.println();
        return dp[numbers.length][target];
    }

    // equalSum: Can we divide our number in two subsets such that it will sum up equal?
    public static boolean equalSum(int[] numbers) {
        // so here we can utilize SubSetSum by providing argument of sum as half of sum of given numbers
        int sum = 0; //Total sum
        for (int i : numbers) {
            sum += i;
        }
        // If sum is odd then no way in earth we can divide given number in two equal sum substes.
        if (sum % 2 == 1) {
            return false;
        } else {
            // we will sum>>1 i.e. sum/2 to argument of subsetsum
            return subsetSum(numbers, sum >> 1);
        }
    }

    // count number of subset sum up to given target
    public static int countSubsetsGivenSum(int[] numbers, int target) {
        int[][] dp = new int[numbers.length + 1][target + 1];
        // Fact that if the sum needs to be 0 then there's only one subset that is [].
        for (int i = 0; i <= numbers.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= numbers.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (numbers[i - 1] <= j) {
                    int taken = dp[i - 1][j - numbers[i - 1]];
                    int notTaken = dp[i - 1][j];
                    // Here we consider both if we take current items then how many subsets are possible and also if we don't then how many subsets possible
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
        return dp[numbers.length][target];
    }

    //TargetSum : Append + or - before number and form equation from given number such that answer of the equation will be equal to target
    //like + 1 - 2 + 1 - 3 = -3
    public static int TargetSum(int[] numbers, int target) {
        // The main logic is that we are dividing given numbers in two subsets such that the difference is equal to target, and we know the fact that their sum will be total of all numbers Bingo:)
        // S1 + S2 = Total
        // S1 - S2 = target
        // ----------------
        // 2 * S1 = Total + target
        // S1 = (Total + target)/2
        // Now this will be our target
        int total = 0;
        for (int i : numbers) {
            total += i;
        }
        // If (Total + target) will be odd then we can't divide it by 2 to integer
        if ((target + total) % 2 == 1) {
            return 0;
        } else {
            target = (target + total) / 2;
        }
        return countSubsetsGivenSum(numbers, target);
    }

}
