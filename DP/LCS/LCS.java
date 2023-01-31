package DP.LCS;

import java.util.Arrays;

// LCS: longest Common Subsequence
// Ex: s1: "ADBCDEGH" s2: "ABCEDFHR"
// LCS: "ABCEH"
public class LCS {

    static int[][] mem = new int[20][20];

    public static void main(String[] args) {

        String x = "ARDBCDEGH", y = "ARBCEDFHR";
        System.out.println("\nString 1: " + x + " String 2: " + y + "\n");

        System.out.println("\nLCS Solution Using Recursion: ");
        int lcsRec = LCSRec(x, y, x.length(), y.length());
        System.out.println("\nLength Of Longest Common Subsequence Of Given Two Strings Is: " + lcsRec + "\n");

        for (int[] m : mem) {
            Arrays.fill(m, -1);
        }
        System.out.println("\nLCS Solution Using Memoization: ");
        int lcsMem = LCSMem(x, y, x.length(), y.length());
        System.out.println("\nMemoization Table");
        for (var i : mem) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println("\nLength Of Longest Common Subsequence Of Given Two Strings Is: " + lcsMem + "\n");

        System.out.println("\nLCS Solution Using Top Down:\n");
        int lcsTopDown = LCSTopDown(x, y, x.length(), y.length());
        System.out.println("\nLength Of Longest Common Subsequence Of Given Two Strings Is: " + lcsTopDown + "\n");

        System.out.println("\nPrinting LCS:\n");
        String lcs = printLCS(x, y, x.length(), y.length());
        System.out.println("\nLongest Common Subsequence Of Given Two Strings Is: " + lcs + "\n");

        System.out.println("\nLongest Common Substring Solution Using Top Down:\n");
        int lcsubstring = longestCommonSubstring(x, y, x.length(), y.length());
        System.out.println("\nLength Of Longest Common Substring Of Given Two Strings Is: " + lcsubstring + "\n");
    }

    // Solving LCS using recursion
    // The basic idea of finding LCS is that we compare last character of each string
    // If the characters are same then we definitely add 1 to our lcs and continue comparing second last characters
    // No if the last characters will not match then there is two choices
    // First, We ignore last character of first string and length of second string will be same as before
    // Second, vice versa of first
    // Now we consider both choice and as our goal is to maximize sequence we will take maximum answer of both
    public static int LCSRec(String x, String y, int m, int n) {
        // If either sting is empty then we know that the LCS will be 0
        if (m == 0 || n == 0) {
            return 0;
        } else {
            // Here for recursive call for m and n we are comparing m-1 and n-1, But why?
            // Because the fact is that m and n represent length, and we need last characters, and it is known fact that index starts from 0 :)
            // If the characters are same we add it to our answer and continue with comparing next means m-2 and n-2 characters
            if (x.charAt(m - 1) == y.charAt(n - 1)) {
                return 1 + LCSRec(x, y, m - 1, n - 1);
            } else {
                // Now we work on our two choice

                // Take full string 2 up to length n and string 1 up to length m-1
                int choice1 = LCSRec(x, y, m - 1, n);
                // Vice versa
                int choice2 = LCSRec(x, y, m, n - 1);
                // return maximum of both
                return Math.max(choice1, choice2);
            }
        }
    }

    // Solving LCS using memoization
    // same as recursion but notice that in recursion there is possibility of same recursion call can be made for multiple time, so we need to solve this problem
    // Here we first check our memoization table and if we've already calculated that particular recursion call we just return previous value
    // Question is that what will be the dimension of the table?
    // Simple, Whatever values changing in recursion call will be our choice for dimension
    // Here we got m and n.
    public static int LCSMem(String x, String y, int m, int n) {

        // Return value if previously calculated
        if (mem[m][n] != -1) {
            return mem[m][n];
        }

        // Same as recursion but store value each
        // If we don't store values in memoization then how can we use the same in the future :)
        if (m == 0 || n == 0) {
            return mem[m][n] = 0;
        } else {
            if (x.charAt(m - 1) == y.charAt(n - 1)) {
                return mem[m][n] = 1 + LCSMem(x, y, m - 1, n - 1);
            } else {
                int choice1 = LCSMem(x, y, m - 1, n);
                int choice2 = LCSMem(x, y, m, n - 1);
                return mem[m][n] = Math.max(choice1, choice2);
            }
        }
    }

    // Solving LCS Using Top-Down:
    public static int LCSTopDown(String s1, String s2, int m, int n) {
        // Here we declare dp table of size m+1(0 to n) X n+1(0 to w)
        // We fill first row and first column with 0 because if either of strings is NULL then LCS will be 0
        // Fortunately java initialize all array values to zero by default, so we don't need to explicitly initialize first row and column
        int[][] dp = new int[m + 1][n + 1];

        // Now we start filling dp table in row manner and also ignore already filled blocks
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if both character are same then we add 1 to our answer and take LCS without ith and jth character string which is represented by dp[i-1][j-1]
                // Here also i and j represent length so for getting last character we use index as i-1 and j-1 respectively
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // now we have two choices as before in recursion so return maximum of our two choices :)
                    int choice1 = dp[i - 1][j];
                    int choice2 = dp[i][j - 1];
                    dp[i][j] = Math.max(choice1, choice2);
                }
            }
        }
        // dp[i][j] indicates that if i and j is length of strings then what will be the LCS.
        System.out.println("DP Table:");
        for (int[] i : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        // Return value of dp[m][n] because it will represent LCS of whole strings
        return dp[m][n];
    }

    public static String printLCS(String s1, String s2, int m, int n) {
        // We first need to create DP table then after using that we can print LCS
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    int choice1 = dp[i - 1][j];
                    int choice2 = dp[i][j - 1];
                    dp[i][j] = Math.max(choice1, choice2);
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

        // Start from last block pf last row
        int i = m, j = n;
        StringBuilder sb = new StringBuilder();
        // We will start reverse process
        // We start from last block where we have both whole string and loop until one of them becomes NULL

        //Ex
        //    2 3
        // 3  3 2
        // 4  3 4
        //here consider we are at i=4 and j=3 now if both characters are same then we know we've taken that character and move to i-1 and j-1 means 3 and 2
        // Otherwise we select maximum from i-1 and j or j-1 and i
        // So in conclusion if character is same then we know our other subsequence came from i-1 and j-1 else we select maximum from two choices
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt((i - 1)));
                i--;
                j--;
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }
        return new String(sb.reverse());
    }

    public static int longestCommonSubstring(String s1, String s2, int m, int n) {
        // initialization will be same as LCS because the same property can be applicable to substring
        int[][] dp = new int[m + 1][n + 1];
        // We need to get maximum value from whole dp table
        int answer = 0;
        // Now we start filling dp table in row manner and also ignore already filled blocks
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // if both character are same then we add in substring
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    // We don't know whether in next step the characters are same or not so we take maximum substring until this step and at the end we return this because the answer variable will contain the longest common substring.
                    answer = Math.max(dp[i][j], answer);
                } else {
                    // now if the characters are not same then we just assign 0 because in substring we need continues character unlike subsequence
                    dp[i][j] = 0;
                }
                // Ex s1: "abed" s2: "abed"
                // we encounter c!=e but until then we know that longest common substring length is 2, but now we take 0 as character is mismatched
                // Now when we encounter d=d at last we need to compare whether this 1 size substring is largest or not so every time we encounter same character we update previous longest common substring value
            }
        }
        // dp[i][j] indicates from last how many characters are same in both string of length i and j respectively
        System.out.println("DP Table:");
        for (int[] i : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        // Return maximum
        return answer;
    }

}
