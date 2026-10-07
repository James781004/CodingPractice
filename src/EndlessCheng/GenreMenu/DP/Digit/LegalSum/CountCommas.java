package EndlessCheng.GenreMenu.DP.Digit.LegalSum;

public class CountCommas {

    // https://leetcode.cn/problems/count-commas-in-range-ii/solutions/3926306/wu-nao-shu-wei-dp-by-sjdyf-isr8/
    // 記憶化陣列 memo[ind][isNum][isLimit]
    // 因為每次返回兩個值，所以陣列的最後一維長度為 2。
    // memo[ind][0/1][0/1][0] 存組合數，memo[ind][0/1][0/1][1] 存逗號數
    private Long[][][][] memo;
    private String s;
    private int n;

    public long countCommas(long num) {
        s = String.valueOf(num);
        n = s.length();

        // 初始化記憶化陣列
        // isNum 和 isLimit 只有 0 或 1 兩種狀態
        memo = new Long[n][2][2][2];

        // 呼叫 dfs，對應 Python 的 dfs(0, False, True)
        // 在 Java 中，我們用 0 代表 False，1 代表 True
        long[] res = dfs(0, 0, 1);

        return res[1]; // res[1] 儲存的是總逗號數
    }

    // 返回值 long[]：[0] 是組合數 (cntb), [1] 是逗號數 (ans)
    private long[] dfs(int ind, int isNum, int isLimit) {
        // 邊界條件
        if (ind == n) {
            if (isNum == 1) {
                return new long[]{1, 0}; // 形成了一個有效的數字，組合數+1，逗號數為0
            } else {
                return new long[]{0, 0}; // 不是有效數字
            }
        }

        // 檢查是否已經計算過該狀態
        if (memo[ind][isNum][isLimit][0] != null) {
            return new long[]{
                    memo[ind][isNum][isLimit][0],
                    memo[ind][isNum][isLimit][1]
            };
        }

        int hi = isLimit == 1 ? s.charAt(ind) - '0' : 9;

        long ans = 0;  // 收集逗號數
        long cntb = 0; // 收集組合數

        for (int i = 0; i <= hi; i++) {
            // 對應 Python 的 aa = (isNum or i != 0)
            int nextIsNum = (isNum == 1 || i != 0) ? 1 : 0;

            // 對應 Python 的 bb = (isLimit and i == hi)
            int nextIsLimit = (isLimit == 1 && i == hi) ? 1 : 0;

            // 遞歸下一層
            long[] nextRes = dfs(ind + 1, nextIsNum, nextIsLimit);
            long ncntb = nextRes[0]; // 下一層返回的組合數
            long nans = nextRes[1];  // 下一層返回的逗號數

            // 計算從當前位置到末尾，一個正常數字該有的逗號數量
            // 比如長度剩下 4，(4-1)/3 = 1 個逗號
            long ff = (n - ind - 1) / 3;

            // 如果當前還不是有效數字（前面全是前導零），而現在選了非零數字（代表這數字從這一位開始）
            if (isNum == 0 && nextIsNum == 1) {
                ans += ff * ncntb; // 這個數字開頭會貢獻：(目前的逗號數 * 後續能形成的組合數)
            }

            ans += nans;   // 累加後續的所有逗號
            cntb += ncntb; // 累加後續的所有組合數
        }

        // 儲存到記憶化陣列中
        memo[ind][isNum][isLimit][0] = cntb;
        memo[ind][isNum][isLimit][1] = ans;

        return new long[]{cntb, ans};
    }

}
