package WeeklyContest;

import java.util.*;

public class Week_436 {

    // https://leetcode.cn/problems/sort-matrix-by-diagonals/solutions/3068709/mo-ban-mei-ju-dui-jiao-xian-pythonjavacg-pjxp/
    public int[][] sortMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 第一排在右上，最後一排在左下
        // 每排從左上到右下
        // 令 k=i-j+n，那麼右上角 k=1，左下角 k=m+n-1
        for (int k = 1; k < m + n; k++) {
            // 核心：計算 j 的最小值和最大值
            int minJ = Math.max(n - k, 0); // i=0 的時候，j=n-k，但不能是負數
            int maxJ = Math.min(m + n - 1 - k, n - 1); // i=m-1 的時候，j=m+n-1-k，但不能超過 n-1
            List<Integer> a = new ArrayList<>(maxJ - minJ + 1); // 預分配空間
            for (int j = minJ; j <= maxJ; j++) {
                a.add(grid[k + j - n][j]); // 根據 k 的定義得 i=k+j-n
            }
            a.sort(minJ > 0 ? null : Comparator.reverseOrder());
            for (int j = minJ; j <= maxJ; j++) {
                grid[k + j - n][j] = a.get(j - minJ);
            }
        }
        return grid;
    }


    // https://leetcode.cn/problems/assign-elements-to-groups-with-constraints/solutions/3068620/diao-he-ji-shu-mei-ju-yu-chu-li-mei-ge-s-8r67/
    public int[] assignElements(int[] groups, int[] elements) {
        Map<Integer, Integer> idx = new HashMap<>();
        // 倒著遍歷，相同元素取最小的下標
        for (int i = elements.length - 1; i >= 0; i--) {
            idx.put(elements[i], i);
        }

        int mx = Arrays.stream(groups).max().getAsInt();
        int[] target = new int[mx + 1];
        Arrays.fill(target, Integer.MAX_VALUE);
        for (var e : idx.entrySet()) { // 枚舉 elements 中的不同元素 x
            int x = e.getKey();
            int i = e.getValue();
            for (int y = x; y <= mx; y += x) { // 枚舉 x 的倍數 y
                target[y] = Math.min(target[y], i); // 標記 y 可以被 elements[i] 整除，取最小的下標
            }
        }

        // 回答詢問
        for (int i = 0; i < groups.length; i++) {
            int j = target[groups[i]];
            groups[i] = j == Integer.MAX_VALUE ? -1 : j; // 原地修改
        }
        return groups;
    }


    // https://leetcode.cn/problems/count-substrings-divisible-by-last-digit/solutions/3068623/gong-shi-tui-dao-dong-tai-gui-hua-python-iw4a/
    public long countSubstrings(String s) {
        long ans = 0;
        int[][] f = new int[10][9];
        for (char d : s.toCharArray()) {
            d -= '0';
            for (int m = 1; m < 10; m++) { // 枚舉模數 m
                // 滾動數組計算 f
                int[] nf = new int[m];
                nf[d % m] = 1;
                for (int rem = 0; rem < m; rem++) { // 枚舉模 m 的余數 rem
                    nf[(rem * 10 + d) % m] += f[m][rem]; // 刷表法
                }
                f[m] = nf;
            }
            // 以 s[i] 結尾的，模 s[i] 余數為 0 的子串個數
            ans += f[d][0];
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximize-the-minimum-game-score/solutions/3068672/er-fen-da-an-cong-zuo-dao-you-tan-xin-py-3bhl/
    public long maxScore(int[] points, int m) {
        int mn = Arrays.stream(points).min().getAsInt();
        long left = 0;
        long right = (long) (m + 1) / 2 * mn + 1;
        while (left + 1 < right) {
            long mid = (left + right) >>> 1;
            if (check(mid, points, m)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(long low, int[] points, int m) {
        int n = points.length;
        long left = m;
        long pre = 0;
        for (int i = 0; i < n; i++) {
            int p = points[i];
            long k = (low - 1) / p + 1 - pre; // 還需要操作的次數
            if (i == n - 1 && k <= 0) { // 最後一個數已經滿足要求
                break;
            }
            k = Math.max(k, 1); // 至少要走 1 步
            left -= k * 2 - 1; // 左右橫跳
            if (left < 0) {
                return false;
            }
            pre = k - 1; // 右邊那個數已經操作 k-1 次
        }
        return true;
    }


}









