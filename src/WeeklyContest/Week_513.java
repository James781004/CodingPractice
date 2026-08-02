package WeeklyContest;

import java.util.Arrays;

public class Week_513 {

    // https://leetcode.cn/problems/maximize-pair-strength-using-gcd/solutions/4005434/mei-ju-pythonjavacgo-by-endlesscheng-t5bz/
    public long maxPairStrength(int[] nums) {
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                long g = gcd(nums[i], nums[j]);
                ans = Math.max(ans, (long) nums[i] * nums[j] / (g * g));
            }
        }
        return ans;
    }

    private long gcd(long a, long b) {
        while (a != 0) {
            long tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


    // https://leetcode.cn/problems/count-of-unfinished-tasks-after-each-shift/solutions/4005423/qian-zhui-he-er-fen-cha-zhao-pythonjavac-4kbm/
    public int[] countTasks(int[] tasks, int[] shifts) {
        int n = tasks.length;
        long[] s = new long[n];
        s[0] = tasks[0];
        for (int i = 1; i < n; i++) {
            s[i] = s[i - 1] + tasks[i];
        }

        long t = 0;
        for (int i = 0; i < shifts.length; i++) {
            t += shifts[i];
            if (t >= s[n - 1]) { // 完成所有任務
                t = 0;
                shifts[i] = 0;
            } else {
                // s 無重復元素，可以用庫函數二分
                int j = Arrays.binarySearch(s, t + 1);
                if (j < 0) j = ~j; // 見 Arrays.binarySearch 源碼
                shifts[i] = n - j;
            }
        }
        return shifts;
    }


    // https://leetcode.cn/problems/count-subarrays-with-even-odd-ratio-ii/solutions/4005429/deng-jie-zhuan-hua-qian-zhui-he-ni-xu-du-2ygw/
    public long countRatioSubarrays(int[] nums, int a, int b) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (nums[i] % 2 == 0 ? -b : a); // 偶數視作 -b，奇數視作 a
        }

        // sum 復制排序
        long[] sortedS = sum.clone();
        Arrays.sort(sortedS);

        FenwickTree t = new FenwickTree(n + 1);
        long ans = 0;
        for (long s : sum) {
            int x = Arrays.binarySearch(sortedS, s) + 1; // 離散化（從 1 開始）
            ans += t.pre(x); // 計算在 s 左邊有多少個 <= s 的數
            t.add(x);
        }
        return ans;
    }

    static class FenwickTree {
        private final int[] tree;

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下標 1 到 n
        }

        // a[i] 增加 1
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public void add(int i) {
            for (; i < tree.length; i += i & -i) {
                tree[i]++;
            }
        }

        // 求前綴和 a[1] + ... + a[i]
        // 1 <= i <= n
        // 時間復雜度 O(log n)
        public int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }
    }


    // 寫法二：歸並排序
    public long countRatioSubarrays2(int[] nums, int a, int b) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (nums[i] % 2 == 0 ? -b : a); // 偶數視作 -b，奇數視作 a
        }

        return mergeCount(sum);
    }

    private long mergeCount(long[] sum) {
        int n = sum.length;
        if (n <= 1) {
            return 0;
        }

        long[] left = Arrays.copyOfRange(sum, 0, n / 2);
        long[] right = Arrays.copyOfRange(sum, n / 2, n);
        long cnt = mergeCount(left) + mergeCount(right); // left 和 right 各自的合法數對個數

        int l = 0;
        int r = 0;
        for (int i = 0; i < n; i++) {
            // 計算一個在 left 中，另一個在 right 中的合法數對個數
            if (l < left.length && (r == right.length || left[l] <= right[r])) {
                sum[i] = left[l];
                l++;
            } else {
                cnt += l; // left 的 [0,l-1] 中的數都 <= right[r]，這有 l 個
                sum[i] = right[r];
                r++;
            }
        }

        return cnt;
    }


}










