package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_483 {

    // https://leetcode.cn/problems/largest-even-number/solutions/3872303/yi-chu-suo-you-de-wei-1pythonjavacgo-by-fm9co/
    public String largestEven(String s) {
        int n = s.length();
        while (n > 0 && s.charAt(n - 1) == '1') {
            n--;
        }
        return s.substring(0, n);
    }


    // https://leetcode.cn/problems/word-squares-ii/solutions/3872301/pai-lie-xing-hui-su-pythonjavacgo-by-end-3i7x/
    public List<List<String>> wordSquares(String[] words) {
        Arrays.sort(words); // 保證答案有序

        int[] path = new int[4];
        boolean[] onPath = new boolean[words.length];
        List<List<String>> ans = new ArrayList<>();

        dfs(words, 0, path, onPath, ans);
        return ans;
    }

    private void dfs(String[] words, int i, int[] path, boolean[] onPath, List<List<String>> ans) {
        if (i == 4) {
            String top = words[path[0]];
            String left = words[path[1]];
            String right = words[path[2]];
            String bottom = words[path[3]];
            if (top.charAt(0) == left.charAt(0) && top.charAt(3) == right.charAt(0)
                    && bottom.charAt(0) == left.charAt(3) && bottom.charAt(3) == right.charAt(3)) {
                ans.add(Arrays.asList(top, left, right, bottom));
            }
            return;
        }

        for (int j = 0; j < words.length; j++) {
            if (!onPath[j]) {
                path[i] = j; // 從沒有選的下標中選一個
                onPath[j] = true; // 已選上
                dfs(words, i + 1, path, onPath, ans);
                onPath[j] = false; // 恢復現場
            }
        }
    }


    // https://leetcode.cn/problems/minimum-cost-to-make-two-binary-strings-equal/solutions/3872286/fen-lei-tao-lun-pythonjavacgo-by-endless-u3jx/
    public long minimumCost(String s, String t, int flipCost, int swapCost, int crossCost) {
        int[][] cnt = new int[2][2];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) & 1][t.charAt(i) & 1]++;
        }

        int a = cnt[0][1];
        int b = cnt[1][0];
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        long res1 = (long) (a + b) * flipCost;
        long res2 = (long) a * swapCost + (long) (b - a) * flipCost;
        int avg = (a + b) / 2;
        long res3 = (long) (avg - a) * crossCost + (long) avg * swapCost + (long) (a + b) % 2 * flipCost;
        return Math.min(Math.min(res1, res2), res3);
    }


    // https://leetcode.cn/problems/minimum-cost-to-merge-sorted-lists/solutions/3872282/yu-chu-li-zi-ji-zhuang-ya-dppythonjavacg-isio/
    public long minMergeCost(int[][] lists) {
        int n = lists.length;
        int u = 1 << n;
        int[] sumLen = new int[u];
        int[][] sorted = new int[u][];
        int[] median = new int[u];

        for (int i = 0; i < n; i++) { // 枚舉不在 s 中的下標 i
            int highBit = 1 << i;
            for (int s = 0; s < highBit; s++) {
                int t = highBit | s;
                sumLen[t] = sumLen[s] + lists[i].length;
                int[] b = merge(sorted[s], lists[i]);
                sorted[t] = b;
                median[t] = b[(b.length - 1) / 2];
            }
        }

        long[] f = new long[u];
        for (int i = 0; i < u; i++) {
            if ((i & (i - 1)) == 0) { // i 只包含一個元素，無法分解成兩個非空子集
                continue; // f[i] = 0
            }
            f[i] = Long.MAX_VALUE;
            // 枚舉 i 的非空真子集 j
            for (int j = i & (i - 1); j > 0; j = (j - 1) & i) {
                int k = i ^ j; // j 關於 i 的補集是 k
                f[i] = Math.min(f[i], f[j] + f[k] + sumLen[j] + sumLen[k] + Math.abs(median[j] - median[k]));
            }
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









