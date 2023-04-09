package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week_333 {
    // https://www.bilibili.com/video/BV1jM411J7y7/
    // https://leetcode.cn/problems/merge-two-2d-arrays-by-summing-values/
    class MergeArrays {
        public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
            int[] cnt = new int[1001];
            for (int[] x : nums1) cnt[x[0]] += x[1];  // 按id把答案放入對應位置
            for (int[] x : nums2) cnt[x[0]] += x[1];
            int n = 0;
            for (int i = 0; i < 1001; i++) {
                if (cnt[i] > 0) n++;  // 大於0的位置是有效位置
            }

            int[][] res = new int[n][2];
            for (int i = 0, j = 0; i < 1001; i++) {
                if (cnt[i] > 0) {
                    res[j++] = new int[]{i, cnt[i]};  // 收集答案
                }
            }
            return res;
        }

        // 歸並排序
        public int[][] mergeArrays2(int[][] nums1, int[][] nums2) {
            List<int[]> list = new ArrayList<>();
            int i = 0, j = 0, m = nums1.length, n = nums2.length;
            while (true) {
                if (i == m) { // nums1到底了，把剩下nums2放進來
                    while (j < n) list.add(nums2[j++]);
                    break;
                }
                if (j == n) { // nums2到底了，把剩下nums1放進來
                    while (i < m) list.add(nums1[i++]);
                    break;
                }
                if (nums1[i][0] < nums2[j][0]) {  // id較小的先進入list，記得移動pointer
                    list.add(nums1[i++]);
                } else if (nums1[i][0] > nums2[j][0]) {
                    list.add(nums2[j++]);
                } else { // id相同的狀況，進入list時值相加，然後同時移動兩個pointer
                    nums1[i][1] += nums2[j++][1];
                    list.add(nums1[i++]);
                }
            }
            int[][] ans = new int[list.size()][2];
            for (i = 0; i < list.size(); i++) {  // 收集答案後返回
                ans[i] = list.get(i);
            }
            return ans;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2571.Minimum%20Operations%20to%20Reduce%20an%20Integer%20to%200/README.md
    class MinOperations {
        public int minOperations(int n) {
            int ans = 1;
            while ((n & (n - 1)) > 0) { // n 不是 2 的冪次
                int lb = n & -n;  // low bit找出最右方的1
                if ((n & (lb << 1)) > 0) n += lb; // 多個連續 1
                else n -= lb; // 單個 1
                ++ans;
            }
            return ans;

            // 一行解
//        return Integer.bitCount(3 * n ^ n);
        }

        // 記憶化搜索
        public int minOperationsDFS(int n) {
            return dfs(n);
        }

        Map<Integer, Integer> memo = new HashMap<>();

        private int dfs(int n) {
            if ((n & (n - 1)) == 0) return 1;  // n 是 2 的冪次
            if (memo.containsKey(n)) return memo.get(n);
            int lb = n & -n;  // low bit找出最右方的1
            int res = 1 + Math.min(dfs(n + lb), dfs(n - lb));
            memo.put(n, res);
            return res;
        }
    }


    // https://leetcode.cn/problems/count-the-number-of-square-free-subsets/
    // https://leetcode.cn/problems/count-the-number-of-square-free-subsets/solution/liang-chong-xie-fa-01bei-bao-zi-ji-zhuan-3ooi/
    class SquareFreeSubsets {
        private final int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        private final int MOD = (int) 1e9 + 7, MX = 30, N_PRIMES = PRIMES.length, M = 1 << N_PRIMES;
        private final int[] SF_TO_MASK = new int[MX + 1]; // SF_TO_MASK[i] 為 i 的質因子集合（用二進制表示）

        {
            for (int i = 2; i <= MX; ++i)
                for (int j = 0; j < N_PRIMES; ++j) {
                    int p = PRIMES[j];
                    if (i % p == 0) {
                        if (i % (p * p) == 0) { // 有平方因子
                            SF_TO_MASK[i] = -1;
                            break;
                        }
                        SF_TO_MASK[i] |= 1 << j; // 把 j 加到集合中
                    }
                }
        }

        public int squareFreeSubsets(int[] nums) {
            int[] f = new int[M]; // f[j] 表示恰好組成質數集合 j 的方案數
            f[0] = 1; // 質數集合是空集的方案數為 1
            for (int x : nums) {
                int mask = SF_TO_MASK[x];
                if (mask >= 0) // x 是 SF
                    for (int j = M - 1; j >= mask; --j)
                        if ((j | mask) == j)  // mask 是 j 的子集
                            f[j] = (f[j] + f[j ^ mask]) % MOD; // 不選 mask + 選 mask
            }
            long ans = 0L;
            for (int v : f) ans += v;
            return (int) ((ans - 1) % MOD); // -1 去掉空集（nums 的空子集）
        }


        public int squareFreeSubsets2(int[] nums) {
            int[] cnt = new int[MX + 1];
            int pow2 = 1;
            for (int x : nums)
                if (x == 1) pow2 = pow2 * 2 % MOD;
                else ++cnt[x];

            long[] f = new long[M]; // f[j] 表示恰好組成質數集合 j 的方案數
            f[0] = pow2; // 用 1 組成空質數集合的方案數
            for (int x = 2; x <= MX; ++x) {
                int mask = SF_TO_MASK[x], c = cnt[x];
                if (mask > 0 && c > 0) {
                    int other = (M - 1) ^ mask, j = other; // mask 的補集 other
                    do { // 枚舉 other 的子集 j
                        f[j | mask] = (f[j | mask] + f[j] * cnt[x]) % MOD; // 不選 mask + 選 mask
                        j = (j - 1) & other;
                    } while (j != other);
                }
            }
            long ans = -1L; // 去掉空集（nums 的空子集）
            for (long v : f) ans += v;
            return (int) (ans % MOD);
        }
    }

    // https://leetcode.cn/problems/find-the-string-with-lcp/solution/tan-xin-gou-zao-yan-zheng-o1e-wai-kong-j-82ik/
    public String findTheString(int[][] lcp) {
        int cur = 0, n = lcp.length;
        char[] s = new char[n];
        for (char c = 'a'; c <= 'z'; c++) {
            while (cur < n && s[cur] > 0) cur++;  // 已經處理過的部份就跳過
            if (cur == n) break; // 構造完畢
            for (int j = cur; j < n; j++) {
                if (lcp[cur][j] > 0) s[j] = c;  // lcp位置如果大於0，就放進當前字典序最小的字母
            }
        }
        while (cur < n) if (s[cur++] == 0) return ""; // 沒有構造完，就返回空字串

        // 直接在原數組上驗證
        for (int i = n - 1; i >= 0; i--)
            for (int j = n - 1; j >= 0; j--) {
                int actualLCP = 0;
//                actualLCP = s[i] != s[j] ? 0 : i == n - 1 || j == n - 1 ? 1 : lcp[i + 1][j + 1] + 1;
                if (s[i] == s[j]) {
                    if (i == n - 1 || j == n - 1) {
                        actualLCP = 1;
                    } else {
                        actualLCP = lcp[i + 1][j + 1] + 1;
                    }
                } else {
                    actualLCP = 0;
                }

                if (lcp[i][j] != actualLCP) return "";
            }

//        int[][] test = new int[n + 1][n + 1];
//        for (int i = n - 1; i >= 0; i--) {
//            for (int j = n - 1; j >= 0; j--) {
//                if (s[i] == s[j]) {
//                    test[i][j] = test[i + 1][j + 1] + 1;
//                } else {
//                    test[i][j] = 0;
//                }
//
//
//                if (test[i][j] != lcp[i][j]) {
//                    return "";
//                }
//            }
//        }

        return new String(s);
    }
}
