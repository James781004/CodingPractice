package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

public class MinMergeCost {

    // https://leetcode.cn/problems/minimum-cost-to-merge-sorted-lists/solutions/3872282/yu-chu-li-zi-ji-zhuang-ya-dppythonjavacg-isio/
    public long minMergeCost(int[][] lists) {
        int n = lists.length;
        int u = 1 << n;
        int[][] sorted = new int[u][];
        for (int i = 0; i < n; i++) { // 枚舉不在 s 中的下標 i
            int highBit = 1 << i;
            for (int s = 0; s < highBit; s++) {
                sorted[highBit | s] = merge(sorted[s], lists[i]);
            }
        }

        long[] f = new long[u];
        for (int i = 3; i < u; i++) {
            if ((i & (i - 1)) == 0) { // i 只包含一個元素，無法分解成兩個非空子集
                continue; // f[i] = 0
            }
            f[i] = Long.MAX_VALUE;
            // 枚舉 i 的非空真子集 j
            for (int j = i & (i - 1); j > (i ^ j); j = (j - 1) & i) {
                int k = i ^ j; // j 關於 i 的補集是 k
                int medJ = sorted[j][(sorted[j].length - 1) / 2];
                int medK = sorted[k][(sorted[k].length - 1) / 2];
                f[i] = Math.min(f[i], f[j] + f[k] + Math.abs(medJ - medK));
            }
            f[i] += sorted[i].length;
        }

        return f[u - 1];
    }

    // 88. 合並兩個有序數組（創建一個新數組）
    private int[] merge(int[] a, int[] b) {
        if (a == null) {
            return b;
        }

        int n = a.length;
        int m = b.length;
        int[] res = new int[n + m];
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i < n || j < m) {
            if (j == m || i < n && a[i] < b[j]) {
                res[idx++] = a[i++];
            } else {
                res[idx++] = b[j++];
            }
        }
        return res;
    }


}
