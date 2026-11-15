package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_528 {

    // https://leetcode.cn/problems/maximize-score-after-zeroing-subtrees/solutions/4057237/shu-xing-dppythonjavacgo-by-endlesscheng-ybbr/
    public int maxScore(int[] parent, int[] op, int[] score) {
        int n = parent.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, a -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }

        return dfs(0, g, op, score);
    }

    // 返回子樹 x 的最大分數和
    private int dfs(int x, List<Integer>[] g, int[] op, int[] score) {
        int sum = score[x];
        if (g[x].isEmpty()) { // x 是葉子
            return sum;
        }
        int mn = Integer.MAX_VALUE;
        for (int y : g[x]) {
            int s = dfs(y, g, op, score); // 子樹 y 的最大分數和
            sum += s; // 累加分數和
            mn = Math.min(mn, s); // 維護最小的分數和
        }
        if (mn < 0 || op[x] > 0) {
            sum -= mn; // 去掉最小的分數和
        }
        return sum;
    }


    // https://leetcode.cn/problems/game-of-divisible-sums/solutions/4057240/bo-yi-dppythonjavacgo-by-endlesscheng-3aa9/
    public long maxDifference(int[] nums) {
        long[] memo = new long[nums.length];
        Arrays.fill(memo, Long.MIN_VALUE);
        return dfs(0, nums, memo);
    }

    private long dfs(int i, int[] nums, long[] memo) {
        if (i == nums.length) {
            return 0;
        }

        if (memo[i] != Long.MIN_VALUE) {
            return memo[i];
        }

        int x = nums[i];
        long res;
        if (x % 3 == 0) {
            res = x - dfs(i + 1, nums, memo); // 下一回合是對手
        } else {
            res = x + dfs(i + 1, nums, memo); // 下一回合還是我
        }

        if (i < nums.length - 1) {
            long s = x + nums[i + 1];
            if (s % 3 == 0) {
                s -= dfs(i + 2, nums, memo); // 下一回合是對手
            } else {
                s += dfs(i + 2, nums, memo); // 下一回合還是我
            }
            res = Math.max(res, s);
        }

        return memo[i] = res;
    }

    public long maxDifferenceDP(int[] nums) {
        int n = nums.length;
        long[] f = new long[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            f[i] = x % 3 == 0 ? x - f[i + 1] : x + f[i + 1];
            if (i < n - 1) {
                int s = x + nums[i + 1];
                long res = s % 3 == 0 ? s - f[i + 2] : s + f[i + 2];
                f[i] = Math.max(f[i], res);
            }
        }

        return f[0];
    }


    // https://leetcode.cn/problems/count-consistent-subarrays-ii/solutions/4057247/logtrick-dan-diao-zhan-pythonjavacgo-by-m1zrb/
    public long countConsistent(int[] nums) {
        List<int[]> data = new ArrayList<>();
        List<Integer> st = new ArrayList<>();
        long ans = 0;

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];

            // x 入單調棧
            while (!st.isEmpty() && nums[st.getLast()] >= x) {
                st.removeLast();
            }
            st.add(i);

            // 計算右端點為 i 的子數組與值
            for (int[] p : data) {
                p[0] &= x;
            }
            // x 單獨一個數作為子數組
            data.add(new int[]{x, i});

            // 原地去重（相同與值只保留最左邊的）
            int m = 1;
            for (int j = 1; j < data.size(); j++) {
                if (data.get(j)[0] != data.get(j - 1)[0]) {
                    data.set(m++, data.get(j));
                }
            }
            data.subList(m, data.size()).clear();

            for (int k = 0; k < m; k++) {
                int andVal = data.get(k)[0];
                // 判斷子數組最小值是否為 andVal
                int j = lowerBound(st, nums, andVal);
                if (j == st.size() || nums[st.get(j)] != andVal) {
                    continue;
                }

                int left = data.get(k)[1];
                int right = k < m - 1 ? data.get(k + 1)[1] - 1 : i;
                right = Math.min(right, st.get(j));
                // 注：由於 nums[st[j-1]] < andVal，假如子數組包含 st[j-1]，那麼與值會比 andVal 還小，矛盾
                // 所以子數組不可能包含 st[j-1]，所以無需考慮 st[j-1]
                ans += Math.max(right - left + 1, 0);
            }
        }

        return ans;
    }

    private int lowerBound(List<Integer> st, int[] nums, int target) {
        int low = -1;
        int high = st.size();
        while (low + 1 < high) {
            int mid = (low + high) >>> 1;
            if (nums[st.get(mid)] >= target) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return high;
    }


}










