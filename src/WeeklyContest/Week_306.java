package WeeklyContest;

import java.util.Arrays;

public class Week_306 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2373.Largest%20Local%20Values%20in%20a%20Matrix/README.md
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        int[][] ans = new int[n - 2][n - 2];
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; ++j) {
                for (int x = i; x <= i + 2; ++x) {  // 3 * 3 範圍內找最大值
                    for (int y = j; y <= j + 2; ++y) {
                        ans[i][j] = Math.max(ans[i][j], grid[x][y]);
                    }
                }
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2374.Node%20With%20Highest%20Edge%20Score/README.md
    public int edgeScore(int[] edges) {
        int n = edges.length;
        long[] cnt = new long[n];
        for (int i = 0; i < n; i++) {
            cnt[edges[i]] += 1;
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (cnt[ans] < cnt[i]) {
                ans = i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/construct-smallest-number-from-di-string/solution/by-endlesscheng-8ee3/
    // 貪心策略：
    // 把 pattern 按照 III⋯IDDD⋯D 分組，每組前一段是 I，後一段是 D。
    // 遍歷每一段，設當前段的長度為 x，我們應該把剩余最小的 x 個數字填到該段上（如果是第一段則填最小的 x+1 個數字），從而保證字典序最小。
    // 舉例說明，假如第一段為 IIIDDD，構造方案如下：
    // 前 2 個 I 視作長度為 3 的上升段；
    // 剩余的 I 和 D 視作長度為 4 的下降段；
    // 最小的 3 個數給上升段，然後剩余最小的 4 個數給下降段；
    // 構造結果為 1237654。
    public String smallestNumber(String pattern) {
        int i = 0, n = pattern.length();
        char cur = '1';
        char[] ans = new char[n + 1];
        while (i < n) {
            if (i > 0 && pattern.charAt(i) == 'I') i++;  // 進入I區段先往後一位（因為最後一行D區段已經線處理了DI邊界）
            for (; i < n && pattern.charAt(i) == 'I'; i++) ans[i] = cur++;  // 統計I區段長度，並把I區段正向順序填入數字cur
            int i0 = i; // D區段的起點
            while (i < n && pattern.charAt(i) == 'D') i++;  // 統計D區段長度
            for (int j = i; j >= i0; j--) ans[j] = cur++;  // D區段反向順序填入數字cur，接下來進入I區段先往後一位（就是while第一行）
        }
        return new String(ans);
    }


    public String smallestNumber2(String pattern) {
        /*
        從前向後依次進行處理:
        首先字典序最小，也就是數字最小，那麼前面的能越小就越好
        假設這個數字是n，最理想的情況肯定是這n+1個數字完全從1開始升序
        1,2,3,...,n，這個顯然是遵循"I"升序的，凡是違反了D降序
        我們就掃描這個過程有哪些"D"，遇到D就代表要降序，我們將該處數字遷移到距離自己最近的升序對處
        例如:12354876 現在9要進來了(注意前面的DD順序不能被打亂)，因此只能插在4與8之間變成123549876
            再往前就變大了，如果不這麼前就會破壞前面的DD關系
         */
        int n = pattern.length();
        StringBuilder sb = new StringBuilder();
        sb.append(1);   // 1是要初始插入的
        // 遍歷pattern
        for (int i = 0; i < n; i++) {
            char c = pattern.charAt(i);
            if (c == 'I') {
                // 升序可以直接加入當前數字
                sb.append(i + 2);
            } else {
                // 降序才需要處理
                // 由於數字i+2必定和前面的數字必定形成升序關系，因此從前面一位數字開始尋找
                // 其中sb的前面一位數字索引為i
                int cur = i;
                // 降序對就要向前走
                while (cur > 0 && sb.charAt(cur - 1) > sb.charAt(cur)) {
                    cur--;
                }
                // cur停留在sb首個升序對後面數字索引處，進行插入
                sb.insert(cur, i + 2);
            }
        }
        return sb.toString();
    }


    // https://leetcode.cn/problems/count-special-integers/solution/shu-wei-dp-mo-ban-by-endlesscheng-xtgx/
    // https://www.bilibili.com/video/BV1rS4y1s721/
    // 數位DP
    char s[];
    int memo[][];

    public int countSpecialNumbers(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++)
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        return f(0, 0, true, false);
    }

    int f(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0; // isNum 為 true 表示得到了一個合法數字
        if (!isLimit && isNum && memo[i][mask] != -1) return memo[i][mask];
        int res = 0;
        if (!isNum) res = f(i + 1, mask, false, false); // 可以跳過當前數位
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的數字都和 n 的一樣，那麼這一位至多填數字 s[i]（否則就超過 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; ++d) // 枚舉要填入的數字 d
            if ((mask >> d & 1) == 0) // d 不在 mask 中
                res += f(i + 1, mask | (1 << d), isLimit && d == up, true);
        if (!isLimit && isNum)
            memo[i][mask] = res;
        return res;
    }
}
