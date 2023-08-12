package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

class Week_241 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1863.Sum%20of%20All%20Subset%20XOR%20Totals/README.md
    private int res;

    public int subsetXORSum(int[] nums) {
        dfs(nums, 0, 0);
        return res;
    }

    private void dfs(int[] nums, int depth, int prev) {
        res += prev;
        for (int i = depth; i < nums.length; ++i) {
            prev ^= nums[i];
            dfs(nums, ++depth, prev);
            prev ^= nums[i];
        }
    }


    // https://leetcode.cn/problems/minimum-number-of-swaps-to-make-the-binary-string-alternating/solutions/779071/qi-shi-yi-gong-zhi-you-liang-chong-qing-9cg5d/
    // 把字符串s和標准答案比較，看看有幾個位不一致即可
    public int minSwaps(String s) {
        int s0n0 = 0, s0n1 = 0;
        int s1n0 = 0, s1n1 = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((i & 1) == 0) { // if (i % 2 == 0)
                if (s.charAt(i) != '0') {
                    s0n0 += 1; // 對於s0來說，這個位是0
                } else {
                    s1n1 += 1; // 對於s1來說，這個位是1
                }
            } else {
                if (s.charAt(i) != '0') {
                    s1n0 += 1;  // 對於s0來說，這個位是1
                } else {
                    s0n1 += 1; // 對於s1來說，這個位是0
                }
            }
        }
        if (s0n0 != s0n1 && s1n0 != s1n1) {
            return -1; // s0 s1 都換不了，返回-1
        }
        if (s0n0 != s0n1) {
            return s1n0;  // s1 換得了，返回s1
        }
        if (s1n0 != s1n1) {
            return s0n0; // s0 換得了，返回s0
        }
        return Math.min(s0n0, s1n0); // 兩個都換得了，返回交換次數較少的那個。
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1865.Finding%20Pairs%20With%20a%20Certain%20Sum/README.md
    class FindSumPairs {
        private int[] nums1;
        private int[] nums2;
        private Map<Integer, Integer> cnt = new HashMap<>(); // 用哈希表 cnt 統計數組 nums2 中每個數字出現的次數

        public FindSumPairs(int[] nums1, int[] nums2) {
            this.nums1 = nums1;
            this.nums2 = nums2;
            for (int v : nums2) {
                cnt.put(v, cnt.getOrDefault(v, 0) + 1);
            }
        }

        // 更新哈希表中 nums2[index] 的值，即 cnt[nums2[index]] -= 1，
        // 然後更新 nums2[index] += val，
        // 最後更新哈希表中 nums2[index] 的值，即 cnt[nums2[index]] += 1
        public void add(int index, int val) {
            int old = nums2[index];
            cnt.put(old, cnt.get(old) - 1);
            cnt.put(old + val, cnt.getOrDefault(old + val, 0) + 1);
            nums2[index] += val;
        }

        // 遍歷數組 nums1，對於每個數字 v，需要統計滿足 tot - v 的數字出現的次數，
        // 即 cnt[tot - v]，然後將其累加到答案中。
        public int count(int tot) {
            int ans = 0;
            for (int v : nums1) {
                ans += cnt.getOrDefault(tot - v, 0);
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/solutions/779216/zhuan-huan-cheng-di-yi-lei-si-te-lin-shu-2y1k/
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1866.Number%20of%20Ways%20to%20Rearrange%20Sticks%20With%20K%20Sticks%20Visible/README.md
    public int rearrangeSticks(int n, int k) {
        final int mod = (int) 1e9 + 7;
        int[][] f = new int[n + 1][k + 1]; // f[i][j] 表示長度為 i 的排列中，恰有 j 根木棍可以看到的排列數目
        f[0][0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= k; ++j) {
                f[i][j] = (int) ((f[i - 1][j - 1] + f[i - 1][j] * (long) (i - 1)) % mod);
            }
        }
        return f[n][k];
    }

    // 一維數組優化空間
    public int rearrangeSticks2(int n, int k) {
        final int mod = (int) 1e9 + 7;
        int[] f = new int[k + 1];
        f[0] = 1;
        for (int i = 1; i <= n; ++i) {
            for (int j = k; j > 0; --j) {
                f[j] = (int) ((f[j] * (i - 1L) + f[j - 1]) % mod);
            }
            f[0] = 0;
        }
        return f[k];
    }
}



