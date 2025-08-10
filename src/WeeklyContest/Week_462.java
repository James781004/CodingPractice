package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_462 {

    // https://leetcode.cn/problems/flip-square-submatrix-vertically/solutions/3748526/100754-chui-zhi-fan-zhuan-zi-ju-zhen-by-6390n/
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        //確定待翻轉的區間，取k的一半否則又還原回去了
        for (int i = 0; i < k / 2; i++) {
            //上下兩端待交換的行
            int a = x + i, b = x + k - 1 - i;
            //在長度為k的範圍內交換兩行的值
            for (int j = y; j < y + k; j++) {
                int temp = grid[a][j];
                grid[a][j] = grid[b][j];
                grid[b][j] = temp;
            }
        }
        return grid;
    }


    // https://leetcode.cn/problems/maximum-k-to-sort-a-permutation/solutions/3748592/gou-zao-ti-pythonjavacgo-by-endlesscheng-zq8z/
    public int sortPermutation(int[] nums) {
        int ans = -1; // 二進制全為 1
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (i != x) {
                ans &= x;
            }
        }
        return Math.max(ans, 0);
    }

    // https://leetcode.cn/problems/maximum-total-from-optimal-activation-order/solutions/3748516/yue-du-li-jie-ti-nao-jin-ji-zhuan-wan-py-kua4/
    public long maxTotal(int[] value, int[] limit) {
        int n = value.length;
        List<Integer>[] groups = new ArrayList[n + 1];
        Arrays.setAll(groups, v -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            groups[limit[i]].add(value[i]);
        }

        long ans = 0;
        for (int lim = 1; lim <= n; lim++) {
            List<Integer> a = groups[lim];
            // 取最大的 lim 個數
            a.sort(Collections.reverseOrder());
            if (a.size() > lim) {
                a = a.subList(0, lim);
            }
            for (int x : a) {
                ans += x;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/next-special-palindrome-number/solutions/3748548/bao-li-mei-ju-he-fa-pai-lie-by-endlessch-b5gw/
    private static final int ODD_MASK = 0x155;
    private static final int D = 9;

    private static final int[] size = new int[1 << D];

    static {
        // 預處理 size 數組
        for (int mask = 1; mask < (1 << D); mask++) {
            int t = mask & ODD_MASK;
            if ((t & (t - 1)) > 0) { // 有超過一個奇數
                continue;
            }
            for (int i = 0; i < D; i++) {
                if ((mask >> i & 1) != 0) {
                    size[mask] += i + 1;
                }
            }
        }
    }

    public long specialPalindrome(long num) {
        int targetSize = String.valueOf(num).length();
        for (int mask = 1; mask < (1 << D); mask++) {
            int sz = size[mask];
            if (sz != targetSize && sz != targetSize + 1) {
                continue;
            }

            // 構造要枚舉的排列
            int[] perm = new int[sz / 2];
            int idx = 0;
            int odd = 0;
            for (int x = 1; x <= D; x++) {
                if ((mask >> (x - 1) & 1) > 0) {
                    for (int k = 0; k < x / 2; k++) {
                        perm[idx++] = x;
                    }
                    if (x % 2 != 0) {
                        odd = x;
                    }
                }
            }

            boolean[] onPath = new boolean[perm.length];
            dfs(0, perm, 0, onPath, odd, num);
        }
        return ans;
    }

    private long ans = Long.MAX_VALUE;

    // i 表示當前要填排列的第幾個數
    private boolean dfs(int i, int[] perm, long res, boolean[] onPath, int odd, long num) {
        if (i == perm.length) {
            long v = res;
            if (odd > 0) {
                res = res * 10 + odd;
            }
            // 反轉 x 的左半，拼在 x 後面
            while (v > 0) {
                res = res * 10 + v % 10;
                v /= 10;
            }
            if (res >= ans) {
                return true;
            }
            if (res > num) { // 滿足要求
                ans = res;
                return true;
            }
            return false;
        }

        for (int j = 0; j < perm.length; j++) {
            if (onPath[j] || (j > 0 && perm[j] == perm[j - 1] && !onPath[j - 1])) {
                continue;
            }
            onPath[j] = true;
            if (dfs(i + 1, perm, res * 10 + perm[j], onPath, odd, num)) {
                return true;
            }
            onPath[j] = false;
        }
        return false;
    }


}









