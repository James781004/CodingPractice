package GuChengAlgorithm.Ch05_DP;

import java.util.Arrays;

public class Q05_IntervalsDP {
    // DP常規4步走
    //
    // 1 狀態
    // status指的是dp數組的定義，區間dp代表的是在下標 i,j 這個區間範圍內所需要的cost，或者分數
    // 2 選擇
    // 也叫transition equation指的是從當前狀態到下一個狀態有哪幾個選項，這里一般是對區間[i, j]進行逼近，比如下一個狀態可以是更小的一個區間[i + 1, j - 1]，也可以是不同的切割區間位置[i, k], [k, j]
    // 3 起點
    // 初始化dp array，這里當size等於1的時候比如[i, i], 也就是dp[i][i] = 1比如長度為1的最長回文子串長度為1
    // 4 終點
    // 到達那里我們就結束開始取max, 一般為整個區間[0, N - 1], 這里取決於寫法top-down或者bottom-up我們來看3，4是否需要調換


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.gd959e32ada_0_55
    public String longestPalindrome(String s) {
        int N = s.length();
        boolean[][] dp = new boolean[N][N];
        int start = 0, end = 0;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j < N; j++) {  // (i + 1 > j - 1)表示ij正好相鄰
                if ((s.charAt(i) == s.charAt(j)) && (i + 1 > j - 1 || dp[i + 1][j - 1])) {
                    if (end - start < j - i) {
                        start = i;
                        end = j;
                    }
                    dp[i][j] = true;
                }
            }
        }
        return s.substring(start, end + 1);
    }


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.g91fd19cbee_2_28
    public int longestPalindromeSubMemo(String s) {
        int N = s.length();
        Integer[][] memo = new Integer[N][N];
        return lpsHelper(s, 0, N - 1, memo);
    }

    private int lpsHelper(String s, int i, int j, Integer[][] memo) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (memo[i][j] != null) return memo[i][j];
        if (s.charAt(i) == s.charAt(j)) memo[i][j] = lpsHelper(s, i + 1, j - 1, memo) + 1;
        else memo[i][j] = Math.max(lpsHelper(s, i + 1, j, memo), lpsHelper(s, i, j - 1, memo));
        return memo[i][j];
    }

    public int lps(String s) {
        int N = s.length();
        int[][] dp = new int[N][N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j < N; j++) {
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = 2 + dp[i + 1][j - 1];
                else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][N - 1];
    }


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.g91fd19cbee_2_47
    // lcs巧解，先reverse，找和自己的lcs
    // 轉化為最長lps與<=k步remove比較
    public boolean isValidPalindrome(String s, int k) {
        int N = s.length();
        return s.length() - lpsHelper(s, 0, N - 1, new Integer[N][N]) <= k;
    }

    public boolean isValidPalindrome2(String s, int k) {
        String r = new StringBuilder(s).reverse().toString();
        return s.length() - lcs(s, r) <= k;
    }

    private int lcs(String s, String r) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == r.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[n][n];
    }


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.gd959e32ada_0_68
    public int maxCoins(int[] nums) {
        int[] balloons = new int[nums.length + 2];
        int N = balloons.length;
        balloons[0] = balloons[N - 1] = 1;  // 左右邊界外各加一個1分的氣球(不能選擇打爆)，方便後面計算
        for (int i = 1; i < N - 1; i++) {
            balloons[i] = nums[i - 1];
        }
        int[][] dp = new int[N][N];  // dp[i][j] 定義：nums[i...j]區間的最高得分
        for (int i = N - 2; i >= 0; i--) {  // i, j代表當前左右區間
            for (int j = i + 2; j < N; j++) {  // 左右區間之間需要留空間給k打爆，所以j從i+2的位置開始往後走
                for (int k = i + 1; k < j; k++) {  // 預定最後打爆的k，得分為balloons[i] * balloons[k] * balloons[j]
                    // 左區間累計結果[i...k]，右區間累計結果[k...j]
                    dp[i][j] = Math.max(dp[i][j],
                            balloons[i] * balloons[k] * balloons[j] + dp[i][k] + dp[k][j]);
                }
            }
        }
        return dp[0][N - 1];
    }


    public int maxCoinsMemo(int[] nums) {
        int N = nums.length;
        Integer[][] memo = new Integer[N][N];
        return coinHelper(nums, 0, N - 1, memo);
    }

    private int coinHelper(int[] nums, int start, int end, Integer[][] memo) {
        if (start > end) return 0;
        if (memo[start][end] != null) return memo[start][end];
        int max = Integer.MIN_VALUE;
        for (int i = start; i <= end; i++) {  // 預定最後打爆的i，得分為nums[start] * nums[i] * nums[end]
            int left = coinHelper(nums, start, i - 1, memo);  // 左區間累計結果[start...i-1]
            int right = coinHelper(nums, i + 1, end, memo);  // 右區間累計結果[i+1...end]
            int cur = getCoin(nums, i) * getCoin(nums, start - 1) * getCoin(nums, end + 1);
            max = Math.max(max, left + right + cur);
        }
        return memo[start][end] = max;
    }

    private int getCoin(int[] nums, int i) {
        if (i == -1 || i == nums.length) return 1;
        return nums[i];
    }


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.gd959e32ada_0_113
    // dp[i][j] means the minimum score to triangulate A[i] ~ A[j],
    // while there is edge connect A[i] and A[j].
    // We enumerate all points A[k] with i < k < j to form a triangle.
    // The score of this triangulation is dp[i][j], dp[i][k] + dp[k][j] + A[i] * A[j] * A[k]
    public int minScoreMemo(int[] nums) {
        Integer[][] memo = new Integer[51][51];
        return minScoreHelper(0, nums.length - 1, nums, memo);
    }

    private int minScoreHelper(int start, int end, int[] nums, Integer[][] memo) {
        if (end - start < 2) return 0;
        if (memo[start][end] != null) return memo[start][end];
        int res = Integer.MAX_VALUE;
        for (int i = start + 1; i < end; i++) {
            int left = minScoreHelper(start, i, nums, memo);
            int right = minScoreHelper(i, end, nums, memo);
            res = Math.min(res,
                    left + right + nums[start] * nums[i] * nums[end]);
        }
        return memo[start][end] = res;
    }

    public int minScore(int[] nums) {
        int N = nums.length;
        int[][] dp = new int[N][N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j],
                            dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[k]);
                }
            }
        }
        return dp[0][N - 1];
    }


    // https://docs.google.com/presentation/d/1nbQty-f4-KILJJN6lfML2i48vFbdP_KBlZzY09t4mcM/edit#slide=id.g91fd19cbee_2_4
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        Arrays.stream(cuts);
        Integer[][] memo = new Integer[m + 1][m + 1];
        return dfs(cuts, 0, m, n, memo);
    }

    private int dfs(int[] cuts, int left, int right, int n, Integer[][] memo) {
        if (memo[left][right] != null) return memo[left][right];
        if (left >= right) return 0;
        int cost = (right == cuts.length ? n : cuts[right]) - (left == 0 ? 0 : cuts[left - 1]);
        memo[left][right] = Integer.MAX_VALUE;
        for (int i = left; i < right; i++) {
            int leftCost = dfs(cuts, left, i, n, memo);
            int rightCost = dfs(cuts, i + 1, right, n, memo);
            memo[left][right] = Math.min(memo[left][right],
                    cost + leftCost + rightCost);
        }
        return memo[left][right];
    }
}
