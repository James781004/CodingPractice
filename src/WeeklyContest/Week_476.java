package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_476 {

    // https://leetcode.cn/problems/maximize-expression-of-three-elements/solutions/3832960/tan-xin-qian-er-da-zhi-he-jian-qu-zui-xi-6lja/
    public int maximizeExpressionOfThree(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return nums[n - 1] + nums[n - 2] - nums[0];
    }


    // https://leetcode.cn/problems/minimum-string-length-after-balanced-removals/solutions/3832949/nao-jin-ji-zhuan-wan-jian-ji-xie-fa-pyth-81r9/
    public int minLengthAfterRemovals(String s) {
        int k = 0;
        for (char ch : s.toCharArray()) {
            k += ch - 'a'; // 統計 b 的個數
        }
        return Math.abs(k * 2 - s.length());
    }


    // https://leetcode.cn/problems/count-distinct-integers-after-removing-zeros/solutions/3832951/tong-ji-bu-han-0-de-zheng-shu-ge-shu-pyt-6pun/
    public long countDistinct(long n) {
        char[] s = Long.toString(n).toCharArray();
        int m = s.length;

        // 計算長度小於 m 的不含 0 的整數個數
        // 9 + 9^9 + ... + 9^(m-1) = (9^m - 9) / 8
        long pow9 = (long) Math.pow(9, m);
        long ans = (pow9 - 9) / 8;

        // 計算長度恰好等於 m 的不含 0 的整數個數
        for (int i = 0; i < m; i++) {
            char d = s[i];
            if (d == '0') { // 只能填 0，不合法，跳出循環
                break;
            }
            // 這一位填 1 到 d-1，後面的數位可以隨便填 1 到 9
            long v = d - '1';
            if (i == m - 1) {
                v++; // 最後一位可以等於 d
            }
            pow9 /= 9;
            ans += v * pow9;
        }

        return ans;
    }


    // https://leetcode.cn/problems/count-stable-subarrays/solutions/3832945/fen-duan-er-fen-cha-zhao-qian-zhui-he-py-ukgs/
    public long[] countStableSubarrays(int[] nums, int[][] queries) {
        int n = nums.length;
        // 找遞增段
        List<Integer> left = new ArrayList<>(); // 遞增段的左端點
        List<Long> s = new ArrayList<>(); // 遞增子數組個數的前綴和
        s.add(0L);
        int start = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (i == n - 1 || x > nums[i + 1]) {
                // 找到了一個遞增段 [start, i]
                left.add(start);
                long m = i - start + 1;
                // 長為 m 的子數組中有 m*(m+1)/2 個遞增子數組
                // 計算 m*(m+1)/2 的前綴和
                s.add(s.getLast() + m * (m + 1) / 2);
                start = i + 1; // 下一個遞增段的左端點
            }
        }

        int qn = queries.length;
        long[] ans = new long[qn];
        for (int k = 0; k < qn; k++) {
            int l = queries[k][0];
            int r = queries[k][1];

            int i = upperBound(left, l); // 左端點嚴格大於 l 的第一個區間
            int j = upperBound(left, r) - 1; // 包含 r 的最後一個區間

            // l 和 r 在同一個區間
            if (i > j) {
                long m = r - l + 1;
                ans[k] = m * (m + 1) / 2;
                continue;
            }

            // l 和 r 在不同區間
            // 分成三段 [l, left[i]) + [left[i], left[j]) + [left[j], r]
            // 中間那段的子數組個數用前綴和計算
            long m = left.get(i) - l;
            long m2 = r - left.get(j) + 1;
            ans[k] = m * (m + 1) / 2 + (s.get(j) - s.get(i)) + m2 * (m2 + 1) / 2;
        }
        return ans;
    }

    private int upperBound(List<Integer> a, int x) {
        int l = -1;
        int r = a.size();
        while (l + 1 < r) {
            int m = l + (r - l) / 2;
            if (a.get(m) > x) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }


}









