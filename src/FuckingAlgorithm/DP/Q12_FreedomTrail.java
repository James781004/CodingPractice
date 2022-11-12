package FuckingAlgorithm.DP;

import java.util.*;

public class Q12_FreedomTrail {
//    https://leetcode.cn/problems/freedom-trail/
//    514. 自由之路
//    電子游戲“輻射4”中，任務 “通向自由” 要求玩家到達名為 “Freedom Trail Ring” 的金屬表盤，並使用表盤拼寫特定關鍵詞才能開門。
//
//    給定一個字符串 ring ，表示刻在外環上的編碼；給定另一個字符串 key ，表示需要拼寫的關鍵詞。您需要算出能夠拼寫關鍵詞中所有字符的最少步數。
//
//    最初，ring 的第一個字符與 12:00 方向對齊。
//    您需要順時針或逆時針旋轉 ring 以使 key 的一個字符在 12:00 方向對齊，然後按下中心按鈕，以此逐個拼寫完 key 中的所有字符。
//
//    旋轉 ring 拼出 key 字符 key[i] 的階段中：
//
//    1. 您可以將 ring 順時針或逆時針旋轉 一個位置 ，計為1步。
//       旋轉的最終目的是將字符串 ring 的一個字符與 12:00 方向對齊，並且這個字符必須等於字符 key[i] 。
//    2. 如果字符 key[i] 已經對齊到12:00方向，您需要按下中心按鈕進行拼寫，這也將算作 1 步。
//       按完之後，您可以開始拼寫 key 的下一個字符（下一階段）, 直至完成所有拼寫。

    // 字符 -> 索引列表
    HashMap<Character, List<Integer>> charToIndex = new HashMap<>();
    // 備忘錄
    int[][] memo;

    /* 主函數 */
    public int findRotateSteps(String ring, String key) {
        int m = ring.length();
        int n = key.length();

        // 備忘錄全部初始化為 0
        memo = new int[m][n];

        // 記錄圓環上字符到索引的映射
        for (int i = 0; i < ring.length(); i++) {
            char c = ring.charAt(i);
            if (!charToIndex.containsKey(c)) {
                charToIndex.put(c, new LinkedList<>());
            }
            charToIndex.get(c).add(i);
        }

        // 圓盤指針最初指向 12 點鐘方向，
        // 從第一個字符開始輸入 key
        // i: 當前位置下標
        // j: 目前字符位置下標
        return process(ring, 0, key, 0);
    }

    // 計算圓盤指針在 ring[i]，輸入 key[j..] 的最少操作數
    private int process(String ring, int i, String key, int j) {
        // base case 完成輸入
        if (j == key.length()) return 0;

        // 查找備忘錄，避免重疊子問題
        if (memo[i][j] != 0) return memo[i][j];

        int n = ring.length();

        // 做選擇
        int res = Integer.MAX_VALUE;

        // ring 上可能有多個字符 key[j]
        for (int k : charToIndex.get(key.charAt(j))) {
            // 撥動指針的次數
            int delta = Math.abs(k - i);
            // 選擇順時針還是逆時針
            delta = Math.min(delta, n - delta);
            // 將指針撥到 ring[k]，繼續輸入 key[j+1..]
            int subProblem = process(ring, k, key, j + 1);
            // 選擇「整體」操作次數最少的
            // 加一是因為按動按鈕也是一次操作
            res = Math.min(res, 1 + delta + subProblem);
        }

        // 將結果存入備忘錄
        memo[i][j] = res;
        return res;
    }


    // https://leetcode.cn/problems/freedom-trail/solution/freedom-trail-by-ikaruga/
    public int findRotateStepsDP(String ring, String key) {
        int n = ring.length();
        int m = key.length();

        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<>();
        }

        // 根據字符下標位置加入座標
        for (int i = 0; i < n; i++) {
            pos[ring.charAt(i) - 'a'].add(i);
        }

        int[][] dp = new int[m][n];
        for (int[] subdp : dp) {
            Arrays.fill(subdp, Integer.MAX_VALUE);
        }

        for (int i = 0; i < m; i++) {
            for (int j : pos[key.charAt(i) - 'a']) {
                if (i == 0) {
                    dp[i][j] = Math.min(dp[i][j], clac(n, 0, j) + 1);
                    continue;
                }
                for (int k : pos[key.charAt(i - 1) - 'a']) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + clac(n, k, j) + 1);
                }
            }
        }
        return Arrays.stream(dp[m - 1]).min().getAsInt();
    }

    private int clac(int n, int a, int b) {
        return Math.min((n + a - b) % n, (n + b - a) % n);
    }
}
