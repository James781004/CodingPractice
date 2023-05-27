package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Week_289 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2243.Calculate%20Digit%20Sum%20of%20a%20String/README.md
    public String digitSum(String s, int k) {
        int n = s.length();
        StringBuilder t = new StringBuilder();
        while (s.length() > k) {
            for (int i = 0; i < n; i += k) {
                int x = 0;
                for (int j = i; j < Math.min(i + k, n); j++) {
                    x += s.charAt(j) - '0';
                }
                t.append(x);
            }
            s = t.toString();
        }
        return s;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2244.Minimum%20Rounds%20to%20Complete%20All%20Tasks/README.md
    public int minimumRounds(int[] tasks) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int t : tasks) {
            cnt.merge(t, 1, Integer::sum);
        }
        int ans = 0;
        for (int v : cnt.values()) {
            if (v == 1) {
                return -1;
            }
            ans += v / 3 + (v % 3 == 0 ? 0 : 1);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-trailing-zeros-in-a-cornered-path/solution/by-endlesscheng-7z5a/
    public int maxTrailingZeros(int[][] grid) {
        // 預處理：遞推算出每個數的因子 2 的個數和因子 5 的個數
        int[][] c25 = new int[1001][2];
        for (int i = 2; i <= 1000; i++) {
            if (i % 2 == 0) c25[i][0] = c25[i / 2][0] + 1;
            if (i % 5 == 0) c25[i][1] = c25[i / 5][1] + 1;
        }

        int m = grid.length, n = grid[0].length, ans = 0;
        int[][][] s = new int[m][n + 1][2];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                s[i][j + 1][0] = s[i][j][0] + c25[grid[i][j]][0]; // 每行的因子 2 的前綴和
                s[i][j + 1][1] = s[i][j][1] + c25[grid[i][j]][1]; // 每行的因子 5 的前綴和
            }

        for (int j = 0; j < n; j++) {
            for (int i = 0, s2 = 0, s5 = 0; i < m; i++) { // 從上往下，枚舉左拐還是右拐
                s2 += c25[grid[i][j]][0];
                s5 += c25[grid[i][j]][1];

                // 尾零的個數就是路徑上的數的因子 2 的個數和，與因子 5 的個數之和的較小值(2*5 == 10)。
                // 因為 s 記錄的是左方(0...j)前綴和，所以右方前綴和 (j+1...n) 是 s[n] - s[j+1] 這種形式求出來
                ans = Math.max(ans, Math.max(Math.min(s2 + s[i][j][0], s5 + s[i][j][1]),
                        Math.min(s2 + s[i][n][0] - s[i][j + 1][0], s5 + s[i][n][1] - s[i][j + 1][1])));
            }
            for (int i = m - 1, s2 = 0, s5 = 0; i >= 0; i--) { // 從下往上，枚舉左拐還是右拐
                s2 += c25[grid[i][j]][0];
                s5 += c25[grid[i][j]][1];

                // 尾零的個數就是路徑上的數的因子 2 的個數和，與因子 5 的個數之和的較小值(2*5 == 10)。
                // 因為 s 記錄的是左方(0...j)前綴和，所以右方前綴和 (j+1...n) 是 s[n] - s[j+1] 這種形式求出來
                ans = Math.max(ans, Math.max(Math.min(s2 + s[i][j][0], s5 + s[i][j][1]),
                        Math.min(s2 + s[i][n][0] - s[i][j + 1][0], s5 + s[i][n][1] - s[i][j + 1][1])));
            }
        }
        return ans;
    }


    public int maxTrailingZeros2(int[][] grid) {
        // 總體思路:枚舉四種拐角路徑->[左上,左下,右上,右下]
        // 這裡由於是求路徑中尾隨0的的最大個數,因此肯定是路徑某個方向取最長的結果(多了某個格子不影響)
        // 我們要找尾隨0的個數必須要10因子,必然可以分解為2與5因子,[2,5]的對數就是尾隨0個數->min(2的個數,5的個數)
        int m = grid.length, n = grid[0].length;

        // 創建四個二維數組分別代表:grid[i - 1][j - 1](含)左邊的2,5因子總個數;grid[i][j](含)上邊的2,5因子總個數
        int[][] r2 = new int[m + 1][n + 1], r5 = new int[m + 1][n + 1], c2 = new int[m + 1][n + 1], c5 = new int[m + 1][n + 1];

        // 默認左邊界和上邊界的為r2[i][0]=c2[0][j]=r5[i][0]=c5[0][j]=0
        // 遍歷每個格子完善r2,r5,c2,c5
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 當前格子的值
                int cur = grid[i - 1][j - 1];
                int num2 = getFactorNum(cur, 2), num5 = getFactorNum(cur, 5);
                // 進行轉移求前綴和
                r2[i][j] = r2[i][j - 1] + num2;
                r5[i][j] = r5[i][j - 1] + num5;
                c2[i][j] = c2[i - 1][j] + num2;
                c5[i][j] = c5[i - 1][j] + num5;
            }
        }

        // 結果
        int res = 0;
        // 遍歷四種拐彎方向(其余的都可以進行等價)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // grid[i-1][j-1]為拐彎的格子,總體計算方法就是橫豎的2或者5因子個數相加,注意避免重疊
                // 左邊向右出發,然後向上走
                int min1 = Math.min(r2[i][j] + c2[i - 1][j], r5[i][j] + c5[i - 1][j]);
                res = Math.max(res, min1);
                // 左邊向右出發,然後向下走
                int min2 = Math.min(r2[i][j] + c2[m][j] - c2[i][j], r5[i][j] + c5[m][j] - c5[i][j]);
                res = Math.max(res, min2);
                // 右邊向左出發,然後向上走
                int min3 = Math.min(r2[i][n] - r2[i][j] + c2[i][j], r5[i][n] - r5[i][j] + c5[i][j]);
                res = Math.max(res, min3);
                // 右邊向左出發,然後向下走
                int min4 = Math.min(r2[i][n] - r2[i][j] + c2[m][j] - c2[i - 1][j], r5[i][n] - r5[i][j] + c5[m][j] - c5[i - 1][j]);
                res = Math.max(res, min4);
            }
        }

        return res;
    }


    // 獲取某個數字對應因子的數目:8=2*2*2
    private int getFactorNum(int num, int factor) {
        int k = 0;
        // 提取因子
        while (num % factor == 0) {
            num /= factor;
            k++;
        }
        return k;
    }


    // https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/solution/-by-programmercoding-royf/
    // 與124題非常類似,這類題屬於不過根節點的路徑問題,采用求兩邊最優再進行合並的解法
    // 存儲節點i的子節點,i為父節點
    List<Integer>[] children;
    // 節點個數
    int n;
    String s;
    // 根節點編號默認為0
    final int root = 0;
    // 結果
    int res = 0;

    public int longestPath(int[] parent, String _s) {
        // 初始化各類成員變量
        s = _s;
        n = s.length();
        children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            // parent數組的意義:索引i->子節點;parent[i]->i的父節點
            // 父節點
            int father = parent[i];
            // father的子節點時此時的索引
            children[father].add(i);
        }
        // 開啟dfs
        dfs(root);
        return res;
    }

    // 返回以編號root為起點符合條件的最長路徑長度
    // 這個以root為起點的路徑只向root的子節點方向進行延伸,而不考慮root父節點的方向的路徑*
    // 因為*的情況在執行dfs(root)時已經進行計算,不用重複計算
    private int dfs(int root) {
        // 越過葉子返回0,空節點不組成任何路徑
        if (root >= n) return 0;
        // max1 和 max2 為 root 子節點為起點的路徑中最長的兩條, 這兩部分最後會直接影響res
        int max1 = 0, max2 = 0;
        // 遍歷 root 的每個葉子節點並求出其符合條件的最長路徑
        for (int child : children[root]) {
            // 當前子節點 child 的最長合法路徑長度
            int childLen = dfs(child);
            // 更新 max1 與 max2
            if (childLen > max1 && s.charAt(child) != s.charAt(root)) {
                // childLen 最大且能與 root 拼接
                max2 = max1;
                max1 = childLen;
            } else if (childLen > max2 && s.charAt(child) != s.charAt(root)) {
                // childLen 次大且能與 root 拼接
                max2 = childLen;
            }
        }
        // 將經過 root 的合法路徑長度更新進去 res-> 左邊 + root + 右邊 = 經過 root 的合法路徑長度(root不一定為起點)
        res = Math.max(res, max1 + max2 + 1);
        // 最後返回以root為起點的合法路徑最長長度,顯然為最長的那一條長度max1+root本身長度1
        return max1 + 1;
    }
}

