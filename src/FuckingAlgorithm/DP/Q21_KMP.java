package FuckingAlgorithm.DP;

public class Q21_KMP {
//    https://leetcode.cn/problems/implement-strstr/


    public class KMP {
        private int[][] dp;
        private String pat;

        public KMP(String pat) {
            this.pat = pat;
            int M = pat.length();
            // dp[狀態][字符] = 下個狀態
            dp = new int[M][256];
            // base case
            dp[0][pat.charAt(0)] = 1;
            // 影子狀態 X 初始為 0
            int X = 0;
            // 構建狀態轉移圖（稍改的更緊湊了）
            for (int j = 1; j < M; j++) {
                for (int c = 0; c < 256; c++)
                    dp[j][c] = dp[X][c];
                dp[j][pat.charAt(j)] = j + 1;
                // 更新影子狀態
                X = dp[X][pat.charAt(j)];
            }
        }

        public int search(String txt) {
            int M = pat.length();
            int N = txt.length();
            // pat 的初始態為 0
            int j = 0;
            for (int i = 0; i < N; i++) {
                // 計算 pat 的下一個狀態
                j = dp[j][txt.charAt(i)];
                // 到達終止態，返回結果
                if (j == M) return i - M + 1;
            }
            // 沒到達終止態，匹配失敗
            return -1;
        }
    }
}
