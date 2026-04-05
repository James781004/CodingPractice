package WeeklyContest;

import java.util.*;

public class Week_496 {

    // https://leetcode.cn/problems/mirror-frequency-distance/solutions/3945010/yue-du-li-jie-pythonjavacgo-by-endlessch-ppcl/
    public int mirrorFrequency(String s) {
        int[] cnt = new int['z' + 1];
        for (char ch : s.toCharArray()) {
            cnt[ch]++;
        }

        int ans = 0;
        for (int i = 0; i < 13; i++) {
            ans += Math.abs(cnt['a' + i] - cnt['z' - i]);
        }
        for (int i = 0; i < 5; i++) {
            ans += Math.abs(cnt['0' + i] - cnt['9' - i]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/integers-with-multiple-sum-of-two-cubes/solutions/3944990/mei-ju-ji-shu-pythonjavacgo-by-endlessch-ljw6/
    class Solution {
        private static final int MX = 1_000_000_000;
        private static final List<Integer> goodIntegers = new ArrayList<>();
        private static boolean initialized = false;

        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            Map<Integer, Integer> cnt = new HashMap<>();
            for (int a = 1; a * a * a <= MX / 2; a++) {
                for (int b = a; a * a * a + b * b * b <= MX; b++) {
                    cnt.merge(a * a * a + b * b * b, 1, Integer::sum);
                }
            }

            for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
                if (e.getValue() > 1) {
                    goodIntegers.add(e.getKey());
                }
            }

            Collections.sort(goodIntegers);
        }

        public List<Integer> findGoodIntegers(int n) {
            int i = Collections.binarySearch(goodIntegers, n + 1);
            if (i < 0) {
                i = ~i; // 見 binarySearch 的源碼
            }
            return goodIntegers.subList(0, i);
        }
    }


    // https://leetcode.cn/problems/minimum-increase-to-maximize-special-indices/solutions/3944988/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-8tcq/
    public long minIncrease(int[] nums) {
        int n = nums.length;
        long[] suf = new long[n + 1];
        for (int i = n - 2; i > 0; i -= 2) {
            suf[i] = suf[i + 2] + Math.max(Math.max(nums[i - 1], nums[i + 1]) - nums[i] + 1, 0);
        }

        if (n % 2 > 0) {
            // 修改所有奇數下標
            return suf[1];
        }

        long ans = suf[2]; // 修改 [2,n-2] 中的所有偶數下標
        long pre = 0;
        // 枚舉修改 [1,i] 中的奇數下標，以及 [i+3,n-2] 中的偶數下標
        for (int i = 1; i < n - 1; i += 2) {
            pre += Math.max(Math.max(nums[i - 1], nums[i + 1]) - nums[i] + 1, 0);
            ans = Math.min(ans, pre + suf[i + 3]);
        }

        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-achieve-at-least-k-peaks/solutions/3945005/zuo-fa-lei-si-huan-xing-da-jia-jie-she-p-yu7j/
    public int minOperations(int[] nums, int k) {
        int n = nums.length;
        if (k > n / 2) {
            return -1;
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (nums[(i - 1 + n) % n] < nums[i] && nums[i] > nums[(i + 1) % n]) {
                cnt++;
            }
        }
        if (cnt >= k) { // 優化：已經有至少 k 個峰值了，無需操作
            return 0;
        }

        // 如果 nums[0] 是峰頂，那麼 nums[n-1] 不是峰頂
        int[] a = new int[n + 1];
        a[0] = nums[n - 1];
        System.arraycopy(nums, 0, a, 1, n);
        int ans1 = solve(a, k);

        // 如果 nums[0] 不是峰頂
        int[] b = new int[n + 1];
        System.arraycopy(nums, 0, b, 0, n);
        b[n] = nums[0];
        int ans2 = solve(b, k);

        return Math.min(ans1, ans2);
    }

    // 非環形版本
    public int solve(int[] a, int k) {
        int n = a.length;
        int[][] memo = new int[k + 1][n - 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(k, n - 2, a, memo);
    }

    // 返回使 [0,i+1] 包含 left 個峰值的最小操作次數
    private int dfs(int left, int i, int[] a, int[][] memo) {
        if (left == 0) {
            return 0;
        }
        if (left > (i + 1) / 2) { // [0,i+1] 至多有 (i+1)/2 個峰值
            return Integer.MAX_VALUE / 2; // 防止加法溢出
        }

        if (memo[left][i] != -1) { // 之前計算過
            return memo[left][i];
        }

        // 選或不選
        int notChoose = dfs(left, i - 1, a, memo);
        int choose = dfs(left - 1, i - 2, a, memo) + Math.max(Math.max(a[i - 1], a[i + 1]) - a[i] + 1, 0);
        int res = Math.min(notChoose, choose);

        memo[left][i] = res; // 記憶化
        return res;
    }


}









