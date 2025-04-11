package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class minHeightShelves {

    // https://leetcode.cn/problems/filling-bookcase-shelves/solutions/2240688/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-0vg6/
    private int[][] books;
    private int shelfWidth;
    private int[] memo;

    public int minHeightShelves(int[][] books, int shelfWidth) {
        this.books = books;
        this.shelfWidth = shelfWidth;
        int n = books.length;
        memo = new int[n];
        Arrays.fill(memo, -1); // -1 表示還沒有計算過
        return dfs(n - 1); //  dfs(i) 表示把 books[0] 到 books[i] 按順序擺放後的最小高度
    }

    private int dfs(int i) {
        if (i < 0) return 0; // 沒有書了，高度是 0
        if (memo[i] != -1) return memo[i]; // 之前計算過了
        int res = Integer.MAX_VALUE, maxH = 0, leftW = shelfWidth;
        for (int j = i; j >= 0; j--) { // 枚舉最後一層的第一本書的下標 j
            leftW -= books[j][0];
            if (leftW < 0) break; // 空間不足，無法放書
            maxH = Math.max(maxH, books[j][1]); // 從 j 到 i 的最大高度
            res = Math.min(res, dfs(j - 1) + maxH);
        }
        return memo[i] = res; // 記憶化
    }


    public int minHeightShelvesDP(int[][] books, int shelfWidth) {
        int n = books.length;
        var f = new int[n + 1]; // f[0]=0，翻譯自 dfs(-1)=0
        for (int i = 0; i < n; i++) {
            f[i + 1] = Integer.MAX_VALUE;
            int maxH = 0, leftW = shelfWidth;
            for (int j = i; j >= 0; j--) {
                leftW -= books[j][0];
                if (leftW < 0) break; // 空間不足，無法放書
                maxH = Math.max(maxH, books[j][1]); // 從 j 到 i 的最大高度
                f[i + 1] = Math.min(f[i + 1], f[j] + maxH);
            }
        }
        return f[n]; // 翻譯自 dfs(n-1)
    }


}
