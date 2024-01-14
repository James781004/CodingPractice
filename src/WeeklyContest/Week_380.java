package WeeklyContest;

import java.util.*;

public class Week_380 {
    // https://leetcode.cn/problems/count-elements-with-maximum-frequency/solutions/2603738/on-yi-ci-bian-li-pythonjavacgo-by-endles-0jye/
    public int maxFrequencyElements(int[] nums) {
        int ans = 0, maxCnt = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            int c = cnt.merge(x, 1, Integer::sum);
            if (c > maxCnt) {
                maxCnt = ans = c;
            } else if (c == maxCnt) {
                ans += c;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-beautiful-indices-in-the-given-array-i/solutions/2603716/fei-bao-li-zuo-fa-kmper-fen-cha-zhao-pyt-0o8y/
    // 用 KMP 求出 a 在 s 中的所有出現位置，記作 posA。
    // 用 KMP 求出 b 在 s 中的所有出現位置，記作 posB。
    // 遍歷 posA 中的下標 i，在 posB 中二分查找離 i 最近的 j。
    // 如果 ∣i−j∣≤k，則把 i 加入答案。
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        char[] text = s.toCharArray();
        List<Integer> posA = kmp(text, a.toCharArray());
        List<Integer> posB = kmp(text, b.toCharArray());

        List<Integer> ans = new ArrayList<>();
        for (int i : posA) {
            int bi = lowerBound(posB, i);
            if (bi < posB.size() && posB.get(bi) - i <= k ||
                    bi > 0 && i - posB.get(bi - 1) <= k) {
                ans.add(i);
            }
        }
        return ans;
    }

    private List<Integer> kmp(char[] text, char[] pattern) {
        int m = pattern.length;
        int[] pi = new int[m];
        int c = 0;
        for (int i = 1; i < m; i++) {
            char v = pattern[i];
            while (c > 0 && pattern[c] != v) {
                c = pi[c - 1];
            }
            if (pattern[c] == v) {
                c++;
            }
            pi[i] = c;
        }

        List<Integer> res = new ArrayList<>();
        c = 0;
        for (int i = 0; i < text.length; i++) {
            char v = text[i];
            while (c > 0 && pattern[c] != v) {
                c = pi[c - 1];
            }
            if (pattern[c] == v) {
                c++;
            }
            if (c == m) {
                res.add(i - m + 1);
                c = pi[c - 1];
            }
        }
        return res;
    }

    // 開區間寫法
    // 請看 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(List<Integer> nums, int target) {
        int left = -1, right = nums.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = (left + right) >>> 1;
            if (nums.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right;
    }


    // https://leetcode.cn/problems/maximum-number-that-sum-of-the-prices-is-less-than-or-equal-to-k/solutions/2603673/er-fen-da-an-shu-wei-dpwei-yun-suan-pyth-tkir/
    public long findMaximumNumber(long k, int x) {
        this.x = x;
        // 開區間二分，原理見 https://www.bilibili.com/video/BV1AP41137w7/
        long left = 0;
        long right = (k + 1) << (x - 1);
        while (left + 1 < right) {
            long mid = (left + right) >>> 1;
            if (countDigitOne(mid) <= k) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int x;
    private long num;
    private long memo[][];

    private long countDigitOne(long num) {
        this.num = num;
        int m = 64 - Long.numberOfLeadingZeros(num);
        memo = new long[m][m + 1];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(m - 1, 0, true);
    }

    private long dfs(int i, int cnt1, boolean isLimit) {
        if (i < 0) return cnt1;
        if (!isLimit && memo[i][cnt1] != -1) return memo[i][cnt1];
        int up = isLimit ? (int) (num >> i & 1) : 1;
        long res = 0;
        for (int d = 0; d <= up; d++) { // 枚舉要填入的數字 d
            res += dfs(i - 1, cnt1 + (d == 1 && (i + 1) % x == 0 ? 1 : 0), isLimit && d == up);
        }
        if (!isLimit) memo[i][cnt1] = res;
        return res;
    }
}
