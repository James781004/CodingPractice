package LeetcodeMaster.DP;

public class Q07_NumTrees {
//    96.不同的二叉搜索樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0096.%E4%B8%8D%E5%90%8C%E7%9A%84%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.md
//
//    給定一個整數 n，求以 1 ... n 為節點組成的二叉搜索樹有多少種？

    public int numTrees(int n) {
        // 初始化 dp 數組
        int[] dp = new int[n + 1]; // dp[i] ： 1到i為節點組成的二叉搜索樹的個數為dp[i]。
        // 初始化0個節點和1個節點的情況
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                // 對於第i個節點，需要考慮1作為根節點直到i作為根節點的情況，所以需要累加
                // 一共i個節點，對於根節點j時，j-1 為j為頭節點左子樹節點數量，i-j 為以j為頭節點右子樹節點數量
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

}
