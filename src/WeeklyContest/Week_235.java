package WeeklyContest;

import java.util.*;

class Week_235 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1816.Truncate%20Sentence/README.md
    public String truncateSentence(String s, int k) {
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ' ' && (--k) == 0) { // 遇到空格--k，直到k == 0 返回 s[0...i]
                return s.substring(0, i);
            }
        }
        return s;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1817.Finding%20the%20Users%20Active%20Minutes/README.md
    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        // 用 map 記錄每個用戶的所有去重操作時間
        Map<Integer, Set<Integer>> d = new HashMap<>();
        for (int[] log : logs) {
            int i = log[0], t = log[1];
            d.computeIfAbsent(i, key -> new HashSet<>()).add(t);
        }

        // 遍歷哈希表，統計每個用戶的用戶活躍分鐘數，最後統計每個用戶活躍分鐘數的分布情況
        int[] ans = new int[k];
        for (Set<Integer> ts : d.values()) {
            ++ans[ts.size() - 1];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1818.Minimum%20Absolute%20Sum%20Difference/README.md
    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        final int mod = (int) 1e9 + 7;
        int[] nums = nums1.clone();
        Arrays.sort(nums);
        int s = 0, n = nums.length;

        // 先計算出在不進行替換的情況下，nums1 和 nums2 的絕對差值和 s
        for (int i = 0; i < n; ++i) {
            s = (s + Math.abs(nums1[i] - nums2[i])) % mod;
        }

        // 枚舉 nums1 中的每個元素 nums1[i] ，將其替換為與 nums2[i] 最接近的元素，
        // 並且這個最接近的元素在 nums1 中。
        int mx = 0;
        for (int i = 0; i < n; ++i) {
            int d1 = Math.abs(nums1[i] - nums2[i]);
            int d2 = 1 << 30;

            // clone 一份 nums1，得到數組 nums，並將 nums 排序。
            // 接下來就在 nums 中二分查找為與 nums2[i] 最接近的元素 nums[j]
            int j = search(nums, nums2[i]);
            if (j < n) {
                d2 = Math.min(d2, Math.abs(nums[j] - nums2[i]));
            }
            if (j > 0) {
                d2 = Math.min(d2, Math.abs(nums[j - 1] - nums2[i]));
            }

            // 更新「原絕對值以及目前找到最小絕對值差值」 mx 的最大值
            mx = Math.max(mx, d1 - d2);
        }

        // 最後絕對差值和 s 減去 剛才求得的 mx
        return (s - mx + mod) % mod;
    }

    private int search(int[] nums, int x) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // https://leetcode.cn/problems/number-of-different-subsequences-gcds/solutions/2061079/ji-bai-100mei-ju-gcdxun-huan-you-hua-pyt-get7/
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1819.Number%20of%20Different%20Subsequences%20GCDs/README.md
    public int countDifferentSubsequenceGCDs(int[] nums) {
        // 對於數組 nums 的所有子序列，其最大公約數一定不超過數組中的最大值 mx
        int mx = Arrays.stream(nums).max().getAsInt();
        boolean[] vis = new boolean[mx + 1];
        for (int x : nums) {
            vis[x] = true;
        }
        int ans = 0;

        // 枚舉 [1...mx] 中的每個數 x ，判斷 x 是否為數組 nums 的子序列的最大公約數。
        // 可以通過枚舉 x 的倍數 y ，判斷 y  是否在數組 nums 中
        for (int x = 1; x <= mx; ++x) {
            int g = 0; // 初始化為0, GCD(x, 0) = x
            for (int y = x; y <= mx; y += x) {
                // 如果 y 在數組 nums 中，則計算 y 的最大公約數 g
                // 如果出現 g == x ，則 x 是數組 nums 的子序列的最大公約數
                if (vis[y]) {
                    g = gcd(g, y);
                    if (x == g) {
                        ++ans; // 找到一個答案
                        break;
                    }
                }
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

